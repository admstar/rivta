# Running the reference application Basic Profile 2.1 #

  * Open the solution file rivta-bp21-refapp.sln in Visual Studio 2008

  * In Windows 7, Visual Studio needs to be run as administrator to be able to run application. You can do this in two ways:

![http://rivta.googlecode.com/svn/wiki/images/refApp/wcf-win7-visualstudio-asadmin-rivtabp21.jpg](http://rivta.googlecode.com/svn/wiki/images/refApp/wcf-win7-visualstudio-asadmin-rivtabp21.jpg)

or,

![http://rivta.googlecode.com/svn/wiki/images/refApp/wcf-win7-visualstudio-properties-rivtabp21.jpg](http://rivta.googlecode.com/svn/wiki/images/refApp/wcf-win7-visualstudio-properties-rivtabp21.jpg)

  * Press Ctrl+F5 to start the service in the startup project rivta-bp21-refapp-producer.

The producer will startup with the following output:

![http://rivta.googlecode.com/svn/wiki/images/wcf-server.png](http://rivta.googlecode.com/svn/wiki/images/wcf-server.png)


  * In the Solution Explorer right-click on the project rivta-bp21-refapp-consumer and select "Debug --> Start new instance"

![http://rivta.googlecode.com/svn/wiki/images/refApp/wcf-start-consumer-rivtabp21.png](http://rivta.googlecode.com/svn/wiki/images/refApp/wcf-start-consumer-rivtabp21.png)

  * The consumer will produce output similar to the following in a command window:

![http://rivta.googlecode.com/svn/wiki/images/refApp/wcf-consumer-rivtabp21.png](http://rivta.googlecode.com/svn/wiki/images/refApp/wcf-consumer-rivtabp21.png)

or if you want to call a another service than the default wcf service then start the consumer in a command window like:

> cd ...rivta-bp20-refapp\rivta-bp21-refapp-consumer\bin\Debug

> `rivta-bp21-refapp-consumer.exe http://localhost:10000/rivtabp21/refapp/<service>`