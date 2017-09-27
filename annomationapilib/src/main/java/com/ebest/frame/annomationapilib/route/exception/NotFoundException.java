package com.ebest.frame.annomationapilib.route.exception;

/**
 * The custom exception represents not found
 */
public class NotFoundException extends RuntimeException {

    /**
     * This type represents not matching to the corresponding uri routing rules
     */
    public static final int TYPE_SCHEMA = 0;
    /**
     * This type represents the uri matching to the routing target does not exist
     */
    public static final int TYPE_CLZ = 1;

    private final int type;
    private final String notFoundName;

    /**
     * @param detailMessage detail error message
     * @param type one of {@link NotFoundException#TYPE_SCHEMA} and {@link NotFoundException#TYPE_CLZ}
     * @param notFoundName The routing target name matched with uri.
     * @see NotFoundException#TYPE_SCHEMA
     * @see NotFoundException#TYPE_CLZ
     */
    public NotFoundException(String detailMessage, int type, String notFoundName) {
        super(detailMessage);
        this.type = type;
        this.notFoundName = notFoundName;
    }

    /**
     * @return the type of not found. it could be one of the {@link NotFoundException#TYPE_SCHEMA} and {@link NotFoundException#TYPE_SCHEMA}
     * @see NotFoundException#TYPE_SCHEMA
     * @see NotFoundException#TYPE_CLZ
     */
    public int getType() {
        return type;
    }

    /**
     * @return the uri matching routing target class name.
     */
    public String getNotFoundName() {
        return notFoundName;
    }

}
