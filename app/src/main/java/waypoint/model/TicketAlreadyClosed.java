package waypoint.model;

public final class TicketAlreadyClosed extends IllegalStateException {
    public TicketAlreadyClosed(TicketId id) {
        super("Ticket already closed: " + id.value());
    }
}