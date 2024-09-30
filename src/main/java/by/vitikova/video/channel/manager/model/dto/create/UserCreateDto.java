package by.vitikova.video.channel.manager.model.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    private String nickname;
    private String name;
    private String email;
}