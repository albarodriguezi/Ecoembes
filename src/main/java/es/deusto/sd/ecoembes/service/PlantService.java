package es.deusto.sd.ecoembes.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import es.deusto.sd.ecoembes.entity.Plant;
import es.deusto.sd.ecoembes.external.IPlantGateway;
import es.deusto.sd.ecoembes.external.PlantGatewayFactory;

@Service
public class PlantService {

    private final AuthService authService;
    private final JpaRepository<Plant, Long> plantRepositoryJPA;
    private final PlantGatewayFactory plantGatewayFactory;

    public PlantService(AuthService authService,
                        JpaRepository<Plant, Long> plantRepositoryJPA,
                        PlantGatewayFactory plantGatewayFactory) {
        this.authService = authService;
        this.plantRepositoryJPA = plantRepositoryJPA;
        this.plantGatewayFactory = plantGatewayFactory;
    }

    public void addPlant(Plant plant) {
        if (plant != null) {
            plantRepositoryJPA.save(plant);
        }
    }

    public Plant getPlantById(long plantId) {
        return plantRepositoryJPA.findById(plantId).orElse(null);
    }

    // Buscar planta por nombre
    public Plant getPlantByName(String name) {
        if (name == null) return null;
        return plantRepositoryJPA.findAll()
                .stream()
                .filter(p -> name.equals(p.getName()))
                .findFirst()
                .orElse(null);
    }

    // ======= CAPACITY POR NOMBRE, PERO GATEWAY POR ID =======

    public int checkPlantCapacity(String token, String plantName, LocalDate date) {
        try {
            Plant plant = getPlantByName(plantName);
            if (plant == null) return -1;

            // Gateway según nombre (PLAS_PAMPLONA -> PlasSB, etc.)
            IPlantGateway plantGateway = plantGatewayFactory.createByPlantName(plantName);

            String formattedDate = date.format(DateTimeFormatter.ofPattern("ddMMyyyy"));

            // Aquí usamos el ID numérico que espera PlasSB en /plants/{id}/capacities
            String remoteId = String.valueOf(plant.getId());

            return plantGateway.getCapacity(remoteId, formattedDate);

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // ======= NOTIFY POR NOMBRE, PERO GATEWAY POR ID =======

    public String notifyAssignment(long dumpster, int containers, String plantName) {
        try {
            Plant plant = getPlantByName(plantName);
            if (plant == null) return "Error: plant not found";

            IPlantGateway plantGateway = plantGatewayFactory.createByPlantName(plantName);

            // Otra vez, ID numérico para PlasSB
            String remoteId = String.valueOf(plant.getId());

            return plantGateway.notifyAssignment(remoteId, dumpster, containers);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error notifying assignment";
        }
    }

    // Update plant capacity by name (solo BBDD Ecoembes)
    public boolean updatePlant(String plantName, long containers) {
        Plant plant = getPlantByName(plantName);
        if (plant == null) return false;
        plant.setCapacity((int) (plant.getCapacity() - containers));
        plantRepositoryJPA.save(plant);
        return true;
    }
}
