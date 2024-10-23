package by.vitikova.video.channel.manager.controller;


import by.vitikova.video.channel.manager.model.dto.ChannelDto;
import by.vitikova.video.channel.manager.model.dto.ChannelInfoDto;
import by.vitikova.video.channel.manager.model.dto.create.ChannelCreateDto;
import by.vitikova.video.channel.manager.model.dto.page.ChannelFilterDto;
import by.vitikova.video.channel.manager.model.dto.page.PageContentDto;
import by.vitikova.video.channel.manager.model.dto.page.PageParamDto;
import by.vitikova.video.channel.manager.model.dto.update.ChannelUpdateDto;
import by.vitikova.video.channel.manager.model.enums.CategoryChannel;
import by.vitikova.video.channel.manager.model.enums.LanguageChannel;
import by.vitikova.video.channel.manager.service.ChannelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/api/channels")
public class ChannelController {

    private final ChannelService channelService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ChannelDto findById(@PathVariable("id") Long id) {
        return channelService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageContentDto<ChannelInfoDto> getAll(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize,
                                                 @RequestParam(value = "name", required = false) String name,
                                                 @RequestParam(value = "language", required = false) LanguageChannel language,
                                                 @RequestParam(value = "category", required = false) CategoryChannel category) {
        return channelService.getAll(new PageParamDto(pageNumber, pageSize), new ChannelFilterDto(name, language, category));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChannelDto create(@ModelAttribute ChannelCreateDto dto, @RequestParam("avatar") MultipartFile avatar) {
        return channelService.create(dto, avatar);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ChannelDto update(@PathVariable("id") Long id, @RequestBody ChannelUpdateDto dto) {
        return channelService.update(id, dto);
    }

    @PutMapping("/{id}/avatar")
    @ResponseStatus(HttpStatus.OK)
    public ChannelDto updateAvatar(@PathVariable("id") Long id, @RequestParam("avatar") MultipartFile avatar) {
        return channelService.updateAvatar(id, avatar);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        channelService.delete(id);
    }
}