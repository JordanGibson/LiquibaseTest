import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	public static final String dbUrl = "jdbc:postgresql://localhost:5432/test";
	public static Connection conn;
	public static JdbcConnection dbConnection;

	static {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(dbUrl, "postgres", "");
			dbConnection = new JdbcConnection(conn);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@SneakyThrows public static void createTenant(String tenantName) {
		createEmptySchema(tenantName);
		setDefaultSchema(tenantName);
		Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(dbConnection);
		populateDefaultSchema(database);
	}

	@SneakyThrows private static void createEmptySchema(String schemaName) {
		var statement = dbConnection.prepareStatement(String.format("create schema if not exists %s;", schemaName));
		statement.execute();
	}

	@SneakyThrows private static void setDefaultSchema(String schemaName) {
		var statement = dbConnection.prepareStatement(String.format("SET search_path TO %s;", schemaName));
		statement.execute();
	}

	@SneakyThrows private static void populateDefaultSchema(Database database) {
		Liquibase liquibase = new Liquibase("changelog.yaml", new ClassLoaderResourceAccessor(), database);
		liquibase.update("");
	}
}


