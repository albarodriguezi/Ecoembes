/**
 * This code is based on solutions provided by ChatGPT 4o and 
 * adapted using GitHub Copilot. It has been thoroughly reviewed 
 * and validated to ensure correctness and that it is free of errors.
 */
package es.deusto.sd.ecoembes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.deusto.sd.ecoembes.entity.Dumpster;
import es.deusto.sd.ecoembes.entity.Employee;
import es.deusto.sd.ecoembes.entity.Plant;
import es.deusto.sd.ecoembes.entity.Registry;
import es.deusto.sd.ecoembes.service.AuthService;
import es.deusto.sd.ecoembes.service.DumpsterService;
import es.deusto.sd.ecoembes.service.PlantService;

@Configuration
public class DataInitializer {

	private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
	
    @Bean
    CommandLineRunner initData(PlantService plantService, DumpsterService dumpsterService,AuthService AuthService,  EmployeeRepository employeeRepository, DumpsterRepository dumpsterRepository, AssignmentRepository assignmentRepository, UsageRepository usageRepository) {
		return args -> {	
			
			//create 2 plants
			Plant p1 = new Plant(1L, 28001, "Bilbao", "Calle Gran Vía 45", 300);
			Plant p2 = new Plant(2L, 8001, "Pamplona", "Avenida Carlos III 120", 400);
			
			plantService.addPlant(p1);
			plantService.addPlant(p2);
		

			logger.info("Plants saved!");

			//Create 5 dumpsters
			Dumpster d1 = new Dumpster(1L, 28001, "Bilbao", "Calle Gran Vía 120", "Organic",130);
			Dumpster d2 = new Dumpster(2L, 8001, "Bilbao", "Calle Gran Vía 120", "Plastic",20);
			Dumpster d3 = new Dumpster(3L, 28001, "Bilbao", "Calle Gran Vía 120", "Glass",90);
			Dumpster d4 = new Dumpster(4L, 8001, "Pamplona", "Avenida Carlos III 45", "Paper",60);
			Dumpster d5 = new Dumpster(5L, 8001, "Pamplona", "Avenida Carlos III 45", "Metal",40);

			
				
			dumpsterService.addDumpster(d1);
			dumpsterService.addDumpster(d2);
			dumpsterService.addDumpster(d3);
			dumpsterService.addDumpster(d4);
			dumpsterService.addDumpster(d5);

			logger.info("Dumpsters saved!");
			
			//create 6 employees
	        Employee e1 = new Employee(1L, "Carlos Gómez", "carlos.gomez@email.com", "C@rlosG123!",
	                LocalDate.of(1985, 3, 15), 2800.00);


	        Employee e2 = new Employee(2L, "Laura Martínez", "laura.martinez@email.com", "L@uraM2023",
	                LocalDate.of(1990, 7, 22), 3100.50);
	

	        Employee e3 = new Employee(3L, "Andrés López", "andres.lopez@email.com", "Andr3sL!456",
	                LocalDate.of(1982, 11, 5), 2950.75);
	  

	        Employee e4 = new Employee(4L, "Sofía Pérez", "sofia.perez@email.com", "S0fi@P789!",
	                LocalDate.of(1995, 2, 28), 2600.00);
	     

	        Employee e5 = new Employee(5L, "Diego Ruiz", "string", "string",
	                LocalDate.of(1988, 9, 12), 3250.90);


	        Employee e6 = new Employee(6L, "Elena Torres", "email@email.com", "password",
	                LocalDate.of(1993, 6, 8), 2750.30);
	  
	        AuthService.addUser(e1);
	        AuthService.addUser(e2);
	        AuthService.addUser(e3);
	        AuthService.addUser(e4);
	        AuthService.addUser(e5);
	        AuthService.addUser(e6);
	        
	        logger.info("Employees saved!");
	        
	        
	        //create usage (dumpsters assigned to plants over time)

	        Registry u1 = new Registry(LocalDate.of(2024, 1, 15),d1.getStatus() ,d1); // Bilbao - Organic
	        Registry u2 = new Registry(LocalDate.of(2024, 2, 10),d2.getStatus() ,d2); // Bilbao - Plastic
	        Registry u3 = new Registry(LocalDate.of(2024, 3, 5),d3.getStatus() ,d3);  // Bilbao - Glass
	        Registry u4 = new Registry(LocalDate.of(2024, 1, 20),d4.getStatus() ,d4); // Pamplona - Paper
	        Registry u5 = new Registry(LocalDate.of(2024, 2, 18),d5.getStatus() ,d5); // Pamplona - Metal

	        // mixtos
	        Registry u6 = new Registry(LocalDate.of(2024, 4, 25),d1.getStatus(), d1);
	        Registry u7 = new Registry(LocalDate.of(2024, 5, 10),d2.getStatus(), d2);
	        Registry u8 = new Registry(LocalDate.of(2024, 6, 2),d4.getStatus(), d4);
	        Registry u9 = new Registry(LocalDate.of(2024, 7, 12),d5.getStatus(), d5);
	        Registry u10 = new Registry(LocalDate.of(2024, 8, 3),d3.getStatus(), d3);

	        DumpsterService.addUsage(u1);
	        DumpsterService.addUsage(u2);
	        DumpsterService.addUsage(u3);
	        DumpsterService.addUsage(u4);
	        DumpsterService.addUsage(u5);
	        DumpsterService.addUsage(u6);
	        DumpsterService.addUsage(u7);
	        DumpsterService.addUsage(u8);
	        DumpsterService.addUsage(u9);
	        DumpsterService.addUsage(u10);
	        logger.info("Usage records saved!");

			// calendar
			Calendar calendar = Calendar.getInstance();
			
			// Set calendar to December 31, current year
			calendar.set(Calendar.MONTH, Calendar.DECEMBER);
			calendar.set(Calendar.DAY_OF_MONTH, 31);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			
			Date auctionEndDate = calendar.getTime();
			
								
		};
	}
}
