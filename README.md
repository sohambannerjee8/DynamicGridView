Dependency

Volley.jar
Picasso.jar

How to use it?

Change the url in the following lines

 volleyJsonRequest.makeJsonObjReqPost("http://192.168.1.2/api/getalldata?limit=20&offset=0");
 new AsyncListLoader("http://192.168.1.2/api/getalldata?limit=40&offset=0",MainActivity.this)
                                    .execute();

Change your pojo class

ActivityScreenModel.java 

Change your adapter class that is CustomGrid.java


