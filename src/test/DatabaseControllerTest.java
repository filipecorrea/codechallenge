package test;

import static org.junit.Assert.assertEquals;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import controller.DatabaseController;

public class DatabaseControllerTest {

	@Test
	public void testDatabaseUpdate() throws JSONException {
		JSONObject response = new DatabaseController().databaseUpdate();
		
		assertEquals("databaseupdate", true, response.get("success"));
	}

}
