package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
	
	private Connection connection = null;
    private final String url = "jdbc:mysql://";
    private final String serverName = "mysql12.maquinarus.com";
    private final String portNumber = "3306";
    private final String databaseName = "maquinarus4";
    private final String userName = "maquinarus4";
    private final String password = "brasilct";
    
    private String getConnectionUrl() {
        return url + serverName + ":" + portNumber + "/" + databaseName ;
    }
	
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(getConnectionUrl(), userName, password);
            if (connection != null) {
                System.out.println("ConnectionFactory: database connected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ConnectionFactory: " + e.getMessage());
        }
        return connection;
    }
}
