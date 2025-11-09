package es.deusto.sd.auctions.dto;

import java.util.ArrayList;
import java.util.List;

public class StatusDTO {
	private long id;
	private List<String> statuses = new ArrayList<>();
	
	public StatusDTO() { }
	
	public StatusDTO(long id, List<String> statuses) {
		this.id = id;
		this.statuses = statuses;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public List<String> getStatuses() {
		return statuses;
	}
	
	public void setStatuses(List<String> statuses) {
		this.statuses = statuses;
	}
	
	
}
