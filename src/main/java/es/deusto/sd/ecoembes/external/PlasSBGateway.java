package es.deusto.sd.ecoembes.external;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PlasSBGateway implements IPlantGateway {

    private final String baseUrl;
    private final HttpClient client;

    public PlasSBGateway(String baseUrl) {
        this.baseUrl = baseUrl;
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public int getCapacity(String plantId, String date) throws Exception {
        // GET para ver capacidad
        String url = baseUrl + "plants/" + plantId + "/capacities?date=" + date;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            // Si falla, devolvemos -1 o lanzamos error
            return -1; 
        }
        return Integer.parseInt(response.body().trim());
    }

    @Override
    public String notifyAssignment(String plantId, long dumpster, int containers) throws Exception {
        // Usar el plantId proporcionado (se espera que sea un id numérico para PlasSB)
        String url = baseUrl + "plants/" + plantId + "/capacity?processed=" + containers;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.noBody()) // PUT sin cuerpo, solo params
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return "OK";
        } else {
            return "ERROR: " + response.statusCode();
        }
    }
}