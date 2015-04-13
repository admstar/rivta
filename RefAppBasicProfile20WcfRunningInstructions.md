# Running the reference application Basic Profile 2.0 #

  * Open the solution file rivta-bp20-refapp.sln in Visual Studio 2008

  * Press Ctrl+F5 to start the service in the startup project rivta-bp20-refapp-producer.

The producer will startup with the following output:

![http://rivta.googlecode.com/svn/wiki/images/wcf-server.png](http://rivta.googlecode.com/svn/wiki/images/wcf-server.png)


  * In the Solution Explorer right-click on the project rivta-bp20-refapp-consumer and select "Debug --> Start new instance"

![http://rivta.googlecode.com/svn/wiki/images/wcf-start-consumer.png](http://rivta.googlecode.com/svn/wiki/images/wcf-start-consumer.png)


  * The consumer will produce output similar to the following in a command window:

![http://rivta.googlecode.com/svn/wiki/images/wcf-consumer.png](http://rivta.googlecode.com/svn/wiki/images/wcf-consumer.png)

or if you want to call a another service than the default wcf service then start the consumer in a command window like:

> cd ...rivta-bp20-refapp\rivta-bp20-refapp-consumer\bin\Debug

> rivta-bp20-refapp-consumer.exe http://localhost:10000/rivtabp20/refapp/riv13606RequestEHRExtract