package group5.swp391.onlinelearning.exception;

public class InvalidInputException extends Exception {
    private final String fieldName;
    private final String errorCode;

    public InvalidInputException(String fieldName, String errorCode, String defaultMessage) {
        super(defaultMessage);
        this.fieldName = fieldName;
        this.errorCode = errorCode;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
