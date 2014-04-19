package controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jdbc.dao.LineDAO;
import jdbc.dao.RouteDAO;
import jdbc.dao.StationDAO;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/database")
public class DatabaseController {

	@GET
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject updateDatabase() throws JSONException {
		StationDAO stationDAO = new StationDAO();
		stationDAO.updateContent();

		LineDAO lineDAO = new LineDAO();
		lineDAO.updateContent();

		RouteDAO routeDAO = new RouteDAO();
		routeDAO.updateContent();

		JSONObject response = new JSONObject();
		response.put("success", true);

		System.out.println("DatabaseController: database updated.");

		return response;
	}

}