package by.vitikova.video.channel.manager.controller;


import by.vitikova.video.channel.manager.model.dto.ChannelNameDto;
import by.vitikova.video.channel.manager.model.dto.UserDto;
import by.vitikova.video.channel.manager.model.dto.create.UserCreateDto;
import by.vitikova.video.channel.manager.model.dto.update.UserUpdateDto;
import by.vitikova.video.channel.manager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findById(id));
    }

    @GetMapping("/{id}/channels")
    public ResponseEntity<List<ChannelNameDto>> getChannelsSubscribe(@PathVariable("id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getChannelsSubscribe(id));
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserCreateDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(dto));
    }

    @PostMapping("/{userId}/subscription/{channelId}")
    public ResponseEntity<Void> subscribe(@PathVariable("channelId") Long channelId, @PathVariable("userId") Long userId) {
        userService.subscribe(channelId, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{userId}/unsubscription/{channelId}")
    public ResponseEntity<Void> unsubscribe(@PathVariable("channelId") Long channelId, @PathVariable("userId") Long userId) {
        userService.unsubscribe(channelId, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable("id") Long id, @RequestBody UserUpdateDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}