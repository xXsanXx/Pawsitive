package com.nastena.pawsitive.utils;

import com.nastena.pawsitive.dto.AnimalBreed;
import com.nastena.pawsitive.dto.AnimalType;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;

public class AnimalUtils {

    private static final HashMap<AnimalType, Set<AnimalBreed>> hashMap = new HashMap<>();

    static {
        hashMap.put(AnimalType.DOG, EnumSet.of(
                AnimalBreed.LABRADOR_RETRIEVER,
                AnimalBreed.GOLDEN_RETRIEVER,
                AnimalBreed.DACHSHUND
        ));

        hashMap.put(AnimalType.CAT, EnumSet.of(
                AnimalBreed.SPHYNX,
                AnimalBreed.SIAMESE,
                AnimalBreed.METIS
        ));
    }

    public Set<AnimalBreed> getBreedForAnimalType(AnimalType type) {
        return hashMap.get(type);
    }

}
