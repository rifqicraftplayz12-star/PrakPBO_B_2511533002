package shop.notification;

public class SmsChannel extends AbstractChannel {
    private static final String ELLIPSIS = "...";
    private final int maxLength;

    public SmsChannel(int maxLength) {
        if (maxLength < ELLIPSIS.length() + 1) {
            throw new IllegalArgumentException("maxLength is too small");
        }
        this.maxLength = maxLength;
    }

    @Override
    public String channelName() {
        return "SMS";
    }

    @Override
    protected String resolveRecipient(CustomerContact contact) {
        String phone = contact.getPhone();
        return phone.startsWith("08") ? phone : null;
    }

    @Override
    protected String prepare(String message) {
        if (message.length() <= maxLength) {
            return message;
        }
        return message.substring(0, maxLength - ELLIPSIS.length()) + ELLIPSIS;
    }

    @Override
    protected boolean deliver(String recipient, String message) {
        System.out.println("[SMS to " + recipient + "] " + message);
        return true;
    }
}