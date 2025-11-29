package es.deusto.sd.ecoembes.entity;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
@Entity
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Dumpster dumpster;

    @Column(nullable = false)
    private Long plantId;
	
	public Assignment() {}
	
	public Assignment(Employee employee, Dumpster dumpster, long plant) {
		this.employee = employee;
		this.dumpster = dumpster;
		this.plantId = plant;
	}

	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Dumpster getDumpster() {
		return dumpster;
	}

	public void setDumpster(Dumpster dumpster) {
		this.dumpster = dumpster;
	}

	public Long getPlant() {
		return plantId;
	}

	public void setPlant(long plant) {
		this.plantId = plant;
	}

	@Override
	public int hashCode() {
		return Objects.hash(employee, dumpster, plantId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Assignment other = (Assignment) obj;
		return Objects.equals(employee, other.employee) && Objects.equals(dumpster, other.dumpster)
				&& Objects.equals(plantId, other.plantId);
	}
	
	
	
}
