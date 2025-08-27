package com.spring.boot.social.controllers;

import com.spring.boot.social.dto.friendship.FriendshipStatusDto;
import com.spring.boot.social.dto.SuccessDto;
import com.spring.boot.social.services.FriendshipStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/friendship")
@RequiredArgsConstructor
public class FriendShipController {

    private final FriendshipStatusService friendshipService;

    @PostMapping("/create-friendship")
    public SuccessDto<ResponseEntity<FriendshipStatusDto>> createFriendship(@RequestParam Long friendshipId) {
        return new SuccessDto<>(
                ResponseEntity.created(URI.create("/create-friendship")).body(friendshipService.createFriendShipStatus(friendshipId))
        );
    }

    @DeleteMapping("/remove-friendship")
    public SuccessDto<ResponseEntity<String>> removeFriendship(@RequestParam Long friendshipId) {
        friendshipService.removeFriendShipStatusByFriendId(friendshipId);
        return new SuccessDto<>(
                ResponseEntity.ok("Successfully Deleted")
        );
    }

}
