package es.deusto.sd.ecoembes.external;

import java.io.IOException;

public interface IPlantGateway {

    int getCapacity(String plantId, String date) throws Exception;

    // plantId: remote plant identifier (name for socket gateways or numeric id for HTTP gateways)
    String notifyAssignment(String plantId, long dumpster, int containers) throws Exception;
}