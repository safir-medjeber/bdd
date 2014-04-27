import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBase{
	
	Connection connection;
	
	public ConnectionBase(String user, String password) throws SQLException {
		connection = DriverManager.getConnection("jdbc:postgresql://localhost/base", user, password);
	}
	
	public void close() throws SQLException{
		connection.close();
	}
	
	
}