package es.deusto.sd.ecoembes.external;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class PlasSBGateway implements IPlantGateway {

    private final String baseUrl;
    private final HttpClient client;


    // Constructor accepts base URL and remote numeric id for the plant
    public PlasSBGateway(String baseUrl) {
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

    }

    @Override
    public int getCapacity(String date) throws Exception {
        try {
            String url = baseUrl + "plants/capacities?date=" + date;
            System.out.println("PlasSBGateway: GET " + url);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .header("Accept", "application/json")
                    .timeout(Duration.ofSeconds(5))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.out.println("PlasSBGateway: non-200 response " + response.statusCode());
                return -1;
            }
            try {
                return Integer.parseInt(response.body().trim());
            } catch (NumberFormatException nfe) {
                System.out.println("PlasSBGateway: failed to parse capacity: " + response.body());
                return -1;
            }
        } catch (Exception e) {
            System.out.println("PlasSBGateway.getCapacity error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public String notifyAssignment(long dumpster, int containers) throws Exception {
        try {
            String url = baseUrl + "plants/updates?dumpster="+dumpster+"&processed=" + containers;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .timeout(Duration.ofSeconds(5))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return "OK";
            } else {
                return "ERROR: " + response.statusCode();
            }
        } catch (Exception e) {
            System.out.println("PlasSBGateway.notifyAssignment error: " + e.getMessage());
            throw e;
        }
    }
}