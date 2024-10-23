package by.vitikova.video.channel.manager.specification;

import by.vitikova.video.channel.manager.model.dto.page.ChannelFilterDto;
import by.vitikova.video.channel.manager.model.entity.Channel;
import by.vitikova.video.channel.manager.model.enums.CategoryChannel;
import by.vitikova.video.channel.manager.model.enums.LanguageChannel;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * Утилитарный класс для построения спецификаций запросов на основе фильтров для каналов.
 * Предоставляет методы для создания сложных критериев поиска каналов с учетом различных условий фильтрации.
 */
@UtilityClass
public class ChannelSpecification {

    /**
     * Создает спецификацию для поиска всех каналов на основе указанных фильтров.
     *
     * @param filter объект, содержащий фильтры для поиска каналов
     * @return {@link Specification<Channel>} спецификация для выполнения запроса поиска каналов
     */
    public static Specification<Channel> findAll(ChannelFilterDto filter) {
        Specification<Channel> spec = Specification.where(null);
        if (StringUtils.isNotEmpty(filter.name())) {
            spec = spec.and(findByName(filter.name()));
        }
        if (filter.category() != null && StringUtils.isNotEmpty(filter.category().getName())) {
            spec = spec.and(findByCategory(filter.category()));
        }
        if (filter.language() != null && StringUtils.isNotEmpty(filter.language().getName())) {
            spec = spec.and(findByLanguage(filter.language()));
        }
        return spec;
    }

    /**
     * Создает спецификацию для поиска каналов по имени.
     *
     * @param name имя канала
     * @return {@link Specification<Channel>} спецификация для выполнения поиска по имени
     */
    private static Specification<Channel> findByName(String name) {
        return (channel, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(channel.get("name"), name);
        };
    }

    /**
     * Создает спецификацию для поиска каналов по языку.
     *
     * @param language язык, по которому нужно искать каналы
     * @return {@link Specification<Channel>} спецификация для выполнения поиска по языку
     */
    private static Specification<Channel> findByLanguage(LanguageChannel language) {
        return (channel, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(channel.get("language"), language.getName());
        };
    }

    /**
     * Создает спецификацию для поиска каналов по категории.
     *
     * @param category категория, по которой нужно искать каналы
     * @return {@link Specification<Channel>} спецификация для выполнения поиска по категории
     */
    private static Specification<Channel> findByCategory(CategoryChannel category) {
        return (channel, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(channel.get("category"), category.getName());
        };
    }
}