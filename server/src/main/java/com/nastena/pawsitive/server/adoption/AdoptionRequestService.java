package com.nastena.pawsitive.server.adoption;

import com.nastena.pawsitive.dto.AdoptionStatus;
import com.nastena.pawsitive.dto.ErrorCode;
import com.nastena.pawsitive.server.animal.Animal;
import com.nastena.pawsitive.server.animal.AnimalService;
import com.nastena.pawsitive.server.exceptions.ServerRuntimeException;
import com.nastena.pawsitive.server.favorite.FavoriteRepository;
import com.nastena.pawsitive.server.shelter.Shelter;
import com.nastena.pawsitive.server.user.User;
import com.nastena.pawsitive.server.user.UserAnimalsQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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

        Optional<AdoptionRequest> maybeRequest = repository.findByUserAndAnimal(user, animal);
        if (maybeRequest.isPresent() &&
                maybeRequest.get().getStatus() != AdoptionStatus.NONE &&
                maybeRequest.get().getStatus() != AdoptionStatus.CANCELED) {
            throw new ServerRuntimeException("Can't create form on active or resolved request", ErrorCode.INVALID_INPUT);
        }

        AdoptionRequest adoptionRequest;
        adoptionRequest = maybeRequest.orElseGet(AdoptionRequest::new);

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

        if (request.getStatus() != AdoptionStatus.PENDING) {
            throw new ServerRuntimeException(
                    "Can't cancel adoption request in not pending status!", ErrorCode.INVALID_INPUT
            );
        }

        request.setStatus(AdoptionStatus.CANCELED);
        repository.save(request);

        userAnimalsQueueService.addAnimalToQueue(user, animalId);
    }

    public void rejectAllByAnimal(Animal animal) {
        List<AdoptionRequest> requests = repository.findByAnimal(animal);

        for (AdoptionRequest r : requests) {
            r.setStatus(AdoptionStatus.REJECTED);
        }

        repository.saveAll(requests);
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
        return repository.findByUser(user).stream()
                .filter(request -> request.getStatus() != AdoptionStatus.CANCELED)
                .toList();
    }

    public List<AdoptionRequest> getShelterRequestsByUser(Shelter shelter) {
        return repository.findByShelter(shelter);
    }

    public AdoptionRequest getRequestOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adoption request not found"));
    }

    public void updateStatus(Long requestId, AdoptionStatus status) {

        AdoptionRequest request = repository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus(status);

        repository.save(request);
    }

}
