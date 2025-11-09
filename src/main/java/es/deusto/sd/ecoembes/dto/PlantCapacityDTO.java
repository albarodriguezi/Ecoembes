package es.deusto.sd.ecoembes.dto;

public class PlantCapacityDTO {
	
	private int id;
	private int capacity;
	
	public PlantCapacityDTO() { }
	
	public PlantCapacityDTO(int id, int capacity) {
		this.id = id;
		this.capacity = capacity;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	


}
