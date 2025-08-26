package com.spring.boot.social.controllers;

import com.spring.boot.social.services.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friendship")
@RequiredArgsConstructor
public class FriendShipController {

    private final FriendshipService friendshipService;

}
