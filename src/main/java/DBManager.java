import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.Singular;
import lombok.SneakyThrows;

public class DBManager {

	public static final String dbUrl = "jdbc:postgresql://localhost:5432/test";
	public static Connection conn;
	public static JdbcConnection dbConnection;
	public static Database database;

	static {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(dbUrl, "postgres", "");
			dbConnection = new JdbcConnection(conn);
			database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(dbConnection);
		} catch (ClassNotFoundException | SQLException | DatabaseException e) {
			e.printStackTrace();
		}
	}

	@SneakyThrows public static void createTenant(String tenantName) {
		createEmptySchema(tenantName);
		database.setDefaultSchemaName(tenantName);
		populateDefaultSchema();
	}

	@SneakyThrows private static void createEmptySchema(String schemaName) {
		dbConnection.prepareStatement(String.format("create schema if not exists %s;", schemaName)).executeUpdate();
	}

	@SneakyThrows private static void populateDefaultSchema() {
		Liquibase liquibase = new Liquibase("changelog.yaml", new ClassLoaderResourceAccessor(), database);
		liquibase.update("");
	}
}
