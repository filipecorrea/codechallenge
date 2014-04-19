package jdbc.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.ConnectionFactory;
import model.Route;

import com.csvreader.CsvReader;

public class RouteDAO {
	
	private Connection connection;
	
	public RouteDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public List<Route> getList() {
		try {
			String sql = "SELECT * FROM lu_routes";
			PreparedStatement statement = this.connection.prepareStatement(sql);
			
			ResultSet result = statement.executeQuery();
			
			List<Route> routes = new ArrayList<Route>();
			
			while(result.next()) {
				Route route = new Route();
				route.setLine(result.getInt("line"));
				route.setName(result.getString("name"));
				route.setColour(result.getString("colour"));
				route.setStripe(result.getString("stripe"));
				
				routes.add(route);
			}
			
			result.close();
			statement.close();
			
			return routes;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void add(Route station) {
		try {
			String sql = "INSERT INTO lu_routes (line, name, colour, stripe) values (?, ?, ?, ?)";
			PreparedStatement statement = this.connection.prepareStatement(sql);
			
			statement.setInt(1, station.getLine());
			statement.setString(2, station.getName());
			statement.setString(3, station.getColour());
			statement.setString(4, station.getStripe());
			
			statement.execute();
			
			statement.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public void update() {
		try {
			String sql = "DELETE FROM lu_routes";
			PreparedStatement statement = this.connection.prepareStatement(sql);
			
			statement.execute();
			
			statement.close();
			
			try {
				// TODO Program CSV upload
				CsvReader routes = new CsvReader("/Users/Filipe/Dropbox/Workspace/Maquinarus/Projects/London Underground/data/routes.csv");
			
				routes.readHeaders();

				while (routes.readRecord()) {
					Route route = new Route();
					route.setLine(Integer.parseInt(routes.get("line")));
					route.setName(routes.get("name"));
					route.setColour(routes.get("colour"));
					route.setStripe(routes.get("stripe"));
					
					add(route);
				}
		
				routes.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
