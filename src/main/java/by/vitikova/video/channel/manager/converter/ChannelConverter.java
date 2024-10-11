package by.vitikova.video.channel.manager.converter;

import by.vitikova.video.channel.manager.model.dto.ChannelDto;
import by.vitikova.video.channel.manager.model.dto.ChannelInfoDto;
import by.vitikova.video.channel.manager.model.dto.ChannelNameDto;
import by.vitikova.video.channel.manager.model.dto.create.ChannelCreateDto;
import by.vitikova.video.channel.manager.model.dto.update.ChannelUpdateDto;
import by.vitikova.video.channel.manager.model.entity.Channel;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChannelConverter {

    ChannelNameDto convert(Channel source);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "authorId", source = "authorId"),
            @Mapping(target = "subscribersCount", expression = "java(source.getSubscribers() != null ? source.getSubscribers().size() : 0)"),
            @Mapping(target = "creationDate", source = "creationDate"),
            @Mapping(target = "mainLanguage", source = "mainLanguage"),
            @Mapping(target = "avatar", source = "avatar"),
            @Mapping(target = "category", source = "category")
    })
    ChannelDto convertToDto(Channel source);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "subscribersCount", expression = "java(source.getSubscribers() != null ? source.getSubscribers().size() : 0)"),
            @Mapping(target = "mainLanguage", source = "mainLanguage"),
            @Mapping(target = "avatar", source = "avatar"),
            @Mapping(target = "category", source = "category")
    })
    ChannelInfoDto convertToInfo(Channel source);

    List<ChannelInfoDto> convertToInfoList(List<Channel> source);

    Channel convert(ChannelCreateDto source);

    Channel merge(@MappingTarget Channel channel, ChannelUpdateDto dto);
}