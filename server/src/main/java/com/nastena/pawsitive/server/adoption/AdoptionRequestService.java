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
        adoptionRequest.setHiddenByShelter(false);
        adoptionRequest.setHiddenByUser(false);

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
        request.setHiddenByUser(false);
        request.setHiddenByShelter(false);
        repository.save(request);

        userAnimalsQueueService.addAnimalToQueue(user, animalId);
    }

    public void rejectAllByAnimal(Animal animal) {
        List<AdoptionRequest> requests = repository.findByAnimal(animal);

        for (AdoptionRequest r : requests) {
            r.setStatus(AdoptionStatus.REJECTED);
            r.setHiddenByShelter(false);
            r.setHiddenByUser(false);
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
                .filter(request -> request.getStatus() != AdoptionStatus.CANCELED && !request.isHiddenByUser())
                .toList();
    }

    public List<AdoptionRequest> getVisibleRequestsByShelter(Shelter shelter) {
        return repository.findByShelter(shelter).stream()
                .filter(request -> !request.isHiddenByShelter())
                .toList();
    }

    public AdoptionRequest getRequestOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ServerRuntimeException("Adoption request not found", ErrorCode.INVALID_INPUT));
    }

    public void updateStatusOrThrow(Long requestId, AdoptionStatus status) {

        AdoptionRequest request = repository.findById(requestId)
                .orElseThrow(() -> new ServerRuntimeException("Request not found", ErrorCode.INVALID_INPUT));

        request.setStatus(status);
        request.setHiddenByShelter(false);
        request.setHiddenByUser(false);

        repository.save(request);
    }

    public void hideUserRequestOrThrow(Long requestId) {
        AdoptionRequest request = repository.findById(requestId)
                .orElseThrow(() -> new ServerRuntimeException("Request not found", ErrorCode.INVALID_INPUT));

        if (request.getStatus() == AdoptionStatus.PENDING) {
            throw new ServerRuntimeException("Can't hide pending request!", ErrorCode.INVALID_INPUT);
        }

        request.setHiddenByUser(true);

        repository.save(request);
    }

    public void hideShelterRequestOrThrow(Long requestId) {
        AdoptionRequest request = repository.findById(requestId)
                .orElseThrow(() -> new ServerRuntimeException("Request not found", ErrorCode.INVALID_INPUT));

        if (request.getStatus() == AdoptionStatus.PENDING) {
            throw new ServerRuntimeException("Can't hide pending request!", ErrorCode.INVALID_INPUT);
        }

        request.setHiddenByShelter(true);

        repository.save(request);
    }

}
