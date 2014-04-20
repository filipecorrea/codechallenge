package jdbc.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Station;
import jdbc.ConnectionFactory;

import com.csvreader.CsvReader;

public class StationDAO {

	private Connection connection;

	public StationDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}

	public List<Station> getAll() {
		try {
			String sql = "SELECT * FROM lu_stations ORDER BY id";
			PreparedStatement statement = this.connection.prepareStatement(sql);

			ResultSet result = statement.executeQuery();

			List<Station> stations = new ArrayList<Station>();

			while (result.next()) {
				Station station = new Station();
				station.setId(result.getInt("id"));
				station.setLatitude(result.getFloat("latitude"));
				station.setLatitude(result.getFloat("longitude"));
				station.setName(result.getString("name"));
				station.setDisplayName(result.getString("display_name"));
				station.setZone(result.getFloat("zone"));
				station.setTotalLines(result.getInt("total_lines"));
				station.setRail(result.getInt("rail"));

				stations.add(station);
			}

			result.close();
			statement.close();

			return stations;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void add(Station station) {
		try {
			String sql = "INSERT INTO lu_stations (id, latitude, longitude, name, display_name, zone, total_lines, rail) values (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = this.connection.prepareStatement(sql);

			statement.setInt(1, station.getId());
			statement.setFloat(2, station.getLatitude());
			statement.setFloat(3, station.getLongitude());
			statement.setString(4, station.getName());
			statement.setString(5, station.getDisplayName());
			statement.setFloat(6, station.getZone());
			statement.setInt(7, station.getTotalLines());
			statement.setInt(8, station.getRail());

			statement.execute();

			statement.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Station findByName(String name) {
		try {
			String sql = "SELECT * FROM lu_stations WHERE name=?";
			PreparedStatement statement = this.connection.prepareStatement(sql);

			statement.setString(1, name);
			
			ResultSet result = statement.executeQuery();
			
			Station station = new Station();
			
			while(result.next()) {
				station.setId(result.getInt("id"));
				station.setLatitude(result.getFloat("latitude"));
				station.setLatitude(result.getFloat("longitude"));
				station.setName(result.getString("name"));
				station.setDisplayName(result.getString("display_name"));
				station.setZone(result.getFloat("zone"));
				station.setTotalLines(result.getInt("total_lines"));
				station.setRail(result.getInt("rail"));
			}
			
			result.close();
			statement.close();
			
			return station;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Integer> findLines(Integer id) {
		try {
			String sql = "SELECT DISTINCT line FROM lu_lines WHERE (station1=? OR station2=?)";
			PreparedStatement statement = this.connection.prepareStatement(sql);

			statement.setInt(1, id);
			statement.setInt(2, id);
			
			ResultSet result = statement.executeQuery();
			
			List<Integer> lines = new ArrayList<Integer>();
			
			while(result.next()) {
				lines.add(result.getInt("line"));
			}
			
			result.close();
			statement.close();
			
			return lines;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void updateContent() {
		try {
			String sql = "DELETE FROM lu_stations";
			PreparedStatement statement = this.connection.prepareStatement(sql);

			statement.execute();

			statement.close();

			try {
				// TODO Program CSV upload
				CsvReader stations = new CsvReader(
						"/Users/Filipe/Dropbox/Workspace/Maquinarus/Projects/London Underground/data/stations.csv");

				stations.readHeaders();

				while (stations.readRecord()) {
					Station station = new Station();
					station.setId(Integer.parseInt(stations.get("id")));
					station.setLatitude(Float.parseFloat(stations
							.get("latitude")));
					station.setLongitude(Float.parseFloat(stations
							.get("longitude")));
					station.setName(stations.get("name"));
					station.setDisplayName(stations.get("display_name"));
					station.setZone(Float.parseFloat(stations.get("zone")));
					station.setTotalLines(Integer.parseInt(stations
							.get("total_lines")));
					station.setRail(Integer.parseInt(stations.get("rail")));

					add(station);
				}

				stations.close();

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
