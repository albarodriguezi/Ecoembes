package es.deusto.sd.auctions.entity;

import java.util.Objects;

public class Plant {
	private long id;
	private int PC;
	private String city;
	private String address;
	private int capacity;
	
	public Plant() {}
	
	public Plant(long id, int pC, String city, String address) {
		this.id = id;
		PC = pC;
		this.city = city;
		this.address = address;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPC() {
		return PC;
	}

	public void setPC(int pC) {
		PC = pC;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(PC, address, capacity, city, id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Plant other = (Plant) obj;
		return PC == other.PC && Objects.equals(address, other.address) && capacity == other.capacity
				&& Objects.equals(city, other.city) && id == other.id;
	}
}