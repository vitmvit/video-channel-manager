package by.vitikova.video.channel.manager.repository;

import by.vitikova.video.channel.manager.config.PostgresSqlContainerInitializer;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@RequiredArgsConstructor
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ChannelRepositoryTest extends PostgresSqlContainerInitializer {

    @Autowired
    private ChannelRepository houseRepository;

    @Test
    void deleteAllByAuthorIdShouldDeleteChannelsByUserId() {
        var authorId = 3L;
        var channelId = 14L;

        houseRepository.deleteAllByAuthorId(authorId);

        assertEquals(Optional.empty(), houseRepository.findById(channelId));
    }
}