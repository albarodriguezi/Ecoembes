package es.deusto.sd.ecoembes.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import es.deusto.sd.ecoembes.entity.Dumpster;
import es.deusto.sd.ecoembes.entity.Employee;
import es.deusto.sd.ecoembes.entity.Plant;

@Service
public class PlantService {
    
    private final AuthService authService;
    private static Map<Long, Plant> plantRepository = new HashMap<>();

    public PlantService(AuthService authService) {
        this.authService = authService;
    }
    
    public void addPlant(Plant plant) {
    	if (plant != null) {
			plantRepository.putIfAbsent(plant.getId(), plant);
		}
    }

    public Plant getPlantById(long plantId) {
        return plantRepository.get(plantId);
    }
    
    public int checkPlantCapacity(String token, long plantId, LocalDate date) {
    	Employee employee = authService.getUserByToken(token);

        if (employee == null) {
            throw new IllegalArgumentException("Invalid token or session expired.");
        }

    	Plant plant = plantRepository.get(plantId);
        return plant.getCapacity();
    }


public boolean updatePlant(long RP_id, long containers) {
    Plant plant = plantRepository.get(RP_id);
    plant.setCapacity((int)(plant.getCapacity()-containers));
    
    return true;
}
}

