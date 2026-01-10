package es.deusto.sd.ecoembes.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "plants")
public class Plant {
	@Id
	private long id;
	@Column(nullable = false)
	private String name; // NEW: plant name used by factory/API
	@Column(nullable = false)
	private int PC;
	@Column(nullable = false)
	private String city;
	@Column(nullable = false)
	private String address;
	@Column(nullable = false)
	private int capacity;
	
	public Plant() {}
	
	public Plant(long id, String name, int pC, String city, String address, int capacity) {
		this.id = id;
		this.name = name;
		PC = pC;
		this.city = city;
		this.address = address;
		this.capacity = capacity;
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
		return Objects.hash(PC, address, capacity, city, id, name);
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
				&& Objects.equals(city, other.city) && id == other.id && Objects.equals(name, other.name);
	}
}