package es.deusto.sd.ecoembes.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import es.deusto.sd.ecoembes.dao.PlantRepository;
import es.deusto.sd.ecoembes.dao.UsageRepository;
import es.deusto.sd.ecoembes.entity.Dumpster;
import es.deusto.sd.ecoembes.entity.Employee;
import es.deusto.sd.ecoembes.entity.Plant;
import es.deusto.sd.ecoembes.external.IPlantGateway;
import es.deusto.sd.ecoembes.external.PlantGatewayFactory;

@Service
public class PlantService {
    
    private final AuthService authService;
    private final PlantRepository plantRepositoryJPA;
    
    public PlantService(AuthService authService, PlantRepository plantRepositoryJPA) {
        this.authService = authService;
        this.plantRepositoryJPA = plantRepositoryJPA;
    }
    
    public void addPlant(Plant plant) {
    	if (plant != null) {
			plantRepositoryJPA.save(plant);
		}
    }

    public Plant getPlantById(long plantId) {
        return plantRepositoryJPA.findById(plantId).orElse(null);
    }
    
    public int checkPlantCapacity(String token, String type, long plantId, LocalDate date) {
        try {
            IPlantGateway plantGateway = PlantGatewayFactory.create(type);

            // Convert LocalDate to ddMMyyyy (expected by PlasSB)
            String formattedDate = date.format(DateTimeFormatter.ofPattern("ddMMyyyy"));

            // PlasSB returns a simple number → parse the String into an int
            return  plantGateway.getCapacity(String.valueOf(plantId), formattedDate);

            //return Integer.parseInt(rawCapacity);

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String notifyAssignment(long dumpster, int containers, String type) {
        try {
			IPlantGateway plantGateway = PlantGatewayFactory.create(type);

			// Notify the plant about the assignment
			return plantGateway.notifyAssignment(dumpster, containers);

		} catch (Exception e) {
			e.printStackTrace();
			return "Error notifying assignment";
		}
    }
    
public boolean updatePlant(long RP_id, long containers) {
    Plant plant = plantRepositoryJPA.findById(RP_id).orElse(null);
    plant.setCapacity((int)(plant.getCapacity()-containers));
    plantRepositoryJPA.save(plant);
    
    return true;
}
}

