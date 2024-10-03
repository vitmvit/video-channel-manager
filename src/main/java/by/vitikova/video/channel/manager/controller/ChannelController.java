package by.vitikova.video.channel.manager.controller;


import by.vitikova.video.channel.manager.model.dto.ChannelDto;
import by.vitikova.video.channel.manager.model.dto.ChannelInfoDto;
import by.vitikova.video.channel.manager.model.dto.create.ChannelCreateDto;
import by.vitikova.video.channel.manager.model.dto.update.ChannelUpdateDto;
import by.vitikova.video.channel.manager.model.enums.CategoryChannel;
import by.vitikova.video.channel.manager.model.enums.LanguageChannel;
import by.vitikova.video.channel.manager.service.ChannelService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/channels")
public class ChannelController {

    private final ChannelService channelService;

    @GetMapping("/{id}")
    public ResponseEntity<ChannelDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(channelService.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ChannelDto> findByName(@PathVariable("name") String name) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(channelService.findByName(name));
    }

    @GetMapping
    public ResponseEntity<Page<ChannelInfoDto>> getAll(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                       @RequestParam(value = "limit", defaultValue = "15") Integer limit,
                                                       @RequestParam(value = "name", required = false) String name,
                                                       @RequestParam(value = "language", required = false) LanguageChannel language,
                                                       @RequestParam(value = "category", required = false) CategoryChannel category) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(channelService.getAll(offset, limit, name, language, category));
    }

    @PostMapping
    public ResponseEntity<ChannelDto> create(@RequestBody ChannelCreateDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(channelService.create(dto));
    }

    @PutMapping
    public ResponseEntity<ChannelDto> update(@RequestBody ChannelUpdateDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(channelService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        channelService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}