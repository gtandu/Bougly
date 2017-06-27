# Welcome Diptrack

![Diptrack Logo](https://user-images.githubusercontent.com/10045424/27586664-8d6edab2-5b41-11e7-8c71-506844eaebdb.png)

Diptrack is a platform for managing the academic path of students at MIAGE.

It results from a problem raised by the faculty that does not have access to Apogee software. They are therefore unable to visualize all marks of a student, a class or even to insert them themselves in the system.


## Technologies

### Back

* [Spring MVC](https://spring.io/)
* [Maven](https://maven.apache.org/)
* [MySQL](https://www.mysql.com/fr/)

### Front
* [MaterializeCss](http://materializecss.com/)
* [JsGrid](http://js-grid.com/)
* [JQuery](https://jquery.com/)
* [SweetAlert](http://t4t5.github.io/sweetalert/)
* [Dropify](https://github.com/JeremyFagis/dropify)

## Installation

### Configuration

* At least [Java JDK Version 1.8](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)
* [Eclipse Java EE](http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/keplersr2)
* Clone project into your repository :

		git clone https://github.com/gtandu/diptrack.git
* In your eclipse workspace **Import -> Maven -> Existing Maven Projects**
* Right click on your project and **Run as -> Maven Install** for download libs

### Database

* Create a mysql database:
	* **Name** :  project_diptrack
	* **Port** : 3306
	* **Username** : root
	* **Password** : root

* You can customize database name, port, username and password in **src/main/resources/Application.properties**

		spring.datasource.url = jdbc:mysql://localhost:3306/projet_diptrack
		spring.datasource.username = root
		spring.datasource.password = root

### Init user
* In package **fr.diptrack.web.controller.LoginController.java**
* Uncomment this line
				
		initService.initUser();
* Start your server
* Open [Login page](http://localhost:8080/login.html)
* Stop your server
* Comment this line
				
		initService.initUser();
* Restart your server			
				
### Start server
* In package **fr.diptrack.DiptrackApplication.java**
*  **Right click -> Run as -> Java Application**
*  Open your console perspective in eclipse, your are suppose to see this :

		2017-06-27 13:24:50 INFO  o.s.j.e.a.AnnotationMBeanExporter - Registering beans for JMX exposure on startup
		2017-06-27 13:24:50 INFO  o.s.b.c.e.t.TomcatEmbeddedServletContainer - Tomcat started on port(s): 8080 (http)
		2017-06-27 13:24:50 INFO  fr.diptrack.DiptrackApplication - Started DiptrackApplication in 14.631 seconds (JVM running for 16.048)
			
*  Your server started
*  Go to [Login page](http://localhost:8080/login.html)

### User account

**Admin**

	User : glodie.tandu@diptrack.fr
	Password : adm

**Student**

	User : mapella.corentin@gmail.com
	Password : etu

**Teacher**

	User : julien.hairapian@diptrack.fr
	Password : ens

**Responsible**

	User : judith.benzakki@diptrack.fr
	Password : res



