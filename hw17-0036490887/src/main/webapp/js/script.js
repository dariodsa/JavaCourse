var imageShown = false;
var picturePath = "";
var currentTag = "";

$(document).ready( 
  traziTagove()
);

$(document).keyup(function(e) {
  if (e.keyCode == 27) {
	  $("#tags").show();
	  $("#picture").hide();
          if(currentTag != "") {
            window.location.hash = currentTag+"a";
          }
	}
});

function showHelp() {

	
}

var numOfTags = 0;		  

function clearOthers() {
  for(let i=0;i<numOfTags;++i) {
	  $("#" + i).html("");
  }
}

function clearAll() {
  
  $("#tags").show();
  $("#picture").hide();
  if(currentTag != "") {
     window.location.hash = currentTag+"a";
  }
 
}

function showPicture(path, imageName) {
  
   $.ajax(
	{
		url: "rest/picture/getTitle/" + imageName,
		data: {
				dummy: Math.random()
		},
		dataType: "json",
		success: function(data) {
			var title = data.title; // već je JSON zbog dataType gore...
			var html = "";
			if(title.length==0) {
					html = "Nema rezultata...";
			} else {
					html += "<table> <tr>";   
					html += "<td rowspan=2><img src=\"" + path + "\" width=1000 height=600></td>";
					html += "<td> <div class=\"heading\">" + title + "</div> </td>";
					html += "</tr> <td>";
					html += "<button class='tag' onClick=clearAll()>Nazad</button>";
					html += "</td></tr> </table>";
			}
			$("#tags").hide();
			$("#picture").show();
			$("#picture").html(html);
		}
	}
  );

}

function showPictures(tagName, tagId) {
  
  $.ajax(
	  {
		  url: "rest/tags/pictures/" + tagName,
		  data: {
			  dummy: Math.random()
		  },
		  dataType: "json",
		  success: function(data) {
				
				clearOthers();
                                currentTag = String(tagId);
				var pictures = data.pictures; // već je JSON zbog dataType gore...
				var html = "";
				if(pictures.length==0) {
					html = "Nema rezultata...";
				} else {
					html += "<div class=\"thumbnailsBox\">";
					for(var i=0; i<pictures.length; i++) {
						let path_small = "createsmallpicture?imageName=" + pictures[i];
						let path_big = "getbigpicture?imageName=" + pictures[i];
						html += "<div class=\"thumbnails\">";
						html += "<img src=" + path_small + " onClick=showPicture('" + path_big + "','" + pictures[i] + "')></div>";
					}
					html += "</div>";
				}
				$("#" + tagId).html(html);
		  }
	  }
  );
}

function traziTagove() {
$.ajax(
  {
	  url: "rest/tags",
	  data: {
		  dummy: Math.random()
	  },
	  dataType: "json",
	  success: function(data) {
			var tags = data; // već je JSON zbog dataType gore...
			var html = "";
			if(tags.length==0) {
				html = "Nema rezultata...";
			} else {
				for(let i=0; i<tags.length; i++) {
					let buttonHtml = "<button class='tag' onClick=\"showPictures('"+ tags[i].text + "'," + i + ")\">";
					html += "<div id=\"" + i + "a\">";
                                        html += buttonHtml + tags[i].text + "</button>";
					html += "<div id="+ i +"></div></div>";
					html += "<br>";
				}
											numOfTags = tags.length;
			}
			$("#tags").html(html);
	  }
  }
);
}
