package es.deusto.sd.auctions.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import es.deusto.sd.auctions.entity.Dumpster;
import es.deusto.sd.auctions.entity.Employee;
import es.deusto.sd.auctions.entity.Plant;
import es.deusto.sd.auctions.entity.Usage;

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
        if (employee == null) {
            throw new IllegalArgumentException("Invalid token or expired session.");
        }
        employee.createDumpster(dumpsterId, PC, city, address, type);
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

    public List<String> checkDumpsterStatus(String token, int PC, LocalDate date) {
        Employee employee = authService.getUserByToken(token);
        if (employee == null) {
            throw new IllegalArgumentException("Invalid token or expired session.");
        }

        List<String> activity = new ArrayList<>();

        employee.getDumpsters().stream()
                .filter(d -> d.getPC() == PC)
                .forEach(d -> {
                    boolean usedThatDay = usageRecords.stream()
                            .anyMatch(u -> u.getDumpster().equals(d) && u.getDate().equals(date));
                    if (usedThatDay) {
                        activity.add("Dumpster ID " + d.getId() + " (" + d.getType() + ") was used on " + date);
                    } else {
                        activity.add("Dumpster ID " + d.getId() + " (" + d.getType() + ") had no activity on " + date);
                    }
                });

        return activity;
    }

    public boolean assignDumpsterPlant(long RP_id, List<Long> list_d_id, long E_id) {
        Plant plant = plantService.getPlantById(RP_id);
        if (plant == null) {
            throw new IllegalArgumentException("Plant with ID " + RP_id + " not found.");
        }

        Employee employee = authService.getUserById(E_id);
        if (employee == null) {
            throw new IllegalArgumentException("Employee with ID " + E_id + " not found.");
        }

        List<Dumpster> employeeDumpsters = new ArrayList<>();
        for (Long id : list_d_id) {
            Dumpster d = dumpsterRepository.get(id);
            if (d != null && employee.getDumpsters().contains(d)) {
                employeeDumpsters.add(d);
            }
        }

        if (employeeDumpsters.size() > plant.getCapacity()) {
            System.out.println("Plant " + RP_id + " does not have enough capacity.");
            return false;
        }

        for (Dumpster d : employeeDumpsters) {
            Usage usage = new Usage(LocalDate.now(), d, plant);
            usageRecords.add(usage);
        }

        plant.setCapacity(plant.getCapacity() - employeeDumpsters.size());
        System.out.println("Assigned " + employeeDumpsters.size() + " dumpsters to plant " + RP_id +
                           " by employee " + employee.getName());
        return true;
    }

    public static void addUsage(Usage usage) {
        if (usage != null) usageRecords.add(usage);
    }
}
