package com.spring.boot.social.controllers;

import com.spring.boot.social.dto.SuccessDto;
import com.spring.boot.social.dto.friendship.FriendshipStatusDto;
import com.spring.boot.social.services.friendship.FriendshipStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/friendship")
@RequiredArgsConstructor
@Tag(name = "Friendship", description = "Friendship management APIs")
public class FriendShipController {

    private final FriendshipStatusService friendshipService;

    @Operation(summary = "Create Friendship", description = "Create a new friendship request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Friendship created successfully",
                    content = @Content(schema = @Schema(implementation = FriendshipStatusDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid friendship ID"),
            @ApiResponse(responseCode = "409", description = "Friendship already exists")
    })
    @PostMapping
    public SuccessDto<ResponseEntity<FriendshipStatusDto>> createFriendship(@RequestParam("friend_id") Long friendId) {
        return new SuccessDto<>(
                ResponseEntity.created(URI.create("/create-friendship")).body(friendshipService.createFriendShipStatus(friendId))
        );
    }

    @Operation(summary = "Update Friendship", description = "Update friendship status")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Friendship updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "404", description = "Friendship not found")
            }
    )
    @PutMapping
    public SuccessDto<ResponseEntity<String>> updateFriendship(
            @RequestParam("friendship_id") Long id,
            @RequestParam String status
    ) {
        friendshipService.updateFriendshipStatus(id, status);
        return new SuccessDto<>(
                ResponseEntity.ok("Friendship updated successfully")
        );
    }

    @Operation(summary = "Remove Friendship", description = "Remove a friendship by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Friendship removed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid friendship ID"),
            @ApiResponse(responseCode = "404", description = "Friendship not found")
    })
    @DeleteMapping
    public SuccessDto<ResponseEntity<String>> removeFriendship(@RequestParam("friend_id") Long friendId) {
        friendshipService.removeFriendShipStatusByFriendId(friendId);
        return new SuccessDto<>(
                ResponseEntity.ok("Successfully Deleted")
        );
    }

    @Operation(summary = "Get Friendships by Status", description = "Retrieve friendships by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Friendships retrieved successfully",
                    content = @Content(schema = @Schema(implementation = FriendshipStatusDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid status parameter")
    })
    @GetMapping("/{status}")
    public SuccessDto<ResponseEntity<List<FriendshipStatusDto>>> getFriendshipStatusByStatus(@PathVariable String status) {
        return new SuccessDto<>(
                ResponseEntity.ok(
                        friendshipService.getFriendshipStatusByStatus(status)
                )
        );
    }
}
