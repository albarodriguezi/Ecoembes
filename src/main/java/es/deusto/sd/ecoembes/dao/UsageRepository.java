package es.deusto.sd.ecoembes.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.deusto.sd.ecoembes.entity.UsageId;
import es.deusto.sd.ecoembes.entity.Registry;
import es.deusto.sd.ecoembes.entity.Dumpster;

@Repository
public interface UsageRepository extends JpaRepository<Registry, UsageId>{
	List<Registry> findByIdDate(LocalDate date);
	List<Registry> findByIdDumpster(Dumpster dumpster);
	Registry findByIdDateAndIdDumpster(LocalDate date, Dumpster dumpster);
}

