/**
 * This code is based on solutions provided by ChatGPT 4o and 
 * adapted using GitHub Copilot. It has been thoroughly reviewed 
 * and validated to ensure correctness and that it is free of errors.
 */
package es.deusto.sd.auctions;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.deusto.sd.auctions.entity.Dumpster;
import es.deusto.sd.auctions.entity.Employee;
import es.deusto.sd.auctions.entity.Plant;
import es.deusto.sd.auctions.service.AuctionsService;
import es.deusto.sd.auctions.service.AuthService;
import es.deusto.sd.auctions.service.DumpsterService;
import es.deusto.sd.auctions.service.PlantService;

@Configuration
public class DataInitializer {

	private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
	
    @Bean
    CommandLineRunner initData(PlantService plantService, DumpsterService dumpsterService,AuthService AuthService) {
		return args -> {	
			
			//create 2 plants
			Plant p1 = new Plant(1L, 28001, "Bilbao", "Calle Gran Vía 45", 300);
			Plant p2 = new Plant(2L, 8001, "Pamplona", "Avenida Carlos III 120", 400);
			
			plantService.addPlant(p1);
			plantService.addPlant(p2);
		

			logger.info("Plants saved!");

			//Create 5 dumpsters
			Dumpster d1 = new Dumpster(1L, 28001, "Bilbao", "Calle Gran Vía 120", "Organic");
			Dumpster d2 = new Dumpster(2L, 28001, "Bilbao", "Calle Gran Vía 120", "Plastic");
			Dumpster d3 = new Dumpster(3L, 28001, "Bilbao", "Calle Gran Vía 120", "Glass");
			Dumpster d4 = new Dumpster(4L, 8001, "Pamplona", "Avenida Carlos III 45", "Paper");
			Dumpster d5 = new Dumpster(5L, 8001, "Pamplona", "Avenida Carlos III 45", "Metal");

			
				
			dumpsterService.addDumpster(d1);
			dumpsterService.addDumpster(d2);
			dumpsterService.addDumpster(d3);
			dumpsterService.addDumpster(d4);
			dumpsterService.addDumpster(d5);

			logger.info("Dumpsters saved!");
			
			//create 6 employees
	        Employee e1 = new Employee(1L, "Carlos Gómez", "carlos.gomez@email.com", "C@rlosG123!",
	                LocalDate.of(1985, 3, 15), 2800.00);
	        e1.setDumpsters(List.of(d1, d2));
	        e1.setPlants(List.of(p1));

	        Employee e2 = new Employee(2L, "Laura Martínez", "laura.martinez@email.com", "L@uraM2023",
	                LocalDate.of(1990, 7, 22), 3100.50);
	        e2.setDumpsters(List.of(d3));
	        e2.setPlants(List.of(p1));

	        Employee e3 = new Employee(3L, "Andrés López", "andres.lopez@email.com", "Andr3sL!456",
	                LocalDate.of(1982, 11, 5), 2950.75);
	        e3.setDumpsters(List.of(d4));
	        e3.setPlants(List.of(p2));

	        Employee e4 = new Employee(4L, "Sofía Pérez", "sofia.perez@email.com", "S0fi@P789!",
	                LocalDate.of(1995, 2, 28), 2600.00);
	        e4.setDumpsters(List.of(d5));
	        e4.setPlants(List.of(p2));

	        Employee e5 = new Employee(5L, "Diego Ruiz", "diego.ruiz@email.com", "Di3goR#987",
	                LocalDate.of(1988, 9, 12), 3250.90);
	        e5.setDumpsters(List.of(d1, d3));
	        e5.setPlants(List.of(p1, p2));

	        Employee e6 = new Employee(6L, "Elena Torres", "elena.torres@email.com", "El3naT2023!",
	                LocalDate.of(1993, 6, 8), 2750.30);
	        e6.setDumpsters(List.of(d2, d5));
	        e6.setPlants(List.of(p2));
	        
	        AuthService.addUser(e1);
	        AuthService.addUser(e2);
	        AuthService.addUser(e3);
	        AuthService.addUser(e4);
	        AuthService.addUser(e5);
	        AuthService.addUser(e6);
	        
	        logger.info("Employees saved!");

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