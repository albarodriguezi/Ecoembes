package es.deusto.sd.ecoembes.dto;

public class DumpsterDTO {
	private long id;
	private int containers;
	private String level;
	
	public DumpsterDTO() { }
	
	public DumpsterDTO(long id, int containers, String level) {
		this.id = id;
		this.containers = containers;
		this.level = level;
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

	// Getters y Setters
	
}
