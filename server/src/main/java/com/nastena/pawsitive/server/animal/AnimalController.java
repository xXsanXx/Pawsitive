package com.nastena.pawsitive.server.animal;

import com.nastena.pawsitive.server.animal.dto.AnimalResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/animals")
public class AnimalController {
    private final AnimalServer animalServer;

    public AnimalController(AnimalServer animalServer) {
        this.animalServer = animalServer;
    }

    @GetMapping
    public List<AnimalResponseDto> getAll() {
        return animalServer.getAllAnimals()
                .stream()
                .map(AnimalResponseDto::new)
                .toList();
    }

    @GetMapping
    public List<AnimalResponseDto> getAnimals(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Animal.Gender gender,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge
    ) {
        return animalServer
                .getAnimalWithFilters(type, gender, minAge, maxAge)
                .stream()
                .map(AnimalResponseDto::new)
                .toList();
    }
}
