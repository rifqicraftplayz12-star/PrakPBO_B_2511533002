package shop.exception;

/** Payment failed (for example the bank is down). */
public class PaymentFailedException extends ShopException {
    private static final long serialVersionUID = 1L;

    public PaymentFailedException(String message) {
        super(message);
    }

    public PaymentFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}