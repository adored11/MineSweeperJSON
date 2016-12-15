<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title></title>
    <script type="text/javascript">

            var getJSON = function(url, callback) {
                var xhr = new XMLHttpRequest();
                xhr.open("get", url, true);
                xhr.responseType = "json";
                xhr.onload = function() {
                  var status = xhr.status;
                  if (status == 200) {
                    callback(null, xhr.response);
                  } else {
                    callback(status);
                  }
                };
                xhr.send();
            };


            getJSON("http://localhost:8080/WebApp1Servlet",
            function(err, data) {
              if (err != null) {
                alert("Something went wrong: " + err);
              } else {
                 /*var jsonPretty = JSON.stringify(JSON.parse(data),null,2);*/
                 /*JSON.stringify(data);*/
                 document.getElementById("jsonPretty").innerHTML = JSON.stringify(data, undefined, 2);
              }
            });
    </script>


  </head>
  <body>
    <h1>Simple Java Web App Demo</h1>
    <!--<p>To invoke the java servlet click <a href="WebApp1Servlet" title="here" id="getJSONLink">here</a></p> -->
    <!--<p>To invoke the java servlet click <a>here</a> </p> getJSON -->
    <a href="javascript:getJSON('')">My link</a>
    <pre id="jsonPretty"></pre>
  </body>
</html>