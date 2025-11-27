package es.deusto.sd.ecoembes.external;

public interface IPlantGateway {

	    int getCapacity(String date) throws Exception;

	    String notifyAssignment(int dumpsters, int containers) throws Exception;
}

