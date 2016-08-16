Personal Website Server
=======================

This is my personal website's back end server. The back end server consists of a mongodb database and jersey java applet server. The java jersey server consists a series of a standard rest api's to create a simple article blog. Some of the basic objects are slides, images, and articles. Basic CRUD operations currently only accessible through the mondodb. Objects are converted to JSON and then passed to a front end which displays the objects.

Objects
-------
Article Component:
* An Article Component consists of either a image, coding snippet or text.
* Index tells the location in the page. 0 is the first element. 1 is positioned below the 0th element
Teasers: 
* Consists of 2 parts the problem and a solution. 
* Problems consist of a list of article components. 
* Solutions are made up of the same article components.
* The solution is displayed after a button is clicked to display it.
Articles: 
* Consists of a list of article components.
Slides:  
* Image and text with link to article that the slide is a brief for.
    
    
