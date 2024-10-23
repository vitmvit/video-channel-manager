package by.vitikova.video.channel.manager.util;

import by.vitikova.video.channel.manager.model.dto.page.PageParamDto;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Утилитарный класс для управления пагинацией.
 * Предоставляет методы для создания объектов Pageable на основе параметров страницы.
 */
@UtilityClass
public class PageUtils {

    /**
     * Создает объект Pageable на основе заданных параметров страницы.
     *
     * @param param параметры страницы, включая номер страницы и размер страницы
     * @return {@link Pageable} объект, представляющий параметры пагинации для запроса
     */
    public static Pageable page(PageParamDto param) {
        return buildPageable(param.pageNumber(), param.pageSize());
    }

    /**
     * Создает объект Pageable с учетом номера страницы и размера страницы.
     *
     * @param pageNumber номер страницы
     * @param pageSize   размер страницы
     * @return {@link Pageable} объект, представляющий параметры пагинации
     */
    private Pageable buildPageable(int pageNumber, int pageSize) {
        return PageRequest.of(
                pageNumber - 1,
                pageSize
        );
    }
}