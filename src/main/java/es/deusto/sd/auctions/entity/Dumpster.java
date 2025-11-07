package es.deusto.sd.auctions.entity;

import java.util.Objects;

public class Dumpster {

	private long id;
	private int PC;
	private String city;
	private String address;
	private String type;
	
	public Dumpster() {}
	
	public Dumpster(long id, int pC, String city, String address, String type) {
		this.id = id;
		PC = pC;
		this.city = city;
		this.address = address;
		this.type = type;
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
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(PC, address, city, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dumpster other = (Dumpster) obj;
		return PC == other.PC && Objects.equals(address, other.address) && Objects.equals(city, other.city)
				&& id == other.id;
	}
	
	
}
