package com.nastena.pawsitive.server.favorite;
import com.nastena.pawsitive.server.favorite.dto.FavoriteResponseDto;
import com.nastena.pawsitive.server.security.UserAuthService;
import com.nastena.pawsitive.server.user.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserAuthService userAuthService;

    public FavoriteController(
            FavoriteService favoriteService,
            UserAuthService userAuthService
    ) {
        this.favoriteService = favoriteService;
        this.userAuthService = userAuthService;
    }

    @PostMapping("/{animalId}")
    public void addToFavorites(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long animalId
    ) {
        User user = userAuthService.getUserFromToken(authHeader);
        favoriteService.addToFavorites(user, animalId);
    }

    @DeleteMapping("/{animalId}")
    public void removeFromFavorites(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long animalId
    ) {
        User user = userAuthService.getUserFromToken(authHeader);
        favoriteService.removeFromFavorites(user, animalId);
    }

    @GetMapping
    public List<FavoriteResponseDto> getFavorites(
            @RequestHeader("Authorization") String authHeader
    ) {
        User user = userAuthService.getUserFromToken(authHeader);
        return favoriteService.getUserFavoritesDto(user);
    }
}

