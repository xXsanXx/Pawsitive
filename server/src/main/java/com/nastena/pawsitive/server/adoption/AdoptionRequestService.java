package com.nastena.pawsitive.server.adoption;

import com.nastena.pawsitive.dto.AdoptionStatus;
import com.nastena.pawsitive.dto.ErrorCode;
import com.nastena.pawsitive.server.animal.Animal;
import com.nastena.pawsitive.server.animal.AnimalService;
import com.nastena.pawsitive.server.exceptions.ServerRuntimeException;
import com.nastena.pawsitive.server.favorite.FavoriteRepository;
import com.nastena.pawsitive.server.user.User;
import com.nastena.pawsitive.server.user.UserAnimalsQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdoptionRequestService {

    @Autowired
    private AdoptionRequestRepository repository;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserAnimalsQueueService userAnimalsQueueService;


    public void createFormOrThrow(User user, Long animalId) throws ServerRuntimeException{

        Animal animal = animalService.getAnimalOrThrow(animalId);

        if (repository.findByUserAndAnimal(user, animal).isPresent()) {
            throw new ServerRuntimeException("Form already exists", ErrorCode.INVALID_INPUT);
        }

        AdoptionRequest adoptionRequest = new AdoptionRequest();
        adoptionRequest.setUser(user);
        adoptionRequest.setAnimal(animal);
        adoptionRequest.setStatus(AdoptionStatus.PENDING);

        repository.save(adoptionRequest);

        userAnimalsQueueService.removeAnimalFromQueue(user, animalId);

    }

    public void cancelAdoptionRequest(User user, Long animalId) {
        Animal animal = animalService.getAnimalOrThrow(animalId);

        AdoptionRequest request = repository.findByUserAndAnimal(user, animal)
                .orElseThrow(() -> new ServerRuntimeException("Adoption request not found", ErrorCode.INVALID_INPUT));

        repository.delete(request);

    }

    public AdoptionStatus getStatus(User user, Long animalId) {
        Animal animal = animalService.getAnimalOrThrow(animalId);
        return getStatus(user, animal);
    }

    public AdoptionStatus getStatus(User user, Animal animal) {
        Optional<AdoptionRequest> maybeRequest = repository.findByUserAndAnimal(user, animal);
        if (maybeRequest.isEmpty()) {
            return AdoptionStatus.NONE;
        }

        return maybeRequest.get().getStatus();
    }


    public List<AdoptionRequest> getRequestsByUser(User user) {
        return repository.findByUser(user);
    }


}
