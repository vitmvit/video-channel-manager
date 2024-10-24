package by.vitikova.video.channel.manager.repository;

import by.vitikova.video.channel.manager.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}