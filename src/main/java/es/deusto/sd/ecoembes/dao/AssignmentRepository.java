package es.deusto.sd.ecoembes.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.deusto.sd.ecoembes.entity.Assignment;
import es.deusto.sd.ecoembes.entity.Employee;
import es.deusto.sd.ecoembes.entity.UsageId;


@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long>{
	List<Assignment> findAssignmentsByDumpster_Id(long dumpsterId);
	
	List<Assignment> findAssignmentsByPlantId(long plantId);
	
	List<Assignment> findAssignmentsByEmployee_Id(long employeeId);
}

