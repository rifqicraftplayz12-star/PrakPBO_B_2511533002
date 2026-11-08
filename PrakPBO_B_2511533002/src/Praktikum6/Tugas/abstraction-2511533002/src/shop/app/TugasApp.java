package shop.app;

import java.util.List;
import shop.StudentInfo;
import shop.model.DigitalProduct;
import shop.model.PhysicalProduct;
import shop.model.Product;
import shop.notification.CustomerContact;
import shop.notification.EmailChannel;
import shop.notification.NotificationChannel;
import shop.notification.NotificationService;
import shop.notification.SmsChannel;
import shop.notification.WhatsAppChannel;
import shop.payment.BankTransferPayment;
import shop.payment.CreditCardPayment;
import shop.payment.EWalletPayment;
import shop.payment.PaymentMethod;
import shop.service.CheckoutService;
import shop.shipping.ExpressShipping;
import shop.shipping.ShippingService;

public class TugasApp {
    public static void main(String[] args) {
        System.out.println("Tugas oleh " + StudentInfo.NAME + " (" + StudentInfo.NIM + ")");

        // ===== T1: grand total tiga metode pembayaran =====
        List<Product> cart = List.of(
                new PhysicalProduct("KOPI-001", "Kopi Arabika Gayo 250g", 85_000, 300),
                new PhysicalProduct("RDG-002", "Rendang Kemasan 250g", 120_000, 400),
                new DigitalProduct("EBK-003", "E-book Resep Kue Nusantara", 50_000,
                        "https://toko-umkm.example/unduh/ebk-003"));
        ShippingService express = new ExpressShipping();
        CheckoutService checkout = new CheckoutService();

        EWalletPayment wallet = new EWalletPayment("DompetKu", 500_000);
        PaymentMethod transfer = new BankTransferPayment("Bank Nagari");
        CreditCardPayment card = new CreditCardPayment("Siti Rahma", 1_000_000);

        System.out.println("\n== T1: Total dengan biaya admin (Ekspres) ==");
        for (PaymentMethod payment : List.of(wallet, transfer, card)) {
            long baseTotal = checkout.total(cart, express);
            System.out.println(payment.getName() + " : Rp" + checkout.grandTotal(cart, express, payment)
                    + " (admin Rp" + payment.adminFee(baseTotal) + ")");
        }

        System.out.println();
        System.out.println("Kebijakan kartu kredit : " + card.policy());
        System.out.println("Kebijakan e-wallet     : " + wallet.policy());

        // ===== T2: notifikasi status pesanan =====
        NotificationService service = new NotificationService();
        service.register(new EmailChannel());
        service.register(new SmsChannel(StudentInfo.SMS_MAX_LENGTH));
        service.register(new WhatsAppChannel());
        service.register(new NotificationChannel() { // kanal log konsol (anonymous class)
            @Override
            public String channelName() {
                return "Log";
            }

            @Override
            public boolean send(CustomerContact contact, String message) {
                System.out.println("[LOG] " + contact.getName() + ": " + message);
                return true;
            }
        });

        String message = "Pesanan ORD-0001 sudah dikirim dengan kurir Ekspres. "
                + "Nomor resi akan muncul di aplikasi dalam 1x24 jam. "
                + "Terima kasih telah berbelanja produk UMKM Sumatera Barat di toko kami! "
                + "Pantau terus status pesanan Anda di aplikasi toko.";

        CustomerContact siti = new CustomerContact("Siti Rahma", "siti@example.com", "081234567890");
        CustomerContact budi = new CustomerContact("Budi", "", "+6281298765432");

        System.out.println("\n== T2: Notifikasi status pesanan ==");
        for (CustomerContact contact : List.of(siti, budi)) {
            int delivered = service.broadcast(contact, message);
            System.out.println("Terkirim ke " + contact.getName() + " lewat " + delivered + " kanal");
        }
    }
}