package by.vitikova.video.channel.manager.repository;

import by.vitikova.video.channel.manager.model.entity.Channel;
import by.vitikova.video.channel.manager.model.enums.CategoryChannel;
import by.vitikova.video.channel.manager.model.enums.LanguageChannel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    Optional<Channel> findByName(String name);

    @Query("SELECT c FROM Channel c WHERE " +
            "(:name IS NULL OR c.name LIKE %:name%) AND " +
            "(:language IS NULL OR c.mainLanguage = :language) AND " +
            "(:category IS NULL OR c.category = :category)")
    Page<Channel> findAllByNameAndMainLanguageAndCategory(Pageable pageable,
                                                          @Param("name") String name,
                                                          @Param("language") LanguageChannel language,
                                                          @Param("category") CategoryChannel category);

    void deleteAllByAuthorId(Long id);
}