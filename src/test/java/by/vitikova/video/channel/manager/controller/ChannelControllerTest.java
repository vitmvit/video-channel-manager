package by.vitikova.video.channel.manager.controller;

import by.vitikova.video.channel.manager.config.PostgresSqlContainerInitializer;
import by.vitikova.video.channel.manager.model.dto.ErrorDto;
import by.vitikova.video.channel.manager.service.ChannelService;
import by.vitikova.video.channel.manager.util.ChannelTestBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ChannelControllerTest extends PostgresSqlContainerInitializer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ChannelService channelService;

    @Test
    void findByIdShouldReturnExpectedChannelDtoAndStatus200() throws Exception {
        var id = 14L;
        var expected = channelService.findById(id);

        mockMvc.perform(get("/api/channels/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void findByIdShouldReturnErrorDtoAndStatus404() throws Exception {
        var id = 15L;

        mockMvc.perform(get("/api/channels/" + id))
                .andExpect(status().isNotFound())
                .andExpect(MvcResult::getResolvedException).getClass().equals(ErrorDto.class);
    }

    @Test
    public void createShouldReturnExpectedChannelDtoAndStatus201() throws Exception {
        var channelCreateDto = ChannelTestBuilder.builder().build().buildChannelCreateDto();
        var avatar = new MockMultipartFile("avatar", "avatar.jpg", MediaType.IMAGE_JPEG_VALUE, "fake image content".getBytes());

        mockMvc.perform(multipart("/api/channels")
                        .file(avatar)
                        .param("name", channelCreateDto.getName())
                        .param("description", channelCreateDto.getDescription())
                        .param("category", channelCreateDto.getCategory().getName())
                        .param("mainLanguage", channelCreateDto.getMainLanguage().getName())
                        .param("authorId", String.valueOf(channelCreateDto.getAuthorId()))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(channelCreateDto.getName()))
                .andExpect(jsonPath("$.description").value(channelCreateDto.getDescription()))
                .andExpect(jsonPath("$.authorId").value(channelCreateDto.getAuthorId()))
                .andExpect(jsonPath("$.category").value(channelCreateDto.getCategory().getName()))
                .andExpect(jsonPath("$.mainLanguage").value(channelCreateDto.getMainLanguage().getName()));
    }

    @Test
    public void updateShouldReturnExpectedChannelDtoAndStatus200() throws Exception {
        var id = 14L;
        var channelUpdateDto = ChannelTestBuilder.builder().build().buildChannelUpdateDto();
        var expected = channelService.update(id, channelUpdateDto);

        mockMvc.perform(put("/api/channels/" + id)
                        .content(objectMapper.writeValueAsString(channelUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void deleteShouldReturnStatus204() throws Exception {
        var id = 14L;

        mockMvc.perform(delete("/api/channels/" + id))
                .andExpect(status().isNoContent());
    }
}