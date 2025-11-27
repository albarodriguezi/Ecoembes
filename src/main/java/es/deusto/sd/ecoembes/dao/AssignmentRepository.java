package es.deusto.sd.ecoembes.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.deusto.sd.ecoembes.entity.Employee;
import es.deusto.sd.ecoembes.entity.Usage;


@Repository
public interface AssignmentRepository {
	List<Assignment> findAssignmentsByDumpster(long dumpsterId);
	
	List<Assignment> findAssignmentsByPlant(long plantId);
	
	List<Assignment> findAssignmentsByEmployee(long employeeId);
}

