/**
 * JavaScript file handles DOM operations and AJAX calls
 * @author  Jiawei Zhu
 * @version 1.0
 * @since   2020-03-18
 */
(function() {
  /**
   * Initialize major event handlers
   */
  function init() {
    // register event listeners
    document.querySelector('#position-btn').addEventListener('click', position);
    document.querySelector('#reset-btn').addEventListener('click', reset);
  }
  
  // -----------------------------------
  // Position event handler
  // -----------------------------------
  function reset() {
	  // clear current results
	  clearPositionResultMessage();
	  var resultList = document.querySelector('#result-list');
      resultList.innerHTML = '';
      var recCoord1 = document.querySelector('#rectangle-1');
      recCoord1.innerHTML = '';
      var recCoord2 = document.querySelector('#rectangle-2');
      recCoord2.innerHTML = '';
  }

  function position() {
	clearPositionResultMessage();
	
    var recCoord1 = document.querySelector('#rectangle-1').value;
    var recCoord2 = document.querySelector('#rectangle-2').value;
    
    if (recCoord1 === "" || recCoord2 === "") {
    	showPositionResultMessage('Please fill in all fields');
    	return
    }
    
    if (recCoord1.match(/^[0-9,]+$/) === null || recCoord2.match(/^[0-9,]+$/) === null) {
    	showPositionResultMessage('Invalid coordinates');
    	return
    }
    
    var recCoordlst1 = recCoord1.split(',')
    var recCoordlst2 = recCoord2.split(',')
    
    if (recCoordlst1.length != 4 || recCoordlst2.length != 4) {
    	showPositionResultMessage('Invalid number of coordinates');
    	return
    }
    
    if (recCoordlst1[0] >= recCoordlst1[2] || recCoordlst1[1] >= recCoordlst1[3]
    || recCoordlst2[0] >= recCoordlst2[2] || recCoordlst2[1] >= recCoordlst2[3]) {
    	showPositionResultMessage('Invalid coordinates');
    	return
    }
    	
    // The request parameters
    var url = './rectangle_position';
    var req = JSON.stringify({
      rectangle_1 : {
    	  id : 1,
    	  left_bottom_x : recCoordlst1[0],
    	  left_bottom_y : recCoordlst1[1],
    	  right_top_x : recCoordlst1[2],
    	  right_top_y : recCoordlst1[3]	
      },
      rectangle_2 : {
    	  id : 2,
    	  left_bottom_x : recCoordlst2[0],
    	  left_bottom_y : recCoordlst2[1],
    	  right_top_x : recCoordlst2[2],
    	  right_top_y : recCoordlst2[3]	
      }
    });

    //AJAX call
    ajax('POST', url, req,
      // successful callback
      function(res) {
        var recs = JSON.parse(res);      
      
	    if (!recs || recs.length === 0) {
	        showPositionResultMessage('Cannot determine the position relation of the given rectangles');
	    } else {
	        var resultList = document.querySelector('#result-list');
	        resultList.innerHTML = '';
	        for (var i = 0; i < recs.length; i++) {
	        	addResult(resultList, recs[i]);
	        }
	    }
      },
      // failure callback
      function() {
    	  var resultList = document.querySelector('#result-list');
	      resultList.innerHTML = '';
    	  showPositionResult('Failed to determine the position relation of the given rectangles');
      }
    );
  }

  function showPositionResultMessage(message) {
    document.querySelector('#input-validation').innerHTML = message;
  }

  function clearPositionResultMessage() {
    document.querySelector('#input-validation').innerHTML = '';
  }

  // -----------------------------------
  // Helper Functions
  // -----------------------------------
  /**
   * A helper function that creates a DOM element <tag options...>
   * @param tag
   * @param options
   * @returns Element
   */
  function $create(tag, options) {
    var element = document.createElement(tag);
    for (var key in options) {
      if (options.hasOwnProperty(key)) {
        element[key] = options[key];
      }
    }
    return element;
  }

  /**
   * AJAX helper
   *
   * @param method - GET|POST|PUT|DELETE
   * @param url - API end point
   * @param data - request body data
   * @param successCallback - Successful callback function
   * @param errorCallback - Error callback function
   */
  function ajax(method, url, data, successCallback, errorCallback) {
    var xhr = new XMLHttpRequest();

    xhr.open(method, url, true);

    xhr.onload = function() {
      if (xhr.status === 200) {
        successCallback(xhr.responseText);
      } else {
        errorCallback();
      }
    };

    xhr.onerror = function() {
      console.error("The request couldn't be completed");
      errorCallback();
    };

    if (data === null) {
      xhr.send();
    } else {
      xhr.setRequestHeader("Content-Type",
        "application/json;charset=utf-8");
      xhr.send(data);
    }
  }

  // -------------------------------------
  // Create list of position results
  // -------------------------------------

  function addResult(resultList, rec) {
    var rec_id = rec.id;

    var li = $create('li', {
      id: 'rectangle-' + rec_id,
      className: 'rectangle'
    });

    li.dataset.rec_id = rec_id;
    li.dataset.position_relation = rec.position_relation;
    li.dataset.intersecting_lines = rec.intersecting_lines;
    li.dataset.intersection_point_1 = rec.intersection_point_1;
    li.dataset.intersection_point_2 = rec.intersection_point_2;
    li.dataset.intersection_point_3 = rec.intersection_point_3;
    li.dataset.intersection_point_4 = rec.intersection_point_4;

    // section
    var section = $create('div');

    // title
    var title = $create('p', {
      className: 'rec-id'
    });
    title.innerHTML = "Rectangle " + rec_id;
    
    // position result
    var positionRelation = $create('p', {
    	className: 'position-relation'
    });
    positionRelation.innerHTML = "Postion Relation: " + rec.position_relation;
    
    section.appendChild(title);
    section.appendChild(positionRelation);
    
    // intersecting lines and intersection points
    if (rec.position_relation === "Intersection") {
    	var intersectingLines = $create('p', {
        	className: 'intersecting-lines'
        });
        intersectingLines.innerHTML = "Number of Intersecting Lines: " + rec.intersecting_lines;
      
    	var intersectionPoint = $create('p', {
        	className: 'intersection-point'
        });
    	intersectionPoint.innerHTML = "Intersection Points: ";
    	
        var intersectionPoint1 = $create('p', {
        	className: 'intersection-point-1'
        });
        intersectionPoint1.innerHTML = "  " + "(" + rec.intersection_point_1 + ")";
        
        var intersectionPoint2 = $create('p', {
        	className: 'intersection-point-2'
        });
        intersectionPoint2.innerHTML = "  " + "(" + rec.intersection_point_2 + ")";
        
        var intersectionPoint3 = $create('p', {
        	className: 'intersection-point-3'
        });
        intersectionPoint3.innerHTML = "  " + "(" + rec.intersection_point_3 + ")";
        
        var intersectionPoint4 = $create('p', {
        	className: 'intersection-point-4'
        });
        intersectionPoint4.innerHTML = "  " + "(" + rec.intersection_point_4 + ")";
        
        section.appendChild(intersectingLines);
        section.appendChild(intersectionPoint);
        section.appendChild(intersectionPoint1);
        section.appendChild(intersectionPoint2);
        section.appendChild(intersectionPoint3);
        section.appendChild(intersectionPoint4);
    }    

    li.appendChild(section);

    resultList.appendChild(li);
  }

  init();

})();