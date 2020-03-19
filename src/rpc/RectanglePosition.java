/**
 * Java Servlet for handling HTTP request and response
 * @author  Jiawei Zhu
 * @version 1.0
 * @since   2020-03-18
 */
package rpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import nuvalence.Rectangle;
import nuvalence.Point;

/**
 * Servlet implementation class RectanglePosition
 */
@WebServlet("/rectangle_position")
public class RectanglePosition extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RectanglePosition() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * This doPOST method handles the client side POST request for determining the position
	 * relation of two Rectangle entities.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		JSONObject input = RpcHelper.readJSONObject(request);
		
		try {
			//parse Rectangle entities
			Rectangle rec1 = RpcHelper.parseRectangle(input.getJSONObject("rectangle_1"));
			Rectangle rec2 = RpcHelper.parseRectangle(input.getJSONObject("rectangle_2"));
			List<Rectangle> recs = new ArrayList<>();
			recs.add(rec1);
			recs.add(rec2);
			
			String positionRelaton = rec1.comparePosition(rec2);
			
			JSONArray array = new JSONArray();
			
			//determine the position relation
			for (Rectangle rec : recs) {
				JSONObject obj = rec.toJSONObject();
				obj.put("position_relation", positionRelaton);
				
				if (positionRelaton.equals("Intersection")) {
					List<Point> intersectPoints = rec.getIntersectPoints();
					for (int i = 0; i < intersectPoints.size(); i++) {
						int num = i + 1;
						obj.put("intersection_point_" + num, intersectPoints.get(i).getX() + ", " + intersectPoints.get(i).getY());
					}
					
					obj.put("intersecting_lines", rec.getNumIntersectLines());
				}

				array.put(obj);
			}
			
			//write to response
			RpcHelper.writeJsonArray(response, array);
	
		} catch(JSONException e) {
			e.printStackTrace();
		}
	}
}
