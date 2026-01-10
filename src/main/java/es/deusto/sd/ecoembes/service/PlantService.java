package es.deusto.sd.ecoembes.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import es.deusto.sd.ecoembes.entity.Dumpster;
import es.deusto.sd.ecoembes.entity.Employee;
import es.deusto.sd.ecoembes.entity.Plant;
import es.deusto.sd.ecoembes.external.IPlantGateway;
import es.deusto.sd.ecoembes.external.PlantGatewayFactory;

@Service
public class PlantService {
    
    private final AuthService authService;
    private final JpaRepository<Plant, Long> plantRepositoryJPA;
    private final PlantGatewayFactory plantGatewayFactory;
    
    public PlantService(AuthService authService, JpaRepository<Plant, Long> plantRepositoryJPA, PlantGatewayFactory plantGatewayFactory) {
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

    // Find plant by name using repository.findAll() to avoid depending on a custom finder
    public Plant getPlantByName(String name) {
        if (name == null) return null;
        return plantRepositoryJPA.findAll()
                .stream()
                .filter(p -> name.equals(p.getName()))
                .findFirst()
                .orElse(null);
    }
    
    // Now accepts plantName (not numeric id). Factory will choose gateway by name.
    public int checkPlantCapacity(String token, String plantName, LocalDate date) {
        try {
            Plant plant = getPlantByName(plantName);
            if (plant == null) return -1;

            IPlantGateway plantGateway = plantGatewayFactory.createByPlantName(plantName);

            // Convert LocalDate to ddMMyyyy (expected by PlasSB)
            String formattedDate = date.format(DateTimeFormatter.ofPattern("ddMMyyyy"));

            // Use plant name as the remote identifier for both HTTP and socket gateways
            String remoteId = plant.getName();

            return plantGateway.getCapacity(remoteId, formattedDate);

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Notify using plantName so factory can pick correct gateway
    public String notifyAssignment(long dumpster, int containers, String plantName) {
        try {
            Plant plant = getPlantByName(plantName);
            if (plant == null) return "Error: plant not found";

            IPlantGateway plantGateway = plantGatewayFactory.createByPlantName(plantName);

            // Use plant name as remote identifier
            String remoteId = plant.getName();

            // Notify the plant about the assignment, passing the remoteId
            return plantGateway.notifyAssignment(remoteId, dumpster, containers);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error notifying assignment";
        }
    }
    
    // Update plant capacity by name
    public boolean updatePlant(String plantName, long containers) {
        Plant plant = getPlantByName(plantName);
        if (plant == null) return false;
        plant.setCapacity((int)(plant.getCapacity() - containers));
        plantRepositoryJPA.save(plant);
        return true;
    }
}