package es.deusto.sd.ecoembes.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Usage {
	private LocalDate date;
	private Dumpster dumpster;
	private Plant plant;
	
	public Usage() {}
	
	public Usage(LocalDate date, Dumpster dumpster, Plant plant) {
		this.date = date;
		this.dumpster = dumpster;
		this.plant = plant;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Dumpster getDumpster() {
		return dumpster;
	}

	public void setDumpster(Dumpster dumpster) {
		this.dumpster = dumpster;
	}

	public Plant getPlant() {
		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, dumpster, plant);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usage other = (Usage) obj;
		return Objects.equals(date, other.date) && Objects.equals(dumpster, other.dumpster)
				&& Objects.equals(plant, other.plant);
	}
	
	
	
}
