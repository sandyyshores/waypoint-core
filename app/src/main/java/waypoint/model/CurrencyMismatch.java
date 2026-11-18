package waypoint.model;

import java.util.Currency;

public final class CurrencyMismatch extends RuntimeException {
    public CurrencyMismatch(Currency left, Currency right) {
        super("Currency mismatch: " + left.getCurrencyCode() + " vs " + right.getCurrencyCode());
    }
}