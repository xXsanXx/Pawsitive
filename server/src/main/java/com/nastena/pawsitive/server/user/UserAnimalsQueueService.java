package com.nastena.pawsitive.server.user;

import com.nastena.pawsitive.dto.AnimalStatus;
import com.nastena.pawsitive.server.adoption.AdoptionRequestRepository;
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

    @Autowired
    private AdoptionRequestRepository adoptionRequestRepository;

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
                .map(animalRepository::findById)
                .flatMap(Optional::stream)
                .filter(
                        animal -> filterAnimal(user, animal)
                )
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
                                    animal -> filterAnimal(user, animal)
                            )
                            .filter(animal -> adoptionRequestRepository.findByUserAndAnimal(user, animal).isEmpty())
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

    private boolean filterAnimal(User user, Animal animal) {
        boolean isInFavorites =
                favoriteRepository.findByUserAndAnimal(user, animal).isEmpty();
        boolean isInShelter = animal.getStatus() == AnimalStatus.IN_SHELTER;
        return !isInFavorites && isInShelter;
    }

    public void removeAnimalFromQueue(User user, Long animalId) {
        AnimalsQueue queue = userAnimalsQueue.get(user.getId());
        if (queue != null) {
            queue.animals.remove(animalId);
        }
    }

    public void addAnimalToQueue(User user, Long animalId) {
        AnimalsQueue queue = userAnimalsQueue.get(user.getId());
        if (queue != null && !queue.animals.contains(animalId)) {
            if (queue.animals.size() < QUEUE_SIZE) {
                queue.animals.add(animalId);
            }
        }
    }
}
