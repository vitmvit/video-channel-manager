package by.vitikova.video.channel.manager.model.enums;

public enum CategoryChannel {

    ENTERTAINMENT("ENTERTAINMENT"),
    EDUCATION("EDUCATION"),
    TECHNOLOGY("TECHNOLOGY"),
    HEALTH_AND_FITNESS("HEALTH_AND_FITNESS"),
    TRAVEL("TRAVEL"),
    PERSONAL_DEVELOPMENT("PERSONAL_DEVELOPMENT"),
    BUSINESS_AND_FINANCE("BUSINESS_AND_FINANCE"),
    SOCIAL_AND_ENVIRONMENTAL_ISSUES("SOCIAL_AND_ENVIRONMENTAL_ISSUES"),
    HOBBIES_AND_INTERESTS("HOBBIES_AND_INTERESTS"),
    NEWS_AND_EVENTS("NEWS_AND_EVENTS"),
    MUSIC("MUSIC");

    private final String category;

    CategoryChannel(String category) {
        this.category = category;
    }
}