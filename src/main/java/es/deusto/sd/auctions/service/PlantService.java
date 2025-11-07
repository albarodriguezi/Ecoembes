package es.deusto.sd.auctions.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import es.deusto.sd.auctions.entity.Employee;
import es.deusto.sd.auctions.entity.Plant;

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

    public int checkPlantCapacity(String token, long plantId, LocalDate date) {
    	Employee employee = authService.getUserByToken(token);

        if (employee == null) {
            throw new IllegalArgumentException("Invalid token or session expired.");
        }

        Plant plant = employee.getPlants().stream()
                .filter(p -> p.getId() == plantId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                    "Employee " + employee.getName() + " does not have a plant with ID " + plantId + "."));

        System.out.println("Employee " + employee.getName() + 
                " is checking the capacity of plant " + plantId +
                " on date " + date);

        return plant.getCapacity();
    }
}

