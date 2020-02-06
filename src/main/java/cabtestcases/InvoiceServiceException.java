package cabtestcases;

public class InvoiceServiceException extends Throwable {

    public ExceptionType exceptionType;

    public enum ExceptionType {
        DATA_NOT_FOUND;
    }

    public InvoiceServiceException(String message, ExceptionType exceptionType) {
        super(message);
        this.exceptionType = exceptionType;
    }
}
