package es.deusto.sd.ecoembes.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import es.deusto.sd.ecoembes.entity.Dumpster;
import es.deusto.sd.ecoembes.entity.Employee;
import es.deusto.sd.ecoembes.entity.Plant;
import es.deusto.sd.ecoembes.entity.Usage;

@Service
public class DumpsterService {

    private final AuthService authService;
    private final PlantService plantService;

    private static Map<Long, Dumpster> dumpsterRepository = new HashMap<>();
    private static List<Usage> usageRecords = new ArrayList<>();

    public DumpsterService(AuthService authService, PlantService plantService) {
        this.authService = authService;
        this.plantService = plantService;
    }

    public void createDumpster(long dumpsterId, int PC, String city, String address, String type, String token) {
        Employee employee = authService.getUserByToken(token);
        System.out.println("Creating dumpster for employee with token: " + token);
        if (employee == null) {
        	System.out.println("Invalid token or expired session.");
            throw new IllegalArgumentException("Invalid token or expired session.");
        }
        employee.createDumpster(dumpsterId, PC, city, address, type);
        System.out.println("Dumpster created: ID " + dumpsterId + ", PC " + PC + ", City " + city + ", Address " + address + ", Type " + type);
        dumpsterRepository.putIfAbsent(dumpsterId, new Dumpster(dumpsterId, PC, city, address, type));
    }

    public void addDumpster(Dumpster dumpster) {
        if (dumpster != null) {
            dumpsterRepository.putIfAbsent(dumpster.getId(), dumpster);
        }
    }

    public List<Usage> queryDumpsterUsage(String token, long d_id, LocalDate i_date, LocalDate f_date) {
        Employee employee = authService.getUserByToken(token);
        if (employee == null) {
            throw new IllegalArgumentException("Invalid token or expired session.");
        }

        boolean ownsDumpster = employee.getDumpsters().stream().anyMatch(d -> d.getId() == d_id);
        if (!ownsDumpster) {
            throw new IllegalArgumentException("Employee does not own dumpster ID " + d_id);
        }

        List<Usage> usagePatterns = new ArrayList<>();
        for (Usage u : usageRecords) {
            if (u.getDumpster().getId() == d_id &&
                (!u.getDate().isBefore(i_date) && !u.getDate().isAfter(f_date))) {
                usagePatterns.add(u);
            }
        }
        return usagePatterns;
    }

    public Map<Long,String> checkDumpsterStatus(String token, int PC, LocalDate date) {
        Employee employee = authService.getUserByToken(token);
        if (employee == null) {
            throw new IllegalArgumentException("Invalid token or expired session.");
        }
        Map<Long,String> statusMap = new HashMap<>();
        for (Dumpster d : employee.getDumpsters()) {
			if (d.getPC() == PC) {
				long d_id = d.getId();
				String status = d.getStatus();
				statusMap.put(d_id, status);
			}
		}
        
        return statusMap;

        
    }
    
    public Dumpster updateDumpster(long id,int containers,String token) {
        Employee employee = authService.getUserByToken(token);
        if (employee == null) {
            throw new IllegalArgumentException("Invalid token or expired session.");
        }

		Dumpster dumpster = dumpsterRepository.get(id);
		if (dumpster == null) {
			throw new IllegalArgumentException("Dumpster with ID " + id + " not found.");
		}
		
		dumpster.setCapacity(containers);
		if(containers > 120) {
			dumpster.setStatus("RED");
		} else if (containers >=80 && containers <120) {
			dumpster.setStatus("ORANGE");
		} else {
			dumpster.setStatus("GREEN");
		}
        
        return dumpster;

        
    }

    public boolean assignDumpsterPlant(long RP_id, long d_id, String token) {
        Plant plant = plantService.getPlantById(RP_id);
        if (plant == null) {
            throw new IllegalArgumentException("Plant with ID " + RP_id + " not found.");
        }

        Employee employee = authService.getUserByToken(token);
        if (employee == null) {
            throw new IllegalArgumentException("Employee with token " + token + " not found.");
        }

        boolean ownsDumpster = employee.getDumpsters().stream().anyMatch(d -> d.getId() == d_id);
        if (!ownsDumpster) {
            throw new IllegalArgumentException("Employee does not own dumpster ID " + d_id);
        }
        Dumpster dumpster = dumpsterRepository.get(d_id);

        if (dumpster.getCapacity()>plant.getCapacity()) {
            System.out.println("Plant " + RP_id + " does not have enough capacity.");
            return false;
        }

        Usage usage = new Usage(LocalDate.now(), dumpster, plant);
        usageRecords.add(usage);

        plant.setCapacity(plant.getCapacity() - dumpster.getCapacity());
        dumpster.setStatus("GREEN");
        dumpster.setCapacity(0);
        System.out.println("Assigned " + dumpster.getCapacity() + " dumpsters to plant " + RP_id +
                           " by employee " + employee.getName());
        return true;
    }

    public static void addUsage(Usage usage) {
        if (usage != null) usageRecords.add(usage);
    }
}
