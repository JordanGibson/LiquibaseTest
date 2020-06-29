import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.SneakyThrows;

public class WebServer {
	public final ServerSocket server;

	@SneakyThrows WebServer() {
		server = new ServerSocket(8080);
	}

	@SneakyThrows public void start() {
		while (true) {
			System.out.println("Listening for connection on port 8080 ....");
			Socket socket = server.accept();
			handleRequest(socket);
		}
	}

	@SneakyThrows private void handleRequest(Socket socket) {
		String request = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
		if (request != null && request.startsWith("GET /newTenant")) {
			Pattern pattern = Pattern.compile("name=(.*)");
			Matcher matcher = pattern.matcher(request);
			if (matcher.find())
				DBManager.createTenant(matcher.group(1).split(" ")[0]);
		}
	}
}
