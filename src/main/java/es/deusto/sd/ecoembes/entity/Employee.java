package es.deusto.sd.ecoembes.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Employee {
	private long id;
	private String name;
	private String email;
	private String password;
	private LocalDate date_birth;
	private double salary;
	private ArrayList<Dumpster> dumpsters = new ArrayList<>();
	private ArrayList<Plant> plants = new ArrayList<>();
	
	public Employee() {}
	
	public Employee(long id, String name, String email, String password, LocalDate date_birth, double salary) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.date_birth = date_birth;
		this.salary = salary;
	}
	
	public boolean checkPassword(String password) {
		return this.password.equals(password);
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDate_birth() {
		return date_birth;
	}

	public void setDate_birth(LocalDate date_birth) {
		this.date_birth = date_birth;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public List<Dumpster> getDumpsters() {
		return dumpsters;
	}

	public void setDumpsters(List<Dumpster> dumpsters) {
		for (Dumpster d : dumpsters) {
			this.dumpsters.add(d);
		}

	}

	public List<Plant> getPlants() {
		return plants;
	}

	public void setPlants(List<Plant> plants) {
		for (Plant p : plants) {
			this.plants.add(p);
		}
	}
	
	public void createDumpster(long dumpsterId, int PC, String city, String address, String type) {
		System.out.println("Employee " + this.name + " is creating dumpster with ID " + dumpsterId);
		Dumpster dumpster = new Dumpster(dumpsterId, PC, city, address, type);
		System.out.println(this.dumpsters.toString());
		this.dumpsters.add(dumpster);
		
	}

	@Override
	public int hashCode() {
		return Objects.hash(date_birth, dumpsters, email, id, name, password, plants, salary);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(date_birth, other.date_birth) && Objects.equals(dumpsters, other.dumpsters)
				&& Objects.equals(email, other.email) && id == other.id && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && Objects.equals(plants, other.plants)
				&& Double.doubleToLongBits(salary) == Double.doubleToLongBits(other.salary);
	}
	
	
}
