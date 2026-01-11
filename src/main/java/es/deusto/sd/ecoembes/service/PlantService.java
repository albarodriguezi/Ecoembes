package es.deusto.sd.ecoembes.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import es.deusto.sd.ecoembes.entity.Plant;
import es.deusto.sd.ecoembes.external.IPlantGateway;
import es.deusto.sd.ecoembes.external.PlantGatewayFactory;
import es.deusto.sd.ecoembes.dao.PlantRepository;

@Service
public class PlantService {

    private final AuthService authService;
    private final PlantRepository plantRepositoryJPA;
    private final PlantGatewayFactory plantGatewayFactory;

    public PlantService(AuthService authService,
                        PlantRepository plantRepositoryJPA,
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
        // Use repository.findAll() and stream to remain compatible with current PlantRepository
        return plantRepositoryJPA.findAll()
                .stream()
                .filter(p -> name.equals(p.getName()))
                .findFirst()
                .orElse(null);
    }

    // ======= CAPACITY: use per-plant gateway (factory returns a gateway bound to the plant) =======

    public int checkPlantCapacity(String token, String plantName, LocalDate date) {
        try {
            Plant plant = getPlantByName(plantName);
            if (plant == null) {
                System.out.println("PlantService: plant not found by name='" + plantName + "'");
                return -1;
            }

            System.out.println("PlantService: checking capacity for plant='" + plantName + "' id=" + plant.getId());

            // Gateway bound to the plant (factory selects and constructs it)
            IPlantGateway plantGateway = plantGatewayFactory.createByPlantName(plantName);

            System.out.println("PlantService: using gateway=" + plantGateway.getClass().getSimpleName() + " for plant='" + plantName + "'");

            String formattedDate = date.format(DateTimeFormatter.ofPattern("ddMMyyyy"));

            // Call the per-plant gateway method
            int cap = plantGateway.getCapacity(formattedDate);
            System.out.println("PlantService: capacity for plant='" + plantName + "' date=" + formattedDate + " -> " + cap);
            return cap;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // ======= NOTIFY: per-plant gateway handles notification without needing plantName param =======

    public String notifyAssignment(long dumpster, int containers, String plantName) {
        try {
            Plant plant = getPlantByName(plantName);
            if (plant == null) return "Error: plant not found";

            IPlantGateway plantGateway = plantGatewayFactory.createByPlantName(plantName);

            System.out.println("PlantService: notifying assignment to plant='" + plantName + "' via gateway=" + plantGateway.getClass().getSimpleName());

            // Call per-plant gateway notify
            return plantGateway.notifyAssignment(dumpster, containers);

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