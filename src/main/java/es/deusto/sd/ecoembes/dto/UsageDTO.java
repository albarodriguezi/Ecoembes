package es.deusto.sd.ecoembes.dto;

import java.util.ArrayList;
import java.util.List;

import es.deusto.sd.ecoembes.entity.Usage;

public class UsageDTO {
	private long d_id;
	private List<Usage> usages = new ArrayList<>();
	
	public UsageDTO() {}
	
	public UsageDTO(List<Usage> usages,long d_id) {
		this.d_id= d_id;
		this.usages = usages;
	}

	public List<Usage> getUsages() {
		return usages;
	}

	public void setUsages(List<Usage> usages) {
		this.usages = usages;
	}

	public long getD_id() {
		return d_id;
	}

	public void setD_id(long d_id) {
		this.d_id = d_id;
	}
	
	
}
