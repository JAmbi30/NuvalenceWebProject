Project name: Rectangle Position Relation Web Development
Author: Jiawei Zhu
Date: 03/18/2020

Description:
This project renders the position relation of two rectangles in web page. 
There are three position relations: Intersection, Containment, and Adjacency.
When the result is Intersection, the number of intersecting lines and
intersection points of the rectangle are also returned. Each rectangle is 
represented by its left bottom point and right top point. Tomcat framework 
is used for web server. HTML/CSS/JavaScript/AJAX are used for front end. Back
end is implemented using Java. The web service is deployed on AWS EC2.

Github repository link for Rectangle exercise with output files:
https://github.com/JAmbi30/NuvalenceProject

-----------------------------------------------------------------------------------
Test:
Unit tests are conducted via JUnit. Integration test for API is conducted 
using Postman.

Libraries:
org.junit, org.json, Tomcat Framework, java.io, java.util

Folder Structure:
src/nuvalence/Point.java: Point entity implementation class.
src/nuvalence/Rectangle.java: Rectangle entity implementation class.
src/nuvalence/RectangleTest.java: JUnit test file.
src/rpc/RectanglePosition.java: Java servlet class for handling HTTP POST Request.
src/rpc/RpcHelper.java: helper class for Java servlet.
WebContent/scripts/main.js: JavaScript file that handles DOM operations and AJAX calls.
WebContent/index.html: main html page file.

Assumptions:
 1. Each rectangle is either vertial or horizontal. Tilted rectangle is not 
 taken into consideration for this project.
 2. Touching rectangles at a single point are not considered as any of the 
 three position relations.
 3. Input coordinate values are non-negative integers. This value is in the range 
 from 0 to 2147483647. 

-----------------------------------------------------------------------------
Instruction:
	To check the user interface, please go to the website:
		http://3.92.179.20:8080/NuvalenceProject/
