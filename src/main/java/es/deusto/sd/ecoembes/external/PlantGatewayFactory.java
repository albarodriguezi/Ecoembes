package es.deusto.sd.ecoembes.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PlantGatewayFactory {

    private static String contSocketHost;
    private static int contSocketPort;
    private static String baseUrl;

    // Spring will call this constructor and inject values
    public PlantGatewayFactory(
            @Value("${contsocket.host}") String host,
            @Value("${contsocket.port}") int port,
            @Value("${plassb.base.url}") String url) {

        contSocketHost = host;
        contSocketPort = port;
        baseUrl = url;
    }

    public static IPlantGateway create(String type) {
        switch (type) {
            case "CONT_SOCKET":
                return new ContSocketGateway(contSocketHost, contSocketPort);

            case "PLASSB":
                return new PlasSBGateway(baseUrl);

            default:
                throw new IllegalArgumentException("Unknown plant type: " + type);
        }
    }
}
