/**
 * The Rectangle class is the implementation of a rectangle entity.
 * @author  Jiawei Zhu
 * @version 1.0
 * @since   2020-03-18
 */
package nuvalence;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Rectangle {
	//rectangle id
	private final int id;
	//coordinate of left bottom point of the rectangle
	private final Point leftBotPoint;
	//coordinate of right top point of the rectangle
    private final Point rightTopPoint;
    //number of intersection lines across the rectangle
    private int numIntersectLines;
    //list of intersection points
    private List<Point> intersectPoints;

    private Rectangle(RectangleBuilder builder) {
    	this.id = builder.id;
    	this.leftBotPoint = builder.leftBotPoint;
        this.rightTopPoint = builder.rightTopPoint;
        this.intersectPoints = new ArrayList<>();
    }
     
    //getters
    public Point getLeftBotPoint() {
    	return leftBotPoint;
    }
 
    public Point getRightTopPoint() {
    	return rightTopPoint;
    }
    
    public int getId() {
    	return id;
    }
    
    public int getNumIntersectLines() {
    	return numIntersectLines;
    }
    
    public List<Point> getIntersectPoints() {
    	return intersectPoints;
    }
    
    //setter
    public void setNumIntersectLines(int numIntersectLines) {
    	this.numIntersectLines = numIntersectLines;
    }
    
    
    /**
     * This method adds the intersection point to the list of intersection points.
     * @param point This is the intersection point.
     * @return Nothing
     */
    public void addIntersectPoint(Point point) {
    	intersectPoints.add(point);
    }
    
    
    /**
     * This method compares the position relation of two Rectangle entities.
     * @param rec This is the Rectangle entity to be compared with.
     * @return String result This is the position relation.
     */
    public String comparePosition(Rectangle rec) {
    	String result = "";
    	if (rec == null) {
    		throw new IllegalArgumentException("Input Rectangle is invalid");
    	}
 
		 if (isContainment(rec)) {
		     result = "Containment";
		 } else if (isIntersection(rec)) {
		     result = "Intersection";
		     getIntersectPoints(rec);
		     calcNumIntersectLines(rec);
		 } else if (isAdjacency(rec)) {
		     result = getAdjacencyType(rec);
		 } else {
		     result = "No Containment/Intersection/Adjacency";
		 }
		     
		 return result;
	 }
 
    
    /**
     * This method checks if one Rectangle entity contains another Rectangle entity.
     * @param rec This is the Rectangle entity to be compared with.
     * @return boolean This indicates if one Rectangle entity contains another or not.
     * true means containment, and false means no containment.
     */
	private boolean isContainment(Rectangle rec) {
		//containment include same rectangle
		if (rec == null || rec.getLeftBotPoint() == null 
				|| rec.getRightTopPoint() == null || this.leftBotPoint == null 
				|| this.rightTopPoint == null) {
			return false;
		}
		
		//if this Rectangle contains rec Rectangle entity
	    if (this.leftBotPoint.getX() <= rec.getLeftBotPoint().getX() 
	    		 && this.leftBotPoint.getY() <= rec.getLeftBotPoint().getY() 
	    			  && this.rightTopPoint.getX() >= rec.getRightTopPoint().getX() 
	    					  && this.rightTopPoint.getY() >= rec.getRightTopPoint().getY()) {
	    	return true;
	    //if rec Rectangle entity contains this Rectangle entity
	    } else if (rec.getLeftBotPoint().getX() <= this.leftBotPoint.getX() 
	    		 && rec.getLeftBotPoint().getY() <= this.leftBotPoint.getY() 
	    				 && rec.getRightTopPoint().getX() >= this.rightTopPoint.getX() 
	    						 && rec.getRightTopPoint().getY() >= this.rightTopPoint.getY()) {
	    	return true;
	    }
	     
	    return false;
	}
 
	
	/**
	 * This method checks if two Rectangle entities intersect.
	 * @param rec This is the Rectangle entity to be compared with.
	 * @return boolean This indicates if two Rectangle entities intersect. true means 
	 * intersection, and false means no intersection.
	 */
	private boolean isIntersection(Rectangle rec) {
		//touching rectangle does not count as intersection
		
		 if (rec == null || rec.getLeftBotPoint() == null 
				 || rec.getRightTopPoint() == null || this.leftBotPoint == null 
				 || this.rightTopPoint == null) {
			 return false;
		 }
		
		if (this.leftBotPoint.getX() < rec.getRightTopPoint().getX() 
				&& this.leftBotPoint.getY() < rec.getRightTopPoint().getY() 
						&& this.rightTopPoint.getX() > rec.getLeftBotPoint().getX() 
								&& this.rightTopPoint.getY() > rec.getLeftBotPoint().getY()) {
	        return true;
	    }
	     
	    return false;
	}
	
	
	/**
	 * This method computes the coordinates of intersection points of two rectangle.
	 * @param rec This is another Rectangle entity.
	 * @return Nothing
	 */
	private void getIntersectPoints(Rectangle rec) {
		if (rec == null || rec.getLeftBotPoint() == null 
				|| rec.getRightTopPoint() == null
				|| rec.getIntersectPoints() == null) {
			return;
		}
		
		int largeLeftBotX = Math.max(this.leftBotPoint.getX(), rec.getLeftBotPoint().getX());
		int largeLeftBotY = Math.max(this.leftBotPoint.getY(), rec.getLeftBotPoint().getY());
		int smallRightTopX = Math.min(this.rightTopPoint.getX(), rec.getRightTopPoint().getX());
		int smallRightTopY = Math.min(this.rightTopPoint.getY(), rec.getRightTopPoint().getY());	
		
		//intersection points of two rectangles
		Point point1 = new Point(largeLeftBotX, smallRightTopY);
		Point point2 = new Point(smallRightTopX, smallRightTopY);
		Point point3 = new Point(smallRightTopX, largeLeftBotY);
		Point point4 = new Point(largeLeftBotX, largeLeftBotY);
		
		this.addIntersectPoint(point1);
		this.addIntersectPoint(point2);
		this.addIntersectPoint(point3);
		this.addIntersectPoint(point4);
		rec.addIntersectPoint(point1);
		rec.addIntersectPoint(point2);		
		rec.addIntersectPoint(point3);
		rec.addIntersectPoint(point4);
	}
	
	
	/**
	 * This method calculates the number of lines cross the intersected rectangles
	 * @param rec This is the Rectangle entity to be intersected.
	 * @return Nothing
	 */
	private void calcNumIntersectLines(Rectangle rec) {
		if (rec == null || rec.getLeftBotPoint() == null 
				|| rec.getRightTopPoint() == null
				|| this.intersectPoints == null
				|| rec.getIntersectPoints() == null
				|| this.intersectPoints.size() == 0
				|| rec.getIntersectPoints().size() == 0) {
			return;
		}
		
		int leftBotX1 = this.leftBotPoint.getX();
		int leftBotY1 = this.leftBotPoint.getY();
		int rightTopX1 = this.rightTopPoint.getX();
		int rightTopY1 = this.rightTopPoint.getY();
		
		int leftBotX2 = rec.getLeftBotPoint().getX();
		int leftBotY2 = rec.getLeftBotPoint().getY();
		int rightTopX2 = rec.getRightTopPoint().getX();
		int rightTopY2 = rec.getRightTopPoint().getY();
			
		int countSameX = 0;
		int countSameY = 0;
		
		if (leftBotX1 == leftBotX2) {
			countSameX++;
		}
		
		if (leftBotX1 == rightTopX2) {
			countSameX++;
		}
		
		if (rightTopX1 == leftBotX2) {
			countSameX++;
		}
		
		if (rightTopX1 == rightTopX2) {
			countSameX++;
		}
		
		if (leftBotY1 == leftBotY2) {
			countSameY++;
		}
		
		if (leftBotY1 == rightTopY2) {
			countSameY++;
		}
		
		if (rightTopY1 == leftBotY2) {
			countSameY++;
		}
		
		if (rightTopY1 == rightTopY2) {
			countSameY++;
		}
		
		//both rectangles have 1 intersecting line
		if (countSameX == 1 && countSameY == 1 || countSameX == 2 || countSameY == 2) {			
			this.setNumIntersectLines(1);
			rec.setNumIntersectLines(1);			
		//one rectangle has 1 intersecting line, and one rectangle has 2 intersecting lines
		} else if (countSameX == 1 || countSameY == 1) {
			Point leftBotPoint = rec.getLeftBotPoint();
			Point rightTopPoint = rec.getRightTopPoint();
			Point leftTopPoint = new Point(leftBotX2, rightTopY2);
			Point rightBotPoint = new Point(rightTopX2, leftBotY2);
			
			if (containPoint(leftBotPoint) || containPoint(rightTopPoint)
					|| containPoint(leftTopPoint) || containPoint(rightBotPoint)) {
				this.setNumIntersectLines(2);
				rec.setNumIntersectLines(1);
			} else if (countSameX == 1 && rightTopY1 > rightTopY2 
					|| countSameY == 1 && rightTopX1 > rightTopX2) {
				this.setNumIntersectLines(2);
				rec.setNumIntersectLines(1);
			} else {
				this.setNumIntersectLines(1);
				rec.setNumIntersectLines(2);
			}
		} else {
			//one rectangle has 1 intersecting line, and one rectangle has 3 intersecting lines
			if (leftBotX1 < leftBotX2 && leftBotY1 > leftBotY2
					&& rightTopX1 > leftBotX2 && rightTopX1 < rightTopX2 && rightTopY1 < rightTopY2
					|| leftBotX1 > leftBotX2 && leftBotX1 < rightTopX2 && leftBotY1 > leftBotY2 && leftBotY1 < rightTopY2
					&& rightTopX1 < rightTopX2 && rightTopY1 > rightTopY2
					|| leftBotX1 > leftBotX2 && leftBotX1 < rightTopX2 && leftBotY1 > leftBotY2 && leftBotY1 < rightTopY2
					&& rightTopX1 > rightTopX2 && rightTopY1 < rightTopY2
					|| leftBotX1 > leftBotX2 && leftBotX1 < rightTopX2 && leftBotY1 < leftBotY2
					&& rightTopX1 < rightTopX2 && rightTopY1 > leftBotY2 && rightTopY1 < rightTopY2) {
				this.setNumIntersectLines(1);
				rec.setNumIntersectLines(3);
			} else if (leftBotX2 < leftBotX1 && leftBotY2 > leftBotY1
					&& rightTopX2 > leftBotX1 && rightTopX2 < rightTopX1 && rightTopY2 < rightTopY1
					|| leftBotX2 > leftBotX1 && leftBotX2 < rightTopX1 && leftBotY2 > leftBotY1 && leftBotY2 < rightTopY1
					&& rightTopX2 < rightTopX1 && rightTopY2 > rightTopY1
					|| leftBotX2 > leftBotX1 && leftBotX2 < rightTopX1 && leftBotY2 > leftBotY1 && leftBotY2 < rightTopY1
					&& rightTopX2 > rightTopX1 && rightTopY2 < rightTopY1
					|| leftBotX2 > leftBotX1 && leftBotX2 < rightTopX1 && leftBotY2 < leftBotY1
					&& rightTopX2 < rightTopX1 && rightTopY2 > leftBotY1 && rightTopY2 < rightTopY1) {
				this.setNumIntersectLines(3);
				rec.setNumIntersectLines(1);
			//both rectangles have 2 intersecting lines
			} else {
				this.setNumIntersectLines(2);
				rec.setNumIntersectLines(2);
			}
		}
	}
	
	
	/**
	 * This method checks if this Rectangle entity contains the input point. (this point
	 * is inside the rectangle)
	 * @param point This is the input point.
	 * @return boolean Return true if this Rectangle entity contains the point; 
	 * otherwise, return false.
	 */
	public boolean containPoint(Point point) {
		if (point == null) {
			return false;
		}
		
		if (this.leftBotPoint.getX() < point.getX() && this.rightTopPoint.getX() > point.getX()
				&& this.leftBotPoint.getY() < point.getY() && this.rightTopPoint.getY() > point.getY()) {
			return true;
		}
		
		return false;
	}
	
    
	/**
	 * This method checks if two Rectangle entities are adjacent.
	 * @param rec This is the Rectangle entity to be compared with.
	 * @return boolean This indicates if two Rectangle entities are adjacent. true means 
	 * adjacency, and false means no adjacency.
	 */
	private boolean isAdjacency(Rectangle rec) {
		if (rec == null || rec.getLeftBotPoint() == null 
				|| rec.getRightTopPoint() == null || this.leftBotPoint == null 
				|| this.rightTopPoint == null) {
			return false;
		}
		 
	    if (this.leftBotPoint.getX() == rec.getRightTopPoint().getX() || this.rightTopPoint.getX() == rec.getLeftBotPoint().getX()) {
	        return true;
	    } else if (this.leftBotPoint.getY() == rec.getRightTopPoint().getY() || this.rightTopPoint.getY() == rec.getLeftBotPoint().getY()) {
	    	return true;
	    }
	     
	    return false;
	}
	
	
	/**
	 * This method determines the adjacency type (sub-line/proper/partial) of two adjacent
	 * rectangles.
	 * @param rec This is the adjacent Rectangle entity.
	 * @return String type This is the adjacency type.
	 */
	private String getAdjacencyType(Rectangle rec) {
		String type = "";
		
		if (rec == null || rec.getLeftBotPoint() == null 
				|| rec.getRightTopPoint() == null) {
			return type;
		}
		
		int leftBotX1 = this.leftBotPoint.getX();
		int leftBotY1 = this.leftBotPoint.getY();
		int rightTopX1 = this.rightTopPoint.getX();
		int rightTopY1 = this.rightTopPoint.getY();
		
		int leftBotX2 = rec.getLeftBotPoint().getX();
		int leftBotY2 = rec.getLeftBotPoint().getY();
		int rightTopX2 = rec.getRightTopPoint().getX();
		int rightTopY2 = rec.getRightTopPoint().getY();
		
		if (leftBotX1 == leftBotX2 && rightTopX1 == rightTopX2 
				|| leftBotY1 == leftBotY2 && rightTopY1 == rightTopY2) {
			type = "Adjacent (Proper)";
		}
		else if (leftBotX1 >= leftBotX2 && rightTopX1 <= rightTopX2 
			|| leftBotX2 >= leftBotX1 && rightTopX2 <= rightTopX1
			|| leftBotY1 >= leftBotY2 && rightTopY1 <= rightTopY2
			|| leftBotY2 >= leftBotY1 && rightTopY2 <= rightTopY1) {
			type = "Adjacent (Sub-line)";
		} else {
			type = "Adjacent (Partial)";
		}		
		
		return type;
	}
 
	
	/**
	 * This method converts the rectangle entity to JSON Object
	 * @return JSONObject This is the JSON Object version of rectangle entity
	 */
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("id", id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	
	/**
	 * This nested class is used to build Rectangle instances. 
	 * The builder design is implemented here. 
	 */
	public static class RectangleBuilder {
		private int id;
	    private Point leftBotPoint;
	    private Point rightTopPoint;
	     
	    //setters
	    public RectangleBuilder id(int id) {
	    	this.id = id;
	    	return this;
	    }
	    
	    public RectangleBuilder leftBotPoint(Point leftBotPoint) {
	        this.leftBotPoint = leftBotPoint;
	        return this;
	    }
	     
	    public RectangleBuilder rightTopPoint(Point rightTopPoint) {
	        this.rightTopPoint = rightTopPoint;
	        return this;
	    }
	    
	    //build
	    public Rectangle build() {
	        if (leftBotPoint == null || rightTopPoint == null) {
	            throw new IllegalArgumentException("Required Rectangle fields are not set");
	        } else if (leftBotPoint.getX() >= rightTopPoint.getX() || leftBotPoint.getY() >= rightTopPoint.getY()) {
	        	throw new IllegalArgumentException("Left bottom coordinate of Rectangle is larger than or equal to right top coordinate");
	        } else if (id <= 0) {
	        	throw new IllegalArgumentException("Invalid Rectangle ID");
	        }
	     
	        return new Rectangle(this);
	    } 
         
	}
 }