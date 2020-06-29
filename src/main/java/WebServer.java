import java.io.BufferedReader;
import java.io.IOException;
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
		while (true) {
			Socket socket = server.accept();
			handleRequest(socket);
		}
	}

	@SneakyThrows private void handleRequest(Socket socket) {
		String request = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
		if (request.startsWith("GET /newTenant")) {
			Pattern pattern = Pattern.compile("name=([a-zA-Z0-9_]*) ");
			Matcher matcher = pattern.matcher(request);
			if(matcher.find())
				DBManager.createTenant(matcher.group(0));
		}
	}
}
