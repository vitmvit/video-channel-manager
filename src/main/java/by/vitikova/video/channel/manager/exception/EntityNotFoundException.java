package by.vitikova.video.channel.manager.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {
        super("Entity not found!");
    }
}