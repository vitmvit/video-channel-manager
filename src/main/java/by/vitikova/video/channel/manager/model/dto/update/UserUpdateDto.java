package by.vitikova.video.channel.manager.model.dto.update;

import by.vitikova.video.channel.manager.model.dto.create.UserCreateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto extends UserCreateDto {

    private Long id;
}