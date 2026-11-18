package waypoint.model;

public record SpotId(String value) {
    public SpotId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("SpotId must not be null or blank");
        }
    }
}