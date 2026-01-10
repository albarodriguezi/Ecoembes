package es.deusto.sd.ecoembes.external;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ContSocketGateway implements IPlantGateway {
	
	private final String host;
    private final int port;

    public ContSocketGateway(String host, int port) {
        this.host = host;
        this.port = port;
    }


    private String sendMessage(String msg) throws IOException {
        try (
            // Creamos la conexión socket
            Socket socket = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            // Enviamos el mensaje
            out.println(msg);
            // Esperamos la respuesta
            return in.readLine();
        }
    }

    @Override
    public int getCapacity(String plantId, String date) throws IOException {
        // CORRECCIÓN: Incluimos el plantId en el mensaje para el servidor Socket. 
        // Asumimos que el formato de mensaje es: GET_CAPACITY;[plantId];[date]
        String response = sendMessage("GET_CAPACITY;" + date + ";" + plantId);
        // El servidor Socket debe devolver un String que se puede parsear a Integer
        return Integer.parseInt(response);
    }


	@Override
	public String notifyAssignment(long dumpster, int containers) throws Exception {
		// TODO Auto-generated method stub
		String response = sendMessage("NOTIFY_ASSIGNMENT;" + dumpster + ";" + containers);
		return response;

	}

}