package controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jdbc.dao.LineDAO;
import jdbc.dao.RouteDAO;
import jdbc.dao.StationDAO;
import model.Station;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/database")
public class DatabaseController {

	@GET
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject databaseUpdate() throws JSONException {
		new StationDAO().updateContent();
		new LineDAO().updateContent();
		new RouteDAO().updateContent();
		
		insertMissingStation();
		
		JSONObject response = new JSONObject();
		response.put("success", true);

		System.out.println("DatabaseController: database updated.");

		return response;
	}
	
	public void insertMissingStation() {
		Station station = new Station();
		station.setId(189);
		station.setLatitude(0f);
		station.setLongitude(0f);
		station.setName("Hogwarts Express");
		station.setDisplayName("Hogwarts Express");
		station.setZone(0f);
		station.setTotalLines(0);
		station.setRail(0);
		
		new StationDAO().add(station);
	}

}