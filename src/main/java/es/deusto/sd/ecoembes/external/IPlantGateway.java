package es.deusto.sd.ecoembes.external;

import java.io.IOException;

public interface IPlantGateway {

    int getCapacity(String plantId, String date) throws Exception;

    String notifyAssignment(long dumpsters, int containers) throws Exception;
}

