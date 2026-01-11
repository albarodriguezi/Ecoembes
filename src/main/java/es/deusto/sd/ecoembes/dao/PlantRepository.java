package es.deusto.sd.ecoembes.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.deusto.sd.ecoembes.entity.Plant;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    Optional<Plant> findById(long id);

    // Finder by business name used by PlantGatewayFactory and gateways
    Optional<Plant> findByName(String name);
}