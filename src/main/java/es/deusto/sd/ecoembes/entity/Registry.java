package es.deusto.sd.ecoembes.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
@Entity
public class Registry {

    @EmbeddedId
    private UsageId usageId;

    @ManyToOne
    @MapsId("dumpsterId")   // maps PK field "dumpsterId" to the dumpster relationship
    @JoinColumn(name = "dumpster_id")
    private Dumpster dumpster;

    @Column(nullable = false)
    private String level;

    public Registry(LocalDate date, String level, Dumpster dumpster) {
        this.usageId = new UsageId(date, dumpster.getId()); // dumpsterId will be filled in automatically
        this.level = level;
        this.dumpster = dumpster;
    }
	
	public LocalDate getDate() {
		return usageId.getDate();
	}
	
	public void setDate(LocalDate date) {
		this.usageId.setDate(date);
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Dumpster getDumpster() {
		return dumpster;
	}
	public void setDumpster(Dumpster dumpster) {
		this.dumpster = dumpster;
	}

	public UsageId getId() {
		return usageId;
	}
	
}
