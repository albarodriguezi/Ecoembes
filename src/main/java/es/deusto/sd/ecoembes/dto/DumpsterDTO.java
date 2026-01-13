package es.deusto.sd.ecoembes.dto;

public class DumpsterDTO {
	private long id;
	private int containers;
	private String level;
	private int pc;
	
	public DumpsterDTO() { }
	
	public DumpsterDTO(long id, int containers, String level, int PC) {
		this.id = id;
		this.containers = containers;
		this.level = level;
		this.pc = PC;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getContainers() {
		return containers;
	}
	public void setContainers(int containers) {
		this.containers = containers;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	

	@Override
	public String toString() {
		return "DumpsterDTO [id=" + id + ", containers=" + containers + ", level=" + level + ", pc=" + pc + "]";
	}
	
	// Getters y Setters
	
}
