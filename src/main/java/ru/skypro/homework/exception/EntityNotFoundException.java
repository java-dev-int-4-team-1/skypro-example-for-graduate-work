package ru.skypro.homework.exception;

public class EntityNotFoundException extends RuntimeException {
    /**
     * @param type class of entity for which entry wasn't found in the db
     * @param id id of entry for which there is no entry in the db
    */
    public EntityNotFoundException(Class type, int id) {
        super("The " + type.getName() +
                " with id=" + id +
                " is not listed in the db.");
    }
}