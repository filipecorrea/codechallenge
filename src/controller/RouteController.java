package controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import jdbc.dao.LineDAO;
import jdbc.dao.StationDAO;
import model.Edge;
import model.Graph;
import model.Line;
import model.Station;
import model.Vertex;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import util.DijkstraAlgorithm;

@Path("/route")
public class RouteController {
	
	private List<Vertex> nodes;
	private List<Edge> edges;

	private JSONObject response;

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject getAllRoutes(@QueryParam("from") String origin, @QueryParam("to") String destination) throws JSONException {

		// TODO

		return response;
	}

	@GET
	@Path("/random")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject getRandomRoute() {
		
		// TODO
		
		return response;
	}

	@GET
	@Path("/short")
	public JSONObject getShortestRoute(@QueryParam("from") String origin, @QueryParam("to") String destination) throws JSONException {
		nodes = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();

		StationDAO stationDAO = new StationDAO();

		Station originStation = stationDAO.findByName(origin);
		Station destinationStation = stationDAO.findByName(destination);
		
		// Make index and ID correspondent
		nodes.add(new Vertex("0", "0"));
		
		for (Station station : stationDAO.getAll()) {
			Vertex vertex = new Vertex(Integer.toString(station.getId()), station.getName());
			nodes.add(vertex);
		}

		for (Line line : new LineDAO().getAll()) {
			addLane("Edge", line.getStation1(), line.getStation2(), 1);
			addLane("Edge", line.getStation2(), line.getStation1(), 1);
		}
		
		// Execute Dijkstra algorithm
		Graph graph = new Graph(nodes, edges);
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		dijkstra.execute(nodes.get(originStation.getId()));
		LinkedList<Vertex> path = dijkstra.getPath(nodes.get(destinationStation.getId()));
		
		response = new JSONObject();
		response.put("origin", originStation.getName());
		response.put("destination", destinationStation.getName());
		
		JSONArray route = new JSONArray();
		
		for (Vertex vertex : path) {
			route.put(vertex);
		}
		
		response.put("directions", route);
		
		return response;
	}

	@GET
	@Path("/time")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject calculateTime(@QueryParam("from") String origin, @QueryParam("to") String destination) throws JSONException {
		
		nodes = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();

		StationDAO stationDAO = new StationDAO();

		Station originStation = stationDAO.findByName(origin);
		Station destinationStation = stationDAO.findByName(destination);

		// Make index and ID correspondent
		nodes.add(new Vertex("0", "0"));

		for (Station station : stationDAO.getAll()) {
			Vertex vertex = new Vertex(Integer.toString(station.getId()), station.getName());
			nodes.add(vertex);
		}

		for (Line line : new LineDAO().getAll()) {
			addLane("Edge", line.getStation1(), line.getStation2(), 1);
			addLane("Edge", line.getStation2(), line.getStation1(), 1);
		}
		
		// Execute Dijkstra algorithm
		Graph graph = new Graph(nodes, edges);
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		dijkstra.execute(nodes.get(originStation.getId()));
		LinkedList<Vertex> path = dijkstra.getPath(nodes.get(destinationStation.getId()));
		
		response = new JSONObject();
		response.put("origin", originStation.getName());
		response.put("destination", destinationStation.getName());
		
		JSONArray route = new JSONArray();
		
		int time = path.size()*3;
		int connections = 0;
		
		for (int i = 0; i < path.size(); i++) {
			route.put(path.get(i));
			
			// Check if path is a connection
			if (i >= 1 && i < path.size()-1) {
				if (checkConnection(path.get(i-1), path.get(i+1))) {
					connections++;
				}
			}
		}
		
		time += connections*12;
		
		response.put("time", time);
		response.put("directions", route);

		return response;
	}

	private void addLane(String laneId, int sourceLocNo, int destLocNo, int duration) {
		Edge lane = new Edge(laneId, nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
		edges.add(lane);
	}
	
	private Boolean checkConnection(Vertex vertex1, Vertex vertex2) {
		List<Integer> vertex1Lines = new StationDAO().findLines(Integer.parseInt(vertex1.getId()));
		List<Integer> vertex2Lines = new StationDAO().findLines(Integer.parseInt(vertex2.getId()));
		
		if (vertex1Lines.size() > vertex2Lines.size()) {
			for (Integer line : vertex1Lines) {
				if (vertex2Lines.contains(line)) {
					return false;
				}
			}
		} else {
			for (Integer line : vertex2Lines) {
				if (vertex1Lines.contains(line)) {
					return false;
				}
			}
		}
		
		return true;
	}

}