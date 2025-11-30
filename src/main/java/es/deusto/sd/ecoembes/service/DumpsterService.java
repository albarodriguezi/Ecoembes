package es.deusto.sd.ecoembes.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import es.deusto.sd.ecoembes.dao.AssignmentRepository;
import es.deusto.sd.ecoembes.dao.DumpsterRepository;
import es.deusto.sd.ecoembes.dao.UsageRepository;
import es.deusto.sd.ecoembes.entity.Assignment;
import es.deusto.sd.ecoembes.entity.Dumpster;
import es.deusto.sd.ecoembes.entity.Employee;
import es.deusto.sd.ecoembes.entity.Plant;
import es.deusto.sd.ecoembes.entity.Registry;

@Service
public class DumpsterService {

    private final AuthService authService;

	private final DumpsterRepository dumpsterRepositoryJPA;
    private final UsageRepository usageRepositoryJPA;
    private final AssignmentRepository assignmentRepository;

    private static Map<Long, Dumpster> dumpsterRepository = new HashMap<>();
    private static List<Registry> usageRecords = new ArrayList<>();

    public DumpsterService(AuthService authService, PlantService plantService, DumpsterRepository dumpsterRepositoryJPA, UsageRepository usageRepositoryJPA, AssignmentRepository assignmentRepository) {
        this.authService = authService;
        this.dumpsterRepositoryJPA = dumpsterRepositoryJPA;
        this.usageRepositoryJPA = usageRepositoryJPA;
        this.assignmentRepository = assignmentRepository;
    }


    public void createDumpster(long dumpsterId, int PC, String city, String address, String type, String token) {
        Employee employee = authService.getUserByToken(token);
        System.out.println("Creating dumpster for employee with token: " + token);
        if (employee == null) {
        	System.out.println("Invalid token or expired session.");
            throw new IllegalArgumentException("Invalid token or expired session.");
        }
		Dumpster dumpster = new Dumpster(dumpsterId, PC, city, address, type);
		dumpsterRepositoryJPA.save(dumpster);
        System.out.println("Dumpster created: ID " + dumpsterId + ", PC " + PC + ", City " + city + ", Address " + address + ", Type " + type);
        
    }

    public void addDumpster(Dumpster dumpster) {
        if (dumpster != null) {
            dumpsterRepositoryJPA.save(dumpster);
        }
    }

    public List<Registry> queryDumpsterUsage(String token, long d_id, LocalDate i_date, LocalDate f_date) {
        Employee employee = authService.getUserByToken(token);
        if (employee == null) {
            throw new IllegalArgumentException("Invalid token or expired session.");
        }


        List<Registry> usagePatterns = new ArrayList<>();
        for (Registry u : usageRepositoryJPA.findAll()) {
            if (u.getDumpster() == d_id &&
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
        for (Registry d : usageRepositoryJPA.findAll()) {
			if (dumpsterRepositoryJPA.findById(d.getDumpster()).orElse(null).getPC() == PC && d.getDate().equals(date)) {
				long d_id = d.getDumpster();
				String status = d.getLevel();
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

		Dumpster dumpster = dumpsterRepositoryJPA.findById(id).orElse(null);
		if (dumpster == null) {
			throw new IllegalArgumentException("Dumpster with ID " + id + " not found.");
		}
		
		dumpster.setContainers(containers);
		if(containers > 120) {
			dumpster.setStatus("RED");
		} else if (containers >=80 && containers <120) {
			dumpster.setStatus("ORANGE");
		} else {
			dumpster.setStatus("GREEN");
		}
        
        return dumpster;

        
    }


    
    public int getDumpsterContainers(long RP_id, long d_id, String token) {


        Dumpster dumpster = dumpsterRepositoryJPA.findById(d_id).orElse(null);
		return dumpster.getContainers();
    }
    
    public void assignDumpsterPlant(long RP_id, long d_id, String token) {
    	Employee employee = authService.getUserByToken(token);
    	Dumpster dumpster = dumpsterRepositoryJPA.findById(d_id).orElse(null);
        Assignment assignment = new Assignment(employee,dumpster, RP_id);
		assignmentRepository.save(assignment);
		System.out.println("Assigned dumpster ID " + d_id + " to plant ID " + RP_id + " by employee " + employee.getEmail());
    }

    public void addUsage(Registry usage) {
        if (usage != null)
			try {
				this.usageRepositoryJPA.save(usage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
}
