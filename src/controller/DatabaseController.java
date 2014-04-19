package controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import jdbc.dao.LineDAO;
import jdbc.dao.RouteDAO;
import jdbc.dao.StationDAO;

@Path("/database")
public class DatabaseController {

	@GET
	@Path("/update")
	public String updateDatabase() {
		StationDAO stationDAO = new StationDAO();
		stationDAO.update();

		LineDAO lineDAO = new LineDAO();
		lineDAO.update();
		
		RouteDAO routeDAO = new RouteDAO();
		routeDAO.update();

		System.out.println("DatabaseController: database updated.");

		return "Database updated.";
	}

}