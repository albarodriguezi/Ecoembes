package es.deusto.sd.ecoembes.dto;

public class PlantDTO {
	private long id;
	private String name;
	private int PC;
	
	public PlantDTO() { }
	
	public PlantDTO(long id, String name, int PC) {
		this.id = id;
		this.name=name;
		this.PC=PC;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPC() {
		return PC;
	}

	public void setPC(int pC) {
		PC = pC;
	}
}
