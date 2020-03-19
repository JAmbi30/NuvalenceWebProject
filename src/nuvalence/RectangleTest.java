/**
 * Unit test cases for methods in Rectangle Class
 * @author  Jiawei Zhu
 * @version 1.0
 * @since   2020-03-18
 */
package nuvalence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class RectangleTest {	
	@Test
	public void testRectangleClassComparePositionMethodContainmentCase() {
		Point leftBotPoint1 = new Point(0, 0);
		Point rightTopPoint1 = new Point(5, 5);
		Point leftBotPoint2 = new Point(1, 1);
		Point rightTopPoint2 = new Point(2, 2); 
		
		Rectangle rec1 = new Rectangle.RectangleBuilder()
				.id(1)
				.leftBotPoint(leftBotPoint1)
				.rightTopPoint(rightTopPoint1)
				.build();
		
		Rectangle rec2 = new Rectangle.RectangleBuilder()
				.id(2)
				.leftBotPoint(leftBotPoint2)
				.rightTopPoint(rightTopPoint2)
				.build();
		
		assertEquals("Containment", rec1.comparePosition(rec2));
	}
	
	@Test
	public void testRectangleClassComparePositionMethodIntersectionCase() {
		Point leftBotPoint1 = new Point(1, 1);
		Point rightTopPoint1 = new Point(6, 7);
		Point leftBotPoint2 = new Point(2, 0);
		Point rightTopPoint2 = new Point(4, 5); 
		
		Rectangle rec1 = new Rectangle.RectangleBuilder()
				.id(1)
				.leftBotPoint(leftBotPoint1)
				.rightTopPoint(rightTopPoint1)
				.build();
		
		Rectangle rec2 = new Rectangle.RectangleBuilder()
				.id(2)
				.leftBotPoint(leftBotPoint2)
				.rightTopPoint(rightTopPoint2)
				.build();
		
		assertEquals("Intersection", rec1.comparePosition(rec2));
	}
	
	@Test
	public void testRectangleClassComparePositionMethodSubLineAdjacencyCase() {
		Point leftBotPoint1 = new Point(1, 1);
		Point rightTopPoint1 = new Point(6, 6);
		Point leftBotPoint2 = new Point(6, 2);
		Point rightTopPoint2 = new Point(8, 4); 
		
		Rectangle rec1 = new Rectangle.RectangleBuilder()
				.id(1)
				.leftBotPoint(leftBotPoint1)
				.rightTopPoint(rightTopPoint1)
				.build();
		
		Rectangle rec2 = new Rectangle.RectangleBuilder()
				.id(2)
				.leftBotPoint(leftBotPoint2)
				.rightTopPoint(rightTopPoint2)
				.build();
		
		assertEquals("Adjacent (Sub-line)", rec1.comparePosition(rec2));
	}
	
	@Test
	public void testRectangleClassComparePositionMethodProperAdjacencyCase() {
		Point leftBotPoint1 = new Point(1, 1);
		Point rightTopPoint1 = new Point(6, 6);
		Point leftBotPoint2 = new Point(6, 1);
		Point rightTopPoint2 = new Point(8, 6); 
		
		Rectangle rec1 = new Rectangle.RectangleBuilder()
				.id(1)
				.leftBotPoint(leftBotPoint1)
				.rightTopPoint(rightTopPoint1)
				.build();
		
		Rectangle rec2 = new Rectangle.RectangleBuilder()
				.id(2)
				.leftBotPoint(leftBotPoint2)
				.rightTopPoint(rightTopPoint2)
				.build();
		
		assertEquals("Adjacent (Proper)", rec1.comparePosition(rec2));
	}
	
	@Test
	public void testRectangleClassComparePositionMethodPartialAdjacencyCase() {
		Point leftBotPoint1 = new Point(1, 1);
		Point rightTopPoint1 = new Point(6, 6);
		Point leftBotPoint2 = new Point(6, 2);
		Point rightTopPoint2 = new Point(8, 8); 
		
		Rectangle rec1 = new Rectangle.RectangleBuilder()
				.id(1)
				.leftBotPoint(leftBotPoint1)
				.rightTopPoint(rightTopPoint1)
				.build();
		
		Rectangle rec2 = new Rectangle.RectangleBuilder()
				.id(2)
				.leftBotPoint(leftBotPoint2)
				.rightTopPoint(rightTopPoint2)
				.build();
		
		assertEquals("Adjacent (Partial)", rec1.comparePosition(rec2));
	}
	
	@Test
	public void testRectangleClassComparePositionMethodNullCase() {
		Point leftBotPoint1 = new Point(0, 0);
		Point rightTopPoint1 = new Point(5, 5);
		
		Rectangle rec1 = new Rectangle.RectangleBuilder()
				.id(1)
				.leftBotPoint(leftBotPoint1)
				.rightTopPoint(rightTopPoint1)
				.build();
		
		Rectangle rec2 = null;
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			rec1.comparePosition(rec2);
		});
		
		String expectedMessage = "Input Rectangle is invalid";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void testRectangleClassComparePositionMethodNegativeCase() {
		Point leftBotPoint1 = new Point(0, 0);
		Point rightTopPoint1 = new Point(5, 5);
		Point leftBotPoint2 = new Point(20, 20);
		Point rightTopPoint2 = new Point(40, 50); 
		
		Rectangle rec1 = new Rectangle.RectangleBuilder()
				.id(1)
				.leftBotPoint(leftBotPoint1)
				.rightTopPoint(rightTopPoint1)
				.build();
		
		Rectangle rec2 = new Rectangle.RectangleBuilder()
				.id(2)
				.leftBotPoint(leftBotPoint2)
				.rightTopPoint(rightTopPoint2)
				.build();
		
		assertEquals("No Containment/Intersection/Adjacency", rec1.comparePosition(rec2));
	}
	
	@Test
	public void testRectangleClassComparePositionMethodIntersectionPointsCase() {
		//4 intersection points
		Point leftBotPoint1 = new Point(0, 0);
		Point rightTopPoint1 = new Point(5, 6);
		Point leftBotPoint2 = new Point(1, 2);
		Point rightTopPoint2 = new Point(3, 8); 
		
		Rectangle rec1 = new Rectangle.RectangleBuilder()
				.id(1)
				.leftBotPoint(leftBotPoint1)
				.rightTopPoint(rightTopPoint1)
				.build();
		
		Rectangle rec2 = new Rectangle.RectangleBuilder()
				.id(2)
				.leftBotPoint(leftBotPoint2)
				.rightTopPoint(rightTopPoint2)
				.build();
		
		Point point1 = new Point(1, 6);
		Point point2 = new Point(3, 6);
		Point point3 = new Point(3, 2);
		Point point4 = new Point(1, 2);
		List<Point> expectedIntersectPoints = new ArrayList<>();
		
		expectedIntersectPoints.add(point1);
		expectedIntersectPoints.add(point2);
		expectedIntersectPoints.add(point3);
		expectedIntersectPoints.add(point4);
		
		rec1.comparePosition(rec2);
		
		assertEquals(expectedIntersectPoints, rec1.getIntersectPoints());
	}
	
	@Test
	public void testRectangleClassComparePositionMethodIntersectingLinesCase1() {
		//Case1: each rectangle has 1 intersecting line
		Point leftBotPoint1 = new Point(0, 0);
		Point rightTopPoint1 = new Point(2, 4);
		Point leftBotPoint2 = new Point(0, 0);
		Point rightTopPoint2 = new Point(5, 2); 
		
		Rectangle rec1 = new Rectangle.RectangleBuilder()
				.id(1)
				.leftBotPoint(leftBotPoint1)
				.rightTopPoint(rightTopPoint1)
				.build();
		
		Rectangle rec2 = new Rectangle.RectangleBuilder()
				.id(2)
				.leftBotPoint(leftBotPoint2)
				.rightTopPoint(rightTopPoint2)
				.build();
		
		rec1.comparePosition(rec2);
		
		int[] expectedIntersectingLines = {1, 1};
		int[] actualIntersectingLines = {rec1.getNumIntersectLines(), rec2.getNumIntersectLines()};
		
		assertArrayEquals(expectedIntersectingLines, actualIntersectingLines);
	}
	
	@Test
	public void testRectangleClassComparePositionMethodIntersectingLinesCase2() {
		//Case2: one rectangle has 1 intersecting line; the other has 2 intersecting lines
		Point leftBotPoint1 = new Point(0, 0);
		Point rightTopPoint1 = new Point(3, 3);
		Point leftBotPoint2 = new Point(2, 0);
		Point rightTopPoint2 = new Point(5, 8); 
		
		Rectangle rec1 = new Rectangle.RectangleBuilder()
				.id(1)
				.leftBotPoint(leftBotPoint1)
				.rightTopPoint(rightTopPoint1)
				.build();
		
		Rectangle rec2 = new Rectangle.RectangleBuilder()
				.id(2)
				.leftBotPoint(leftBotPoint2)
				.rightTopPoint(rightTopPoint2)
				.build();
		
		rec1.comparePosition(rec2);
		
		int[] expectedIntersectingLines = {1, 2};
		int[] actualIntersectingLines = {rec1.getNumIntersectLines(), rec2.getNumIntersectLines()};
		
		assertArrayEquals(expectedIntersectingLines, actualIntersectingLines);
	}

	@Test
	public void testRectangleClassComparePositionMethodIntersectingLinesCase3() {
		//Case3: one rectangle has 1 intersecting line; the other has 3 intersecting lines
		Point leftBotPoint1 = new Point(3, 3);
		Point rightTopPoint1 = new Point(5, 5);
		Point leftBotPoint2 = new Point(4, 2);
		Point rightTopPoint2 = new Point(8, 8); 
		
		Rectangle rec1 = new Rectangle.RectangleBuilder()
				.id(1)
				.leftBotPoint(leftBotPoint1)
				.rightTopPoint(rightTopPoint1)
				.build();
		
		Rectangle rec2 = new Rectangle.RectangleBuilder()
				.id(2)
				.leftBotPoint(leftBotPoint2)
				.rightTopPoint(rightTopPoint2)
				.build();
		
		rec1.comparePosition(rec2);
		
		int[] expectedIntersectingLines = {1, 3};
		int[] actualIntersectingLines = {rec1.getNumIntersectLines(), rec2.getNumIntersectLines()};
		
		assertArrayEquals(expectedIntersectingLines, actualIntersectingLines);
	}
	
	@Test
	public void testRectangleClassComparePositionMethodIntersectingLinesCase4() {
		//Case4: both rectangles have 2 intersecting lines
		Point leftBotPoint1 = new Point(3, 3);
		Point rightTopPoint1 = new Point(8, 8);
		Point leftBotPoint2 = new Point(5, 1);
		Point rightTopPoint2 = new Point(10, 6); 
		
		Rectangle rec1 = new Rectangle.RectangleBuilder()
				.id(1)
				.leftBotPoint(leftBotPoint1)
				.rightTopPoint(rightTopPoint1)
				.build();
		
		Rectangle rec2 = new Rectangle.RectangleBuilder()
				.id(2)
				.leftBotPoint(leftBotPoint2)
				.rightTopPoint(rightTopPoint2)
				.build();
		
		rec1.comparePosition(rec2);
		
		int[] expectedIntersectingLines = {2, 2};
		int[] actualIntersectingLines = {rec1.getNumIntersectLines(), rec2.getNumIntersectLines()};
		
		assertArrayEquals(expectedIntersectingLines, actualIntersectingLines);
	}	
}
