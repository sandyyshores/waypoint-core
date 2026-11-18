package waypoint.model;

import java.time.Instant;
import java.util.Objects;

public final class Ticket {
    private final TicketId id;
    private final SpotId spotId;
    private final Instant entry;
    private Instant exit;
    private Money fare;

    private Ticket(TicketId id, SpotId spotId, Instant entry) {
        this.id = Objects.requireNonNull(id, "id");
        this.spotId = Objects.requireNonNull(spotId, "spotId");
        this.entry = Objects.requireNonNull(entry, "entry");
    }

    public static Ticket open(TicketId id, SpotId spotId, Instant entry) {
        return new Ticket(id, spotId, entry);
    }

    public TicketId id() {
        return id;
    }

    public SpotId spotId() {
        return spotId;
    }

    public Instant entry() {
        return entry;
    }

    public synchronized Instant exit() {
        return exit;
    }

    public synchronized Money fare() {
        return fare;
    }

    public synchronized boolean isOpen() {
        return exit == null;
    }

    public synchronized Money close(Instant at, FarePolicy policy) {
        Objects.requireNonNull(at, "at");
        Objects.requireNonNull(policy, "policy");
        if (exit != null) {
            throw new TicketAlreadyClosed(id);
        }
        if (at.isBefore(entry)) {
            throw new IllegalArgumentException("exit " + at + " is before entry " + entry);
        }
        Money computed = Objects.requireNonNull(policy.fareFor(entry, at, spotId), "policy returned null fare");
        if (computed.isNegative()) {
            throw new IllegalStateException("policy returned a negative fare");
        }
        this.exit = at;
        this.fare = computed;
        return computed;
    }
}