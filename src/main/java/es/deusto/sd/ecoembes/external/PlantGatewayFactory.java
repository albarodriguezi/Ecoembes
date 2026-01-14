package es.deusto.sd.ecoembes.external;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import es.deusto.sd.ecoembes.dao.PlantRepository;
import es.deusto.sd.ecoembes.entity.Plant;

@Component
public class PlantGatewayFactory {

    private final String contSocketHost;
    private final int contSocketPort;
    private final String baseUrl;
    private final PlantRepository plantRepository;

    // Cache one gateway instance per plantName to respect "1 gateway per plant" requirement
    private final Map<String, IPlantGateway> gatewayCache = new ConcurrentHashMap<>();

    // Spring will call this constructor and inject values
    public PlantGatewayFactory(
            @Value("${contsocket.host}") String host,
            @Value("${contsocket.port}") int port,
            @Value("${plassb.base.url}") String url,
            PlantRepository plantRepository) {

        this.contSocketHost = host;
        this.contSocketPort = port;
        this.baseUrl = url;
        this.plantRepository = plantRepository;
    }

    // Decide which gateway to use based on the plant's TYPE stored in DB (or derived from name)
    public IPlantGateway createByPlantName(String plantName) {
        if (plantName == null) {
            throw new IllegalArgumentException("plantName is null");
        }

        // Fetch plant from repository (single source of truth)
        Plant plant = plantRepository.findByName(plantName)
                .orElseThrow(() -> new IllegalArgumentException("Unknown plant: " + plantName));

        System.out.println("PlantGatewayFactory: resolved plant name='" + plantName + "' id=" + plant.getId() + " type=" + plant.getType());

        // Decide type based on plant attributes. Prefer an explicit type field if present; otherwise derive from name.
        String type = deriveTypeFromPlant(plant);

        System.out.println("PlantGatewayFactory: derived type='" + type + "' for plant='" + plantName + "'");

        final String key = plantName; // one gateway per plant
        return gatewayCache.computeIfAbsent(key, name -> {
            IPlantGateway gw;
            if ("CONT_BILBAO".equalsIgnoreCase(type)) {
                // pass plantName so socket server can identify which plant
                gw = new ContSocketGateway(contSocketHost, contSocketPort, plant.getName());
            } else if ("PLAS_PAMPLONA".equalsIgnoreCase(type)) {
                // pass remote numeric id expected by PlasSB HTTP API
                gw = new PlasSBGateway(baseUrl);
            } else {
                // default to HTTP gateway
                gw = new PlasSBGateway(baseUrl);
            }
            System.out.println("PlantGatewayFactory: created gateway instance " + gw.getClass().getSimpleName() + " for plant='" + plantName + "'");
            return gw;
        });
    }

    private String deriveTypeFromPlant(Plant plant) {
        // If the Plant entity ever contains an explicit type field, use it here (e.g., plant.getType()).
        // For now derive from name convention: names starting with "CONT" → CONT socket, otherwise PLAS.
        String name = plant.getName();
        if (name == null) return "PLAS";
        if (name.toUpperCase().startsWith("CONT")) return "CONT";
        if (name.toUpperCase().startsWith("PLAS")) return "PLAS";
        // prefer explicit type if provided
        if (plant.getType() != null && !plant.getType().isBlank()) return plant.getType();
        return "PLAS";
    }
}