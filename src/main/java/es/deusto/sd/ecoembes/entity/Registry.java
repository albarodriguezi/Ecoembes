package es.deusto.sd.ecoembes.entity;

import java.time.LocalDate;

public class Registry {
	
	LocalDate date;
	String level;
	Dumpster dumpster;
	
	public Registry(LocalDate date, String level, Dumpster dumpster) {
		super();
		this.date = date;
		this.level = level;
		this.dumpster = dumpster;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
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
	
}
