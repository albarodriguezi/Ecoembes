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
    public int getCapacity(String date) throws Exception {
        String url = baseUrl + "/capacity?date=" + date;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Check HTTP status
        if (response.statusCode() != 200) {
            throw new RuntimeException("PlasSB server error: HTTP " + response.statusCode());
        }

        // Parse JSON using Jackson
        Map<String, Object> jsonMap = objectMapper.readValue(response.body(), Map.class);

        if (!jsonMap.containsKey("capacity")) {
            throw new RuntimeException("Invalid JSON from PlasSB: missing 'capacity'");
        }

        return (Integer) jsonMap.get("capacity");
    }

    @Override
    public String notifyAssignment(int dumpsters, int containers) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
}
