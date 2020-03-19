/**
 * Helper class for Java Servlet
 * @author  Jiawei Zhu
 * @version 1.0
 * @since   2020-03-18
 */
package rpc;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import nuvalence.Point;
import nuvalence.Rectangle;

public class RpcHelper {
	//writes a JSONArray to HTTP response
	public static void writeJsonArray(HttpServletResponse response, JSONArray array) throws IOException{
		response.setContentType("application/json");
		response.getWriter().print(array);
	}
	
	//read JSON Object from HTTP request body
	public static JSONObject readJSONObject(HttpServletRequest request) {
		StringBuilder sBuilder = new StringBuilder();
		try(BufferedReader reader = request.getReader()){
			String line = null;
			while((line = reader.readLine()) != null) {
				sBuilder.append(line);
			}
			return new JSONObject(sBuilder.toString());
		} catch(Exception e) {	
			e.printStackTrace();
		}
		
		return new JSONObject();
	}
	
	
	/**
	 * This method is used to parse Rectangle entities from JSON format.
	 * @param input This is the JSON format of a rectangle.
	 * @return Rectangle rec This is the Rectangle entity parsed from JSON format.
	 */
    public static Rectangle parseRectangle(JSONObject inputRectangle) throws JSONException {
    	Point leftBotPoint = new Point(Integer.parseInt(inputRectangle.getString("left_bottom_x")), Integer.parseInt(inputRectangle.getString("left_bottom_y")));
		Point rightTopPoint = new Point(Integer.parseInt(inputRectangle.getString("right_top_x")), Integer.parseInt(inputRectangle.getString("right_top_y")));
		
		//use chaining to build Rectangle entity
		Rectangle rec = new Rectangle.RectangleBuilder()
				.id(inputRectangle.getInt("id"))
				.leftBotPoint(leftBotPoint)
				.rightTopPoint(rightTopPoint)
				.build();
		
		return rec;
	}
}