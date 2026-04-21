package com.nastena.pawsitive.server.adoption;

import com.nastena.pawsitive.dto.AdoptionStatus;
import com.nastena.pawsitive.dto.ErrorCode;
import com.nastena.pawsitive.server.animal.Animal;
import com.nastena.pawsitive.server.animal.AnimalService;
import com.nastena.pawsitive.server.exceptions.ServerRuntimeException;
import com.nastena.pawsitive.server.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdoptionRequestService {

    @Autowired
    private AdoptionRequestRepository repository;
    @Autowired
    private AnimalService animalService;


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

        repository.deleteByUserAndAnimal(user, animal);
    }

    public List<AdoptionRequest> getRequestsByUser(User user) {
        return repository.findByUser(user);
    }


}
