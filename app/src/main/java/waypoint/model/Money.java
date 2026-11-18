package waypoint.model;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public final class Money implements Comparable<Money> {
    public static final Currency INR = Currency.getInstance("INR");
    public static final Currency USD = Currency.getInstance("USD");

    private final long minor;
    private final Currency currency;

    private Money(long minor, Currency currency) {
        this.minor = minor;
        this.currency = Objects.requireNonNull(currency, "currency");
    }

    public static Money ofMinor(long minor, Currency currency) {
        return new Money(minor, currency);
    }

    public static Money rupees(long rupees) {
        return new Money(Math.multiplyExact(rupees, 100L), INR);
    }

    public Money plus(Money other) {
        requireSameCurrency(other);
        return new Money(Math.addExact(minor, other.minor), currency);
    }

    public Money minus(Money other) {
        requireSameCurrency(other);
        return new Money(Math.subtractExact(minor, other.minor), currency);
    }

    public Money times(int factor) {
        return new Money(Math.multiplyExact(minor, (long) factor), currency);
    }

    public boolean isNegative() {
        return minor < 0;
    }

    public List<Money> allocate(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        long base = Math.floorDiv(minor, (long) n);
        long remainder = minor - base * n;
        List<Money> shares = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            shares.add(new Money(i < remainder ? base + 1 : base, currency));
        }
        return Collections.unmodifiableList(shares);
    }

    @Override
    public int compareTo(Money other) {
        requireSameCurrency(other);
        return Long.compare(minor, other.minor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money m = (Money) o;
        return minor == m.minor && currency.equals(m.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minor, currency);
    }

    @Override
    public String toString() {
        int digits = Math.max(currency.getDefaultFractionDigits(), 0);
        return currency.getCurrencyCode() + " " + BigDecimal.valueOf(minor, digits).toPlainString();
    }

    private void requireSameCurrency(Money other) {
        Objects.requireNonNull(other, "other");
        if (!currency.equals(other.currency)) {
            throw new CurrencyMismatch(currency, other.currency);
        }
    }
}