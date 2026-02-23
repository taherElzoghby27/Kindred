package com.spring.boot.social.controllers;

import com.spring.boot.social.dto.SuccessDto;
import com.spring.boot.social.dto.friendship.FriendshipStatusDto;
import com.spring.boot.social.services.friendship.FriendshipStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/friendship")
@RequiredArgsConstructor
@Tag(name = "Friendship", description = "Friendship management APIs")
public class FriendShipController {

    private final FriendshipStatusService friendshipService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Operation(summary = "Create Friendship", description = "Create a new friendship request")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Friendship created successfully", content = @Content(schema = @Schema(implementation = FriendshipStatusDto.class))), @ApiResponse(responseCode = "400", description = "Invalid friendship ID"), @ApiResponse(responseCode = "409", description = "Friendship already exists")})
    @PostMapping
    public ResponseEntity<SuccessDto<FriendshipStatusDto>> createFriendship(@RequestParam("friend_id") Long friendId) {
        FriendshipStatusDto result = friendshipService.createFriendShipStatus(friendId);
        if (result.getFriendship() != null && result.getFriendship().getAccount() != null && result.getFriendship().getFriend() != null) {
            //send with socket
            simpMessagingTemplate.convertAndSendToUser(
                    result.getFriendship().getFriend().getUsername(),
                    "/listener/notification",
                    result
            );
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessDto<>(result));
    }

    @Operation(summary = "Update Friendship", description = "Update friendship status")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Friendship updated successfully"), @ApiResponse(responseCode = "400", description = "Invalid input data"), @ApiResponse(responseCode = "404", description = "Friendship not found")})
    @PutMapping
    public ResponseEntity<SuccessDto<String>> updateFriendship(@RequestParam("friendship_id") Long id, @RequestParam String status) {
        friendshipService.updateFriendshipStatus(id, status);
        return ResponseEntity.ok(new SuccessDto<>("Friendship updated successfully"));
    }

    @Operation(summary = "Remove Friendship", description = "Remove a friendship by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Friendship removed successfully"), @ApiResponse(responseCode = "400", description = "Invalid friendship ID"), @ApiResponse(responseCode = "404", description = "Friendship not found")})
    @DeleteMapping
    public ResponseEntity<SuccessDto<String>> removeFriendship(@RequestParam("friend_id") Long friendId) {
        friendshipService.removeFriendShipStatusByFriendId(friendId);
        return ResponseEntity.ok(new SuccessDto<>("Successfully Deleted"));
    }

    @Operation(summary = "Get Friendships by Status", description = "Retrieve friendships by status")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Friendships retrieved successfully", content = @Content(schema = @Schema(implementation = FriendshipStatusDto.class))), @ApiResponse(responseCode = "400", description = "Invalid status parameter")})
    @GetMapping("/{status}")
    public ResponseEntity<SuccessDto<List<FriendshipStatusDto>>> getFriendshipStatusByStatus(@PathVariable String status) {
        return ResponseEntity.ok(new SuccessDto<>(friendshipService.getFriendshipStatusByStatus(status)));
    }
}
