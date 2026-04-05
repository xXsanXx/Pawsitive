package com.nastena.pawsitive.utils;

import com.nastena.pawsitive.dto.AnimalBreed;
import com.nastena.pawsitive.dto.AnimalType;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Pattern;

public class AnimalUtils {

    public static class RequestParams {
        public static final String ANIMAL_PHOTOS = "animalPhotos";
        public static final String PASSPORT_PHOTOS = "passportPhotos";
    }

    private static final Pattern NAME_REGEX = Pattern.compile("^[A-Za-zА-Яа-я\\s]{2,50}$");
    private static final HashMap<AnimalType, Set<AnimalBreed>> hashMap = new HashMap<>();

    static {
        hashMap.put(AnimalType.DOG, EnumSet.of(
                AnimalBreed.LABRADOR_RETRIEVER,
                AnimalBreed.DACHSHUND
        ));

        hashMap.put(AnimalType.CAT, EnumSet.of(
                AnimalBreed.SIAMESE,
                AnimalBreed.METIS
        ));
    }

    public static Set<AnimalBreed> getBreedForAnimalType(AnimalType type) {
        return hashMap.get(type);
    }

}
