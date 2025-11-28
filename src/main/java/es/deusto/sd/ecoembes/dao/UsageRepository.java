package es.deusto.sd.ecoembes.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.deusto.sd.ecoembes.entity.UsageId;
import es.deusto.sd.ecoembes.entity.Registry;
import es.deusto.sd.ecoembes.entity.Dumpster;

@Repository
public interface UsageRepository extends JpaRepository<Registry, UsageId> {

    // Find all registry entries by date
    List<Registry> findByIdDate(LocalDate date);

    // Find all registry entries by dumpster entity
    List<Registry> findByDumpster(Dumpster dumpster);

    // Find a specific registry entry by date + dumpsterId inside the embedded ID
    Registry findByIdDateAndIdDumpsterId(LocalDate date, long dumpsterId);
}

