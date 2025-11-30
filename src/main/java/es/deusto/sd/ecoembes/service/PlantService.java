package es.deusto.sd.ecoembes.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import es.deusto.sd.ecoembes.entity.Dumpster;
import es.deusto.sd.ecoembes.entity.Employee;
import es.deusto.sd.ecoembes.entity.Plant;
import es.deusto.sd.ecoembes.external.IPlantGateway;
import es.deusto.sd.ecoembes.external.PlantGatewayFactory;

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


public boolean updatePlant(long RP_id, long containers) {
    Plant plant = plantRepository.get(RP_id);
    plant.setCapacity((int)(plant.getCapacity()-containers));
    
    return true;
}
}

