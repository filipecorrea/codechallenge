package test;

import static org.junit.Assert.assertEquals;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import controller.RouteController;

public class RouteControllerTest {

	@Test
	public void testShortestRoute() throws JSONException {
		JSONObject response = new RouteController().getShortestRoute("Alperton", "London Bridge");
		
		assertEquals("origin", "Alperton", response.get("origin"));
		assertEquals("destination", "London Bridge", response.get("destination"));
		assertEquals("steps", 18, response.getJSONArray("directions").length());
	}
	
	@Test
	public void testCalculateTime() throws JSONException {
		JSONObject response = new RouteController().calculateTime("Queensway", "Holborn");
		
		assertEquals("origin", "Queensway", response.get("origin"));
		assertEquals("destination", "Holborn", response.get("destination"));
		assertEquals("time", 21, response.getInt("time"));
		assertEquals("steps", 7, response.getJSONArray("directions").length());
	}
}
