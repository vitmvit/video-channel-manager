package by.vitikova.video.channel.manager.service.impl;

import by.vitikova.video.channel.manager.converter.ChannelConverter;
import by.vitikova.video.channel.manager.exception.EntityNotFoundException;
import by.vitikova.video.channel.manager.exception.OperationException;
import by.vitikova.video.channel.manager.model.dto.ChannelDto;
import by.vitikova.video.channel.manager.model.dto.ChannelInfoDto;
import by.vitikova.video.channel.manager.model.dto.create.ChannelCreateDto;
import by.vitikova.video.channel.manager.model.dto.page.ChannelFilterDto;
import by.vitikova.video.channel.manager.model.dto.page.PageContentDto;
import by.vitikova.video.channel.manager.model.dto.page.PageDto;
import by.vitikova.video.channel.manager.model.dto.page.PageParamDto;
import by.vitikova.video.channel.manager.model.dto.update.ChannelUpdateDto;
import by.vitikova.video.channel.manager.model.entity.Channel;
import by.vitikova.video.channel.manager.repository.ChannelRepository;
import by.vitikova.video.channel.manager.service.ChannelService;
import by.vitikova.video.channel.manager.specification.ChannelSpecification;
import by.vitikova.video.channel.manager.util.FileUtils;
import by.vitikova.video.channel.manager.util.PageUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Реализация сервиса канала.
 */
@Service
@AllArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final ChannelConverter channelConverter;

    /**
     * Находит канал по идентификатору.
     *
     * @param id идентификатор канала.
     * @return объект {@link ChannelDto}, содержащий информацию о канале.
     * @throws EntityNotFoundException если канал с данным идентификатором не найден.
     */
    @Override
    public ChannelDto findById(Long id) {
        return channelConverter.convertToDto(channelRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    /**
     * Получает страницу каналов с возможностью фильтрации.
     *
     * @param param  параметры страницы, включая номер страницы и размер страницы
     * @param filter объект, содержащий критерии фильтрации каналов
     * @return {@link PageContentDto<ChannelInfoDto>} объект, содержащий информацию о странице и списке каналов
     * (пустой список, если каналы не найдены)
     */
    @Override
    public PageContentDto<ChannelInfoDto> getAll(PageParamDto param, ChannelFilterDto filter) {
        var pageable = PageUtils.page(param);
        var specification = Specification.where(ChannelSpecification.findAll(filter));
        Page<Channel> page = channelRepository.findAll(specification, pageable);
        return new PageContentDto<>(
                new PageDto(param.pageNumber(), param.pageSize(), page.getTotalPages(), page.getTotalElements()),
                page.getContent().isEmpty()
                        ? List.of()
                        : channelConverter.convertToInfoList(page.getContent())
        );
    }

    /**
     * Создает новый канал.
     *
     * @param dto    объект {@link ChannelCreateDto} с данными для создания канала.
     * @param avatar аватарка
     * @return объект {@link ChannelDto}, содержащий информацию о созданном канале.
     * @throws OperationException если произошла ошибка при создании канала.
     */
    @Override
    @Transactional
    public ChannelDto create(ChannelCreateDto dto, MultipartFile avatar) {
        try {
            var channel = channelConverter.convert(dto);
            channel.setAvatar(FileUtils.convertMultipartFileToBase64(avatar));
            return channelConverter.convertToDto(channelRepository.save(channel));
        } catch (Exception ex) {
            throw new OperationException("Channel create error: " + ex.getMessage());
        }
    }

    /**
     * Обновляет существующий канал с заданным идентификатором.
     *
     * @param id  уникальный идентификатор канала, который требуется обновить
     * @param dto объект, содержащий данные обновления канала
     * @return {@link ChannelDto} обновленный объект канала, конвертированный в DTO
     * @throws EntityNotFoundException если канал с заданным идентификатором не найден
     * @throws OperationException      если произошла ошибка при обновлении канала
     */
    @Override
    @Transactional
    public ChannelDto update(Long id, ChannelUpdateDto dto) {
        try {
            var channel = channelRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            return channelConverter.convertToDto(channelRepository.save(channelConverter.merge(channel, dto)));
        } catch (Exception ex) {
            throw new OperationException("Channel update error: " + ex.getMessage());
        }
    }

    /**
     * Обновляет аватар канала.
     * <p>
     * Этот метод принимает идентификатор канала и новый аватар в формате
     * мультипарт файла. Если канал с указанным идентификатором не найден,
     * выбрасывается исключение {@link EntityNotFoundException}. После
     * успешного обновления аватар сохраняется в виде строки Base64.
     *
     * @param id     идентификатор канала, для которого необходимо обновить аватар
     * @param avatar новый аватар в формате {@link MultipartFile}, который
     *               будет преобразован в строку Base64
     * @return объект {@link ChannelDto}, представляющий обновленный канал
     * @throws OperationException если произошла ошибка при обновлении канала,
     *                            например, если не удается преобразовать аватар
     */
    @Override
    public ChannelDto updateAvatar(Long id, MultipartFile avatar) {
        try {
            var channel = channelRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            channel.setAvatar(FileUtils.convertMultipartFileToBase64(avatar));
            return channelConverter.convertToDto(channelRepository.save(channel));
        } catch (Exception ex) {
            throw new OperationException("Channel update error: " + ex.getMessage());
        }
    }

    /**
     * Удаляет канал по идентификатору.
     *
     * @param id идентификатор канала.
     */
    @Override
    @Transactional
    public void delete(Long id) {
        channelRepository.deleteById(id);
    }
}