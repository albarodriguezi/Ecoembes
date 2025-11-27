package es.deusto.sd.ecoembes.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.deusto.sd.ecoembes.entity.Dumpster;
import es.deusto.sd.ecoembes.entity.Employee;

public interface DumpsterRepository extends JpaRepository<Employee, Long>{
	Optional<Dumpster> findByID(long id);
}
