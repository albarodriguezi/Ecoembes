package es.deusto.sd.auctions.service;

import java.util.HashMap;
import java.util.Map;

import es.deusto.sd.auctions.entity.Article;
import es.deusto.sd.auctions.entity.Bid;
import es.deusto.sd.auctions.entity.Category;
import es.deusto.sd.auctions.entity.Dumpster;
import es.deusto.sd.auctions.entity.Employee;
import es.deusto.sd.auctions.entity.User;

public class DumpsterService {

	private static Map<Long, Dumpster> dumpsterRepository = new HashMap<>();
	private static AuthService authService;
	
	public void createDumpster(long dumpsterId, int PC, String city, String address, String type, String token) {
		// Retrieve the article by ID
		Employee employee = authService.getUserByToken(token);
		employee.createDumpster(dumpsterId,PC,city,address, type);
	}
	
	public void addDumpster(Dumpster dumpster) {
		if (dumpster != null) {
			dumpsterRepository.putIfAbsent(dumpster.getId(), dumpster);
		}
	}
}
