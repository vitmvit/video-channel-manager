package by.vitikova.video.channel.manager.model.enums;

import lombok.Getter;

@Getter
public enum LanguageChannel {

    RU("RU"),
    ENGLISH("ENGLISH");

    private final String language;

    LanguageChannel(String language) {
        this.language = language;
    }
}