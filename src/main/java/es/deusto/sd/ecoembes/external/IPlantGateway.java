package es.deusto.sd.ecoembes.external;

public interface IPlantGateway {

    // Gateway is created per plant; methods don't need plantName parameter anymore
    int getCapacity(String date) throws Exception;

    String notifyAssignment(long dumpster, int containers) throws Exception;
}