package es.deusto.sd.ecoembes.external;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ContSocketGateway implements IPlantGateway {
    
    private final String host;
    private final int port;
    private final String plantName; // business identifier for this gateway

    public ContSocketGateway(String host, int port, String plantName) {
        this.host = host;
        this.port = port;
        this.plantName = plantName;
    }

    private String sendMessage(String msg) throws IOException {
        try (
            Socket socket = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            System.out.println("ContSocketGateway: sending -> " + msg);
            out.println(msg);
            String resp = in.readLine();
            System.out.println("ContSocketGateway: received <- " + resp);
            return resp;
        }
    }

    @Override
    public int getCapacity(String date) throws Exception {
        try {
            // For socket gateway send the business plantName and date
            String response = sendMessage("GET_CAPACITY;" + date + ";" + plantName);
            if (response == null) {
                System.out.println("ContSocketGateway: response is null");
                return -1;
            }
            try {
                return Integer.parseInt(response.trim());
            } catch (NumberFormatException nfe) {
                System.out.println("ContSocketGateway: failed to parse capacity from response='" + response + "'");
                throw nfe;
            }
        } catch (IOException ioe) {
            System.out.println("ContSocketGateway.getCapacity IO error: " + ioe.getMessage());
            throw ioe;
        }
    }

    @Override
    public String notifyAssignment(long dumpster, int containers) throws Exception {
        try {
            // Include plant identifier so the remote socket server knows which plant
            String msg = "NOTIFY;" + plantName + ";" + dumpster + ";" + containers;
            String resp = sendMessage(msg);
            return resp;
        } catch (IOException ioe) {
            System.out.println("ContSocketGateway.notifyAssignment IO error: " + ioe.getMessage());
            throw ioe;
        }
    }
}