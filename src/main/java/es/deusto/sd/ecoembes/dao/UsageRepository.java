package es.deusto.sd.ecoembes.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.deusto.sd.ecoembes.entity.UsageId;
import es.deusto.sd.ecoembes.entity.Usage;
import es.deusto.sd.ecoembes.entity.Dumpster;

@Repository
public interface UsageRepository extends JpaRepository<Usage, UsageId>{
	List<Usage> findByIdDate(LocalDate date);
	List<Usage> findByIdDumpster(Dumpster dumpster);
	Usage findByIdDateAndIdDumpster(LocalDate date, Dumpster dumpster);
}
