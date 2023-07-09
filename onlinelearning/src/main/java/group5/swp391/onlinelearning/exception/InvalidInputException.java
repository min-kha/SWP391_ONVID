package group5.swp391.onlinelearning.exception;

public class InvalidInputException extends Exception {
    private final String fieldName;
    private final String errorCode;
    private final String errorMessage;

    public InvalidInputException(String fieldName, String errorCode, String defaultMessage) {
        super(defaultMessage);
        this.fieldName = fieldName;
        this.errorCode = errorCode;
        this.errorMessage = defaultMessage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
