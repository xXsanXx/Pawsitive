package com.nastena.pawsitive.server.animal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public List<Animal> getAll() {
        return animalServer.getAllAnimals();
    }
}
