package es.deusto.sd.ecoembes.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "dumpsters")
public class Dumpster {

    @Id
    private long id;  // Manual ID, assign before saving

    @Column(nullable = false)
    private int PC;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private int containers;

    // Empty constructor required by JPA
    public Dumpster() {}

    // Constructor with manual ID
    public Dumpster(long id, int PC, String city, String address, String type, int containers) {
        this.id = id;
        this.PC = PC;
        this.city = city;
        this.address = address;
        this.type = type;
        this.containers = containers;
        updateStatus();
    }

    // Constructor with 0 containers by default
    public Dumpster(long id, int PC, String city, String address, String type) {
        this(id, PC, city, address, type, 0);
    }

    // Calculate status based on container count
    private void updateStatus() {
        if (containers > 120) {
            this.status = "RED";
        } else if (containers >= 80) {
            this.status = "ORANGE";
        } else {
            this.status = "GREEN";
        }
    }

    // --- Getters & Setters ---
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public int getPC() { return PC; }
    public void setPC(int PC) { this.PC = PC; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getContainers() { return containers; }
    public void setContainers(int containers) { 
        this.containers = containers;
        updateStatus();
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Optional: override equals/hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id, PC, city, address);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Dumpster other = (Dumpster) obj;
        return id == other.id && PC == other.PC &&
               Objects.equals(city, other.city) &&
               Objects.equals(address, other.address);
    }
}

