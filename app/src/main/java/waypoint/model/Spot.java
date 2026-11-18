package waypoint.model;

import java.util.Objects;

public final class Spot {
    private final SpotId id;
    private VehicleId occupant;

    public Spot(SpotId id) {
        this.id = Objects.requireNonNull(id, "id");
    }

    public SpotId id() {
        return id;
    }

    public synchronized boolean tryAssign(VehicleId v) {
        Objects.requireNonNull(v, "v");
        if (occupant != null) {
            return false;
        }
        occupant = v;
        return true;
    }

    public synchronized void release(VehicleId v) {
        Objects.requireNonNull(v, "v");
        if (v.equals(occupant)) {
            occupant = null;
        }
    }

    public synchronized boolean isFree() {
        return occupant == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Spot)) return false;
        return id.equals(((Spot) o).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}