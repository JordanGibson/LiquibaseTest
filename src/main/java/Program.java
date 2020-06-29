import lombok.SneakyThrows;

public class Program {

	@SneakyThrows public static void main(String[] args) {
		WebServer webServer = new WebServer();
		webServer.start();
	}

}
