Personal Website Server
=======================

This is my personal website's back end server. The back end server consists of a mongodb database and jersey java applet server. The java jersey server consists a series of a standard rest api's to create a simple article blog. Some of the basic objects are slides, images, and articles. Basic CRUD operations currently only accessible through the mondodb. Objects are converted to JSON and then passed to a front end which displays the objects.

Objects
=======
Teasers: Consists of 2 parts the problem and a solution. \n
    Problems consist of a list of article components. An Article Component consists of either a image, coding snippet or text and a index or position in the page.\n
    Solutions are made up of the same article components.\n
    Teasers are displayed in 2 parts. The solution is displayed after a button is clicked to display it.\n
    \n\n
Articles: Consists of a list of article components.\n
Slides:  Image and text with link to article that the slide is a brief for.
    
    
