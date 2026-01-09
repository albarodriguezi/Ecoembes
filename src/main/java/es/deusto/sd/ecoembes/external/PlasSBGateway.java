package es.deusto.sd.ecoembes.external;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


public class PlasSBGateway implements IPlantGateway {

    private final String baseUrl;
    private final HttpClient client;

    public PlasSBGateway(String baseUrl) {
        this.baseUrl = baseUrl;
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public int getCapacity(String plantId, String date) throws Exception {
        String url = baseUrl + "plants/" + plantId + "/capacities?date=" + date;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                "Error del servidor PlasSB: HTTP " + response.statusCode() + " | Cuerpo: " + response.body()
            );
        }

        // 🔥 NO HAGAS JSON PARSING AQUÍ — ES SOLO UN NÚMERO
        String body = response.body().trim();

        try {
            return Integer.parseInt(body);  // <-- AHORA FUNCIONA
        } catch (NumberFormatException e) {
            throw new RuntimeException("Respuesta inválida, se esperaba un número pero llegó: " + body);
        }
    }

    @Override
    public String notifyAssignment(int dumpsters, int containers) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
}