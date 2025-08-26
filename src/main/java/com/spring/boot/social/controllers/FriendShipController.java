package com.spring.boot.social.controllers;

import com.spring.boot.social.dto.FriendShipDto;
import com.spring.boot.social.dto.SuccessDto;
import com.spring.boot.social.services.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/friendship")
@RequiredArgsConstructor
public class FriendShipController {

    private final FriendshipService friendshipService;

    @PostMapping("/create-friendship")
    public SuccessDto<ResponseEntity<FriendShipDto>> createFriendship(@RequestParam Long friendshipId) {
        return new SuccessDto<>(
                ResponseEntity.created(URI.create("/create-friendship")).body(friendshipService.createFriendShip(friendshipId))
        );
    }

}
