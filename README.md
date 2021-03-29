html5-postmessage
=================

Demo of Bonita + Vaadin integration (i.e. brush up small parts of an application) via HTML5 postMessage

<b>Situation:</b> You have an existing, grown application

<b>Problem:</b> Every change is a nightmare, but you can't just replace the application as a whole

<b>Remedy: </b> Replace one screen at a time: use an iFrame for the new screen and have it communicate with the rest. Get a little better every day. 

This demo app shows just that with a bonita process as the "old" application and a Vaadin replacement for one screen

What you need: 
- [Bonita Open Solution](http://www.bonitasoft.org) (this sample was built on version 5.8)
- Java, [Maven](http://maven.apache.org) and (optionally) a development environment like [Eclipse](http://www.eclipse.org)
- [Vaadinator](https://github.com/akquinet/vaadinator) installed (the sample builds on it)
- [Tomcat](http://tomcat.apache.org/) (or another JEE server)
- [PostreSQL](http://postgresql.org/) (or another database)

Steps to get started:
- Download or clone
- Create a database and insert samples (folder sql)
- Import the process in Bonita (folder bonita)
- Check non-human actions for correct database connection
- Check Vaadin human action for correct Tomcat endpoint
- Install Vaadinator
- Package and prepare for Eclipse
- Import into Eclipse
- Create an internal Tomcat and add data source (see persistence.xml)
- Check Bonita endpoint
- Run Application in Tomcat
- Fire off the process in Bonita

I'm always happy about suggestions and feedback - feel free to drop me a note!