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
            Socket socket = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            out.println(msg);
            return in.readLine();
        }
    }

    @Override
    public int getCapacity(String date) throws IOException {
        String response = sendMessage("GET_CAPACITY;" + date);
        return Integer.parseInt(response);
    }


	@Override
	public String notifyAssignment(int dumpsters, int containers) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
