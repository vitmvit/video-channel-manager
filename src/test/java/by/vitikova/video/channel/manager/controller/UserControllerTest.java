package by.vitikova.video.channel.manager.controller;

import by.vitikova.video.channel.manager.config.PostgresSqlContainerInitializer;
import by.vitikova.video.channel.manager.model.dto.ErrorDto;
import by.vitikova.video.channel.manager.service.UserService;
import by.vitikova.video.channel.manager.util.UserTestBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest extends PostgresSqlContainerInitializer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    void findByIdShouldReturnExpectedUserDtoAndStatus200() throws Exception {
        var id = 3L;
        var expected = userService.findById(id);

        mockMvc.perform(get("/api/users/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void findByIdShouldReturnErrorDtoAndStatus404() throws Exception {
        var id = 4L;

        mockMvc.perform(get("/api/users/" + id))
                .andExpect(status().isNotFound())
                .andExpect(MvcResult::getResolvedException).getClass().equals(ErrorDto.class);
    }

    @Test
    public void getChannelsSubscribeShouldReturnListChannels() throws Exception {
        var userId = 3L;

        mockMvc.perform(get("/api/users/{id}/channels", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void getChannelsSubscribeShouldReturnErrorDtoAndStatus404() throws Exception {
        var userId = 4L;

        mockMvc.perform(get("/api/users/{id}/channels", userId))
                .andExpect(status().isNotFound())
                .andExpect(MvcResult::getResolvedException).getClass().equals(ErrorDto.class);
    }

    @Test
    public void createShouldReturnExpectedUserDtoAndStatus201() throws Exception {
        var userCreateDto = UserTestBuilder.builder().build().buildUserCreateDto();

        mockMvc.perform(post("/api/users")
                        .content(objectMapper.writeValueAsString(userCreateDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nickname").value(userCreateDto.getNickname()))
                .andExpect(jsonPath("$.name").value(userCreateDto.getName()))
                .andExpect(jsonPath("$.email").value(userCreateDto.getEmail()));
    }

    @Test
    public void subscribeShouldCallUserServiceAndReturn204() throws Exception {
        var userId = 3L;
        var channelId = 14L;

        mockMvc.perform(post("/api/users/" + userId + "/subscription/" + channelId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void subscribeShouldReturnErrorDtoAndStatus422() throws Exception {
        var userId = 4L;
        var channelId = 4L;

        mockMvc.perform(post("/api/users/" + userId + "/subscription/" + channelId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MvcResult::getResolvedException).getClass().equals(ErrorDto.class);
    }

    @Test
    public void unsubscribeShouldCallUserServiceAndReturn204() throws Exception {
        var userId = 3L;
        var channelId = 14L;

        mockMvc.perform(post("/api/users/" + userId + "/unsubscription/" + channelId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void unsubscribeShouldReturnErrorDtoAndStatus422() throws Exception {
        var userId = 3L;
        var channelId = 4L;

        mockMvc.perform(post("/api/users/" + userId + "/subscription/" + channelId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MvcResult::getResolvedException).getClass().equals(ErrorDto.class);
    }

    @Test
    public void updateShouldReturnExpectedUpdateDtoAndStatus200() throws Exception {
        var id = 3L;
        var userUpdateDto = UserTestBuilder.builder().build().buildUserUpdateDto();
        var expected = userService.update(id, userUpdateDto);

        mockMvc.perform(put("/api/users/" + id)
                        .content(objectMapper.writeValueAsString(userUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void deleteShouldReturnStatus204() throws Exception {
        var id = 3L;

        mockMvc.perform(delete("/api/users/" + id))
                .andExpect(status().isNoContent());
    }
}