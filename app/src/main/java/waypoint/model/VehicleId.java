package waypoint.model;

public record VehicleId(String value) {
    public VehicleId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("VehicleId must not be null or blank");
        }
    }
}