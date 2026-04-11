package com.nastena.pawsitive.server.user;

import com.nastena.pawsitive.server.animal.Animal;
import com.nastena.pawsitive.server.animal.AnimalRepository;
import com.nastena.pawsitive.server.favorite.FavoriteRepository;
import com.nastena.pawsitive.server.shelter.Shelter;
import com.nastena.pawsitive.server.shelter.ShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserAnimalsQueueService {

    private class AnimalsQueue {

        public final ArrayList<Long> animals = new ArrayList<>(QUEUE_SIZE);

        public int lastAnimalIndex = 0;
    }

    private static final int RATION_SIZE = 5;
    private static final int QUEUE_SIZE = 50;
    private static final int SHELTERS_PER_QUEUE_GENERATION = 5;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private ShelterRepository shelterRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    private final HashMap<Long, AnimalsQueue> userAnimalsQueue = new HashMap<>();

    public List<Animal> getNextRation(User user) {

        AnimalsQueue queue = userAnimalsQueue.getOrDefault(user.getId(), new AnimalsQueue());
        userAnimalsQueue.put(user.getId(), queue);

        if (queue.animals.isEmpty()) {
            fillQueue(user, queue);
        }

        int beginIndex = queue.lastAnimalIndex;
        int endIndex = Math.min(beginIndex + RATION_SIZE, queue.animals.size());
        List<Animal> animals = queue.animals.subList(beginIndex, endIndex).stream()
                .filter(
                        animalId -> favoriteRepository.findByUserAndAnimalId(user, animalId).isEmpty()
                )
                .map(animalRepository::findById)
                .flatMap(Optional::stream)
                .toList();


        if (endIndex == queue.animals.size()) {
            queue.animals.clear();
        }

        return animals;

    }

    private void fillQueue(User user, AnimalsQueue animalsQueue) {
        animalsQueue.animals.clear();

        List<Shelter> shelters = shelterRepository.findAll();
        Collections.shuffle(shelters);

        int useSheltersAmount = Math.min(SHELTERS_PER_QUEUE_GENERATION, shelters.size());
        List<Shelter> sheltersToUse = shelters.subList(0, useSheltersAmount);

        for (Shelter shelter : sheltersToUse) {
            animalsQueue.animals.addAll(
                    animalRepository.findAnimalsByShelter(shelter).stream()
                            .filter(
                                    animal -> favoriteRepository.findByUserAndAnimal(user, animal).isEmpty()
                            )
                            .map(Animal::getId)
                            .toList()
            );
        }

        Collections.shuffle(animalsQueue.animals);

        int animalsSize = animalsQueue.animals.size();
        if (animalsQueue.animals.size() > QUEUE_SIZE) {
            animalsQueue.animals.subList(animalsSize - QUEUE_SIZE, animalsSize).clear();
        }
    }
}
