package es.deusto.sd.auctions.dto;

import java.util.ArrayList;
import java.util.List;

import es.deusto.sd.auctions.entity.Usage;

public class UsageDTO {
	private List<Usage> usages = new ArrayList<>();
	
	public UsageDTO() {}
	
	public UsageDTO(List<Usage> usages) {
		this.usages = usages;
	}

	public List<Usage> getUsages() {
		return usages;
	}

	public void setUsages(List<Usage> usages) {
		this.usages = usages;
	}
	
	
}
