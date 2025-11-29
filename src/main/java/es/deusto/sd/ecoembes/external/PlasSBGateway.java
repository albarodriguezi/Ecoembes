package es.deusto.sd.ecoembes.external;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PlasSBGateway implements IPlantGateway {

    private final String baseUrl;
    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public PlasSBGateway(@Value("${plassb.base.url}") String baseUrl) {
        this.baseUrl = baseUrl;
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public int getCapacity(String plantId, String date) throws Exception {
        // CORRECCIÓN: La URL ahora incluye el ID de la planta para que el servidor externo sepa a quién consultar.
        String url = baseUrl + "/plant/" + plantId + "/capacity?date=" + date;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Verificamos el estado HTTP
        if (response.statusCode() != 200) {
            // Incluimos el cuerpo de la respuesta en el error para mejor depuración
            throw new RuntimeException("Error del servidor PlasSB: HTTP " + response.statusCode() + " | Cuerpo: " + response.body());
        }

        // Parseamos el JSON usando Jackson
        Map<String, Object> jsonMap = objectMapper.readValue(response.body(), Map.class);

        if (!jsonMap.containsKey("capacity")) {
            throw new RuntimeException("JSON inválido de PlasSB: falta 'capacity'");
        }
        
        // Usamos Number para un casteo más robusto (maneja tanto Integer como Double)
        Number capacityNumber = (Number) jsonMap.get("capacity");

        return capacityNumber.intValue();
    }

    @Override
    public String notifyAssignment(int dumpsters, int containers) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
}