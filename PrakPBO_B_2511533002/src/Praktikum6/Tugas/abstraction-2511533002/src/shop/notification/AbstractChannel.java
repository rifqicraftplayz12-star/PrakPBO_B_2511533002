package shop.notification;

public abstract class AbstractChannel implements NotificationChannel {
    private int sentCount;
    private String lastDelivered;

    @Override
    public final boolean send(CustomerContact contact, String message) {
        if (contact == null || message == null || message.isBlank()) {
            return false;
        }
        String recipient = resolveRecipient(contact);
        if (recipient == null || recipient.isEmpty()) {
            return false;
        }
        String prepared = prepare(message);
        if (!deliver(recipient, prepared)) {
            return false;
        }
        sentCount++;
        lastDelivered = prepared;
        return true;
    }

    /** @return alamat tujuan untuk kanal ini, atau null/"" jika kontak tidak cocok */
    protected abstract String resolveRecipient(CustomerContact contact);

    /** Mengirim pesan yang sudah disiapkan (simulasi). */
    protected abstract boolean deliver(String recipient, String message);

    /** Hook: subclass boleh meng-override untuk mengubah pesan. */
    protected String prepare(String message) {
        return message;
    }

    public int getSentCount() {
        return sentCount;
    }

    public String getLastDelivered() {
        return lastDelivered;
    }
}