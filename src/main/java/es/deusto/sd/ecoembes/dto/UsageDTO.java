package es.deusto.sd.ecoembes.dto;

import java.util.ArrayList;
import java.util.List;

import es.deusto.sd.ecoembes.entity.Registry;

public class UsageDTO {
	private long d_id;
	private List<Registry> usages = new ArrayList<>();
	
	public UsageDTO() {}
	
	public UsageDTO(List<Registry> usages,long d_id) {
		this.d_id= d_id;
		this.usages = usages;
	}

	public List<Registry> getUsages() {
		return usages;
	}

	public void setUsages(List<Registry> usages) {
		this.usages = usages;
	}

	public long getD_id() {
		return d_id;
	}

	public void setD_id(long d_id) {
		this.d_id = d_id;
	}
	
	
}
