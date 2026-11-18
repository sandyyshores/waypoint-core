package waypoint.model;

public record TicketId(String value) {
    public TicketId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("TicketId must not be null or blank");
        }
    }
}