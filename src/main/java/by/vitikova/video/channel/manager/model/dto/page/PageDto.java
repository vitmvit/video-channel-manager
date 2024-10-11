package by.vitikova.video.channel.manager.model.dto.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageDto {

    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElements;

    public PageDto() {
        this.totalPages = 0;
        this.totalElements = 0L;
    }
}