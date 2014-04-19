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
import model.Line;

import com.csvreader.CsvReader;

public class LineDAO {
	
	private Connection connection;
	
	public LineDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public List<Line> getList() {
		try {
			String sql = "SELECT * FROM lu_lines";
			PreparedStatement statement = this.connection.prepareStatement(sql);
			
			ResultSet result = statement.executeQuery();
			
			List<Line> lines = new ArrayList<Line>();
			
			while(result.next()) {
				Line line = new Line();
				line.setStation1(result.getInt("station1"));
				line.setStation2(result.getInt("station2"));
				line.setLine(result.getInt("line"));
				
				lines.add(line);
			}
			
			result.close();
			statement.close();
			
			return lines;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void add(Line line) {
		try {
			String sql = "INSERT INTO lu_lines (station1, station2, line) values (?, ?, ?)";
			PreparedStatement statement = this.connection.prepareStatement(sql);
			
			statement.setInt(1, line.getStation1());
			statement.setInt(2, line.getStation2());
			statement.setInt(3, line.getLine());
			
			statement.execute();
			
			statement.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public void update() {
		try {
			String sql = "DELETE FROM lu_lines";
			PreparedStatement statement = this.connection.prepareStatement(sql);
			
			statement.execute();
			
			statement.close();
			
			try {
				// TODO Program CSV upload
				CsvReader lines = new CsvReader("/Users/Filipe/Dropbox/Workspace/Maquinarus/Projects/London Underground/data/lines.csv");
			
				lines.readHeaders();

				while (lines.readRecord()) {
					Line line = new Line();
					line.setStation1(Integer.parseInt(lines.get("station1")));
					line.setStation2(Integer.parseInt(lines.get("station2")));
					line.setLine(Integer.parseInt(lines.get("line")));
					
					add(line);
				}
		
				lines.close();
				
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
