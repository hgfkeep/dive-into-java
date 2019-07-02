package win.hgfdodo.springtx.exception;

public class NotCreditAccountException extends RuntimeException {

    public NotCreditAccountException(String s) {
        this(s, true, false);
    }

    private NotCreditAccountException(String message,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, null, true, false);
    }
}
