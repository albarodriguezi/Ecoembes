package es.deusto.sd.ecoembes.dto;

public class PlantCapacityDTO {
	
	private String id;
	private int capacity;
	
	public PlantCapacityDTO() { }
	
	public PlantCapacityDTO(String id, int capacity) {
		this.id = id;
		this.capacity = capacity;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	


}
