/**
 * The Point class is the implementation of a point.
 * @author  Jiawei Zhu
 * @version 1.0
 * @since   2020-03-18
 */
package nuvalence;

public class Point {
	private int x;
    private int y;

    public Point(int x, int y) {
    	this.x = x;
    	this.y = y;
    }
     
    //getters
    public int getX() {
    	return this.x;
    }
 
    public int getY() {
    	return this.y;
    }
	     
    //setters
    public void setX(int x) {
        this.x = x;
    }
     
    public void setY(int y) {
        this.y = y;
    }
    
    public boolean equals(Object obj) {
    	if (obj == this) {
    		return true;
    	}
    	
    	if (!(obj instanceof Point)) {
    		return false;
    	}
    	
    	Point point = (Point) obj;
    	return this.x == point.getX() && this.y == point.getY();
    }
}
