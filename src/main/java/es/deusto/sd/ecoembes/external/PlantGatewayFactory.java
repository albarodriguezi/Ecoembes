package es.deusto.sd.ecoembes.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PlantGatewayFactory {

    private final String contSocketHost;
    private final int contSocketPort;
    private final String baseUrl;

    // Spring will call this constructor and inject values
    public PlantGatewayFactory(
            @Value("${contsocket.host}") String host,
            @Value("${contsocket.port}") int port,
            @Value("${plassb.base.url}") String url) {

        this.contSocketHost = host;
        this.contSocketPort = port;
        this.baseUrl = url;
    }

    // Decide which gateway to use based on the plant name
    public IPlantGateway createByPlantName(String plantName) {
        if (plantName == null) {
            throw new IllegalArgumentException("plantName is null");
        }

        // Use naming convention: names starting with "CONT" -> socket, otherwise PLASSB
        if (plantName.toUpperCase().startsWith("CONT")) {
            return new ContSocketGateway(contSocketHost, contSocketPort);
        } else {
            return new PlasSBGateway(baseUrl);
        }
    }
}