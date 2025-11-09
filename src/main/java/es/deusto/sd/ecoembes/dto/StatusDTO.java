package es.deusto.sd.ecoembes.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.deusto.sd.ecoembes.entity.Dumpster;

public class StatusDTO {
	private int id;
	private Map<Long, String> statuses = new HashMap<>();
	
	public StatusDTO() { }
	
	public StatusDTO(int id, Map<Long, String> statuses) {
		this.id = id;
		this.statuses = statuses;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Map<Long, String> getStatuses() {
		return statuses;
	}
	
	public void setStatuses(Map<Long, String> statuses) {
		this.statuses = statuses;
	}
	
	
}
