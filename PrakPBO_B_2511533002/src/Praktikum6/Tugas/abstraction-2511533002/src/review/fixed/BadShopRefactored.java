package review.fixed;

import java.util.ArrayList;
import java.util.List;

interface Payment {
    boolean pay(long amount);

    String printInvoice();
}

interface Refundable extends Payment {
    boolean refund(long amount);
}

final class GiftVoucher implements Payment {
    private long balance = 100_000;

    @Override
    public boolean pay(long amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    @Override
    public String printInvoice() {
        return "sisa Rp" + balance;
    }
}

abstract class Courier {
    protected abstract long ratePerKg();
    public final long cost(int kilograms) {
        return kilograms <= 0 ? 0 : kilograms * ratePerKg();
    }
}

final class RegularCourier extends Courier {
    @Override
    protected long ratePerKg() {
        return 10_000;
    }
}

final class ExpressCourier extends Courier {
    @Override
    protected long ratePerKg() {
        return 18_000;
    }
}

final class SameDayCourier extends Courier {
    @Override
    protected long ratePerKg() {
        return 30_000;
    }
}

final class Cart {
    private final List<Item> items = new ArrayList<>();
    static final class Item {
        private final String name;
        private final long price;
        private final int quantity;

        Item(String name, long price, int quantity) {
            if (name == null || name.isBlank() || price < 0 || quantity <= 0) {
                throw new IllegalArgumentException("Item tidak valid");
            }
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        long subtotal() {
            return price * quantity;
        }

        String getName() {
            return name;
        }
    }

    void add(Item item) {
        items.add(item);
    }

    long total() {
        long total = 0;
        for (Item item : items) {
            total += item.subtotal();
        }
        return total;
    }
}
final class Address {
    private final String street;
    private final String city;

    Address(String street, String city) {
        this.street = street;
        this.city = city;
    }

    Address withCity(String newCity) {
        return new Address(street, newCity);
    }

    @Override
    public String toString() {
        return street + ", " + city;
    }
}

final class SimpleOrder {
    private final String id;
    private final Address address;

    SimpleOrder(String id, Address address) {
        this.id = id;
        this.address = address;
    }

    Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return id + " -> " + address;
    }
}
interface Discount {
    int percent();

    default long apply(long price) {
        return price - price * percent() / 100;
    }
}

final class MemberDiscount implements Discount {
    @Override
    public int percent() {
        return 5;
    }
}

final class FlashSaleDiscount implements Discount {
    @Override
    public int percent() {
        return 30;
    }
}

public class BadShopRefactored {
    public static void main(String[] args) {
        Cart cart = new Cart();
        cart.add(new Cart.Item("Kopi Gayo", 85_000, 2));
        System.out.println("Total cart: " + cart.total());

        Courier courier = new ExpressCourier();
        System.out.println("Ongkir Ekspres 2 kg: " + courier.cost(2));

        Address home = new Address("Jl. Sudirman 10", "Padang");
        SimpleOrder first = new SimpleOrder("ORD-1", home);
        SimpleOrder second = new SimpleOrder("ORD-2", home.withCity("Bukittinggi"));
        System.out.println(first);
        System.out.println(second);

        Discount flashSale = new FlashSaleDiscount();
        System.out.println("Harga flash sale: " + flashSale.apply(100_000));

        Payment voucher = new GiftVoucher();
        System.out.println(voucher.pay(30_000) + " | " + voucher.printInvoice());
    }
}