package es.deusto.sd.ecoembes.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class UsageId implements Serializable {

    private LocalDate date;
    private long dumpsterId;
    private long plantId;

    public UsageId() {}

    public UsageId(LocalDate date, long dumpsterId, long plantId) {
        this.date = date;
        this.dumpsterId = dumpsterId;
        this.plantId = plantId;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getDumpsterId() {
        return dumpsterId;
    }

    public long getPlantId() {
        return plantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsageId)) return false;
        UsageId that = (UsageId) o;
        return dumpsterId == that.dumpsterId &&
               plantId == that.plantId &&
               Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, dumpsterId, plantId);
    }
}
