package com.nastena.pawsitive.server.animal;

import com.nastena.pawsitive.server.animal.dto.AnimalRequestDto;
import com.nastena.pawsitive.server.animal.dto.AnimalResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/animals")
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    public List<AnimalResponseDto> getAnimals(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Animal.Gender gender,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge
    ) {
        return animalService
                .getAnimalWithFilters(type, gender, minAge, maxAge)
                .stream()
                .map(AnimalResponseDto::new)
                .toList();
    }

    @PostMapping
    public AnimalResponseDto addAnimal(
            @RequestBody AnimalRequestDto dto,
            @RequestHeader("Authorization") String authHeader
    ) {
        return animalService.addAnimal(dto, authHeader);
    }
}
