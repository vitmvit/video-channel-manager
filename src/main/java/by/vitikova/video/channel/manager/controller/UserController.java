package by.vitikova.video.channel.manager.controller;


import by.vitikova.video.channel.manager.model.dto.ChannelNameDto;
import by.vitikova.video.channel.manager.model.dto.UserDto;
import by.vitikova.video.channel.manager.model.dto.create.UserCreateDto;
import by.vitikova.video.channel.manager.model.dto.update.UserUpdateDto;
import by.vitikova.video.channel.manager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/channels")
    public List<ChannelNameDto> getChannelsSubscribe(@PathVariable("id") Long id) {
        return userService.getSubscribedChannels(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserCreateDto dto) {
        return userService.create(dto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{userId}/subscribes/{channelId}")
    public void subscribe(@PathVariable("channelId") Long channelId, @PathVariable("userId") Long userId) {
        userService.subscribe(channelId, userId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}/subscribes/{channelId}")
    public void unsubscribe(@PathVariable("channelId") Long channelId, @PathVariable("userId") Long userId) {
        userService.unsubscribe(channelId, userId);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto update(@PathVariable("id") Long id, @RequestBody UserUpdateDto dto) {
        return userService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }
}