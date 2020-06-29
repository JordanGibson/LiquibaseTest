import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Program {

	@SneakyThrows public static void main(String[] args) {
		final ServerSocket server = new ServerSocket(8080);
		System.out.println("Listening for connection on port 8080 ....");
		while (true) {
			Socket socket = server.accept();
			handleRequest(socket);
		}

	}

	private static void handleRequest(Socket socket) throws IOException {
		String req = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
		if (req.startsWith("GET /newTenant")) {
			String tenantName = req.split("name=")[1].split(" ")[0];
			createTenant(tenantName);
		}
	}

	@SneakyThrows public static void createTenant(String tenantName) {
		String dbUrl = "jdbc:postgresql://localhost:5432/test";
		Class.forName("org.postgresql.Driver");
		Connection conn = DriverManager.getConnection(dbUrl, "postgres", "");
		JdbcConnection dbConnection = new JdbcConnection(conn);
		dbConnection.prepareStatement(String.format("create schema if not exists %s;", tenantName)).executeUpdate();
		Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(dbConnection);
		database.setDefaultSchemaName(tenantName);
		Liquibase liquibase = new Liquibase("changelog.yaml", new ClassLoaderResourceAccessor(), database);
		liquibase.update("");
	}

}
