package waypoint.model;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public record Registration(String value) {
    private static final Pattern FORMAT = Pattern.compile("^[A-Z]{2}[0-9]{2}[A-Z]{1,2}[0-9]{4}$");

    public Registration {
        Objects.requireNonNull(value, "value");
        value = value.trim().toUpperCase(Locale.ROOT);
        if (!FORMAT.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid registration: " + value);
        }
    }
}