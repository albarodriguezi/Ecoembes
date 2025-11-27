package es.deusto.sd.ecoembes.external;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

public class PlasSBGateway implements IPlantGateway {

	 	private final String baseUrl;
	    private final HttpClient client;

	    public PlasSBGateway(@Value("${plassb.base.url}") String baseUrl) {
	        this.baseUrl = baseUrl;
	        this.client = HttpClient.newHttpClient();
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

	        // Comprobar HTTP status
	        if (response.statusCode() != 200) {
	            throw new RuntimeException("PlasSB server error: HTTP " + response.statusCode());
	        }

	        // Parsear JSON del tipo: { "capacity": 123 }
	        JSONObject json = new JSONObject(response.body());

	        if (!json.has("capacity")) {
	            throw new RuntimeException("Invalid JSON from PlasSB: missing 'capacity'");
	        }

	        return json.getInt("capacity");
	    }

		@Override
		public String notifyAssignment(int dumpsters, int containers) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

}
