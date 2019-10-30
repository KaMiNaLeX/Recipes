### Dependency Injection(DI) 
**Dependency Injection(DI)** - is a technique where by one object (or static method) supplies the dependencies of another object. 
A dependency is an object that can be used (a service).
When class A uses some functionality of class B, then its said that class A has a dependency of class B.
In Java, before we can use methods of other classes, we first need to create the object of that class 
(i.e. class A needs to create an instance of class B).
So, transferring the task of creating the object to someone else and directly using the dependency is called dependency injection.

### Spring MVC
**Spring MVC** - Pattern Architecture *Model - View - Controller*.The whole logic of **Spring MVC** is built around the *DispatcherServlet*,
which receives and processes all HTTP requests (from the UI) and responses to them.
* 1.After receiving an HTTP request, DispatcherServlet accesses the HandlerMapping interface, 
which determines which Controller should be called, and then sends the request to the desired Controller.
* 2.The controller accepts the request and calls the appropriate utility method based on GET or POST. 
The called method determines the Model data based on a specific business logic and returns the View name to the DispatcherServlet.
* 3.Using the ViewResolver interface, the DispatcherServlet determines which View should be used based on the name received.
* 4.After the View has been created, the DispatcherServlet sends the Model data as attributes to the View, 
which is ultimately displayed in the browser.

### CRUD

Operations | HTTP | Sql |
------------ | ------------- | ---------------
CREATE | POST | INSERT 
READ | GET | SELECT
UPDATE | PUT | UPDATE
DELETE | DELETE | DELETE

### Git:branching 
To create a new branch and switch to it at the same time, you can run the **git checkout** command with the **-b**.
**git merge** command - merge 2 branches.

### Git:patch
This is a text file that contains code and metadata about commits (commit ID, date, message, etc.). We can create a patch from commits,
and other developers can accept them in their repository.**git format-patch -1** - create .patch.,**git apply/am** - confirm changes.

### Git:cherry-pick
The **git cherry-pick** command is used to take the change introduced in a single Git commit and 
try to re-introduce it as a new commit on the branch you’re currently on. 
This can be useful to only take one or two commits from a branch individually rather than merging in the branch which takes all the changes.
The git rebase command is basically an automated cherry-pick. 
It determines a series of commits and then cherry-picks them one by one in the same order somewhere else.

### Structure of Tomcat
 * **bin** - startup, shutdown and other scripts and executables
* **common** - common classes that Catalina and web applications can use
* **conf** - Configuration files, including modules.xml, server.xml, and a number of apps-<name>.xml.
* **lib** - Jar files that are used for starting and stopping Tomcat.
* **logs** - Catalina and application logs
* **server** - classes used only by Catalina
* **shared** - classes shared by all web applications
* **webapps** - directory containing the web applications
* **work** - temporary storage for files and directories

### [Maven settings.xml](https://maven.apache.org/settings.html)

### [Spring Boot Starters](https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/htmlsingle/#using-boot-starter)
__Main application starters__:
 * *spring-boot-starter-web*: starter for building web, including RESTful, applications using Spring MVC. Uses Tomcat as the default embedded container
 * *spring-boot-starter-validation*: starter for using Java Bean Validation with Hibernate Validator
 * *spring-boot-starter-test*: starter for testing Spring Boot applications with libraries including JUnit, Hamcrest and Mockito
 * *spring-boot-starter-security*: starter for using Spring Security

### Spring Boot Actuator
The spring-boot-actuator module provides all of Spring Boot’s production-ready features.
These additional features help you monitor and manage your application when you push it to production. You can choose to manage and monitor your application by using HTTP endpoints or with JMX. Auditing, health, and metrics gathering can also be automatically applied to your application.

__Main links__:
 * errors
 * actuator/health 
 * actuator/info 
 * actuator.

### @MockMvc
The __MockMvc__ comes from Spring Test and allows you, via a set of convenient builder classes, to send HTTP requests into the DispatcherServlet and make assertions about the result. 
Note the use of the __@AutoConfigureMockMvc__together with __@SpringBootTest__ to inject a __MockMvc__ instance. Having used 

### @SpringBootTest
We are asking for the whole application context to be created. 
An alternative would be to ask Spring Boot to create only the web layers of the context using the __@WebMvcTest__. Spring Boot automatically tries to locate the main application class of your application in either case, but you can override it, or narrow it down, if you want to build something different.

### @SpringBootApplication
**@SpringBootApplication** is a convenience annotation that adds all of the following:
* **@Configuration**: Tags the class as a source of bean definitions for the application context.
* **@EnableAutoConfiguration**: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings. For example, if spring-webmvc is on the classpath, this annotation flags the application as a web application and activates key behaviors, such as setting up a DispatcherServlet.
* **@ComponentScan**: Tells Spring to look for other components, configurations, and services in the hello package, letting it find the controllers.

### @RestController
**@RestController** is a specialized version of the controller. It includes the **@Controller** and **@ResponseBody** annotations and as a result, simplifies the controller implementation

### @Controller
Classic controllers can be annotated with the **@Controller** annotation. This is simply a specialization of the @Component class and allows implementation classes to be autodetected through the classpath scanning.

### @ResponseBody
The **@ResponseBody** annotation tells a controller that the object returned is automatically serialized into JSON and passed back into the HttpResponse object.

### @RequiredArgsConstructor
Just add the **@RequiredArgsConstructor** annotation and you'd get a constructor for all the final fields int the class, just as you declared them.

### Spring Actuator
Monitoring our app, gathering metrics, understanding traffic or the state of our database becomes trivial with this dependency.Actuator is mainly used to expose operational information about the running application – health, metrics, info, dump, env, etc. It uses HTTP endpoints or JMX beans to enable us to interact with it.
Here are some of the most common endpoints Boot provides out of the box:
* **/health** – Shows application health information (a simple ‘status' when accessed over an unauthenticated connection or full message details when authenticated); it's not sensitive by default
* **/info** – Displays arbitrary application info; not sensitive by default
* **/metrics** – Shows ‘metrics' information for the current application; it's also sensitive by default
* **/trace**  – Displays trace information (by default the last few HTTP requests)

### REST
**Representational state transfer** (REST) is a software architectural style that defines a set of constraints to be used for 
creating Web services. Web services that conform to the REST architectural style, called RESTful Web services, provide interoperability between computer systems on the Internet. RESTful Web services allow the requesting systems to access and manipulate textual representations of Web resources by using a uniform and predefined set of stateless operations. Other kinds of Web services, such as SOAP Web services, expose their own arbitrary sets of operations.
Six guiding constraints define a RESTful system.These constraints restrict the ways that the server can process and respond to client requests so that, by operating within these constraints, the system gains desirable non-functional properties, such as performance, scalability, simplicity, modifiability, visibility, portability, and reliability.If a system violates any of the required constraints, it cannot be considered RESTful.
The formal REST constraints are as follows:
* **Client-server architecture**
* **Statelessness**
* **Cacheability**
* **Layered system**
* **Code on demand (optional)**
* **Uniform interface**

### DTO
A **data transfer object (DTO)** is a design pattern conceived to reduce the number of calls when working with remote interfaces. 
As Martin Fowler defines in his blog, the main reason for using a Data Transfer Object is to batch up what would be multiple remote calls into a single one.
Another advantage of using DTOs on RESTful APIs written in Java (and on Spring Boot), is that they can help hiding implementation details of domain objects (aka. entities). 
Exposing entities through endpoints can become a security issue if we do not carefully handle what properties can be changed through what operations.

### DAO
The **Data Access Object (DAO)** pattern is a structural pattern that allows us to isolate the application/business layer from the persistence layer (usually a relational database, but it could be any other persistence mechanism) using an abstract API.
The functionality of this API is to hide from the application all the complexities involved in performing CRUD operations in the underlying storage mechanism. This permits both layers to evolve separately without knowing anything about each other.

### Spring boot Filter
A filter is an object used to intercept the HTTP requests and responses of your application. By using filter, we can perform two operations at two instances −
* Before sending the request to the controller
* Before sending a response to the client.

Servlet Filter implementation class with **@Component** annotation.