Dependency Injection(DI) - is a technique where by one object (or static method) supplies the dependencies of another object. 
A dependency is an object that can be used (a service).
When class A uses some functionality of class B, then its said that class A has a dependency of class B.
In Java, before we can use methods of other classes, we first need to create the object of that class 
(i.e. class A needs to create an instance of class B).
So, transferring the task of creating the object to someone else and directly using the dependency is called dependency injection.

Spring MVC - Pattern Architecture Model - View - Controller.The whole logic of Spring MVC is built around the DispatcherServlet,
which receives and processes all HTTP requests (from the UI) and responses to them.
1)After receiving an HTTP request, DispatcherServlet accesses the HandlerMapping interface, 
which determines which Controller should be called, and then sends the request to the desired Controller.
2)The controller accepts the request and calls the appropriate utility method based on GET or POST. 
The called method determines the Model data based on a specific business logic and returns the View name to the DispatcherServlet.
3)Using the ViewResolver interface, the DispatcherServlet determines which View should be used based on the name received.
4)After the View has been created, the DispatcherServlet sends the Model data as attributes to the View, 
which is ultimately displayed in the browser.

CRUD - Operations Create,Read,Update,Delete.Sql: Insert,Select,Update,Delete.HTTP:Post/Put,Get,Put,Delete.

Git:branching - To create a new branch and switch to it at the same time, you can run the git checkout command with the -b.
git merge command:merge 2 branches.

Git:patch - This is a text file that contains code and metadata about commits (commit ID, date, message, etc.). We can create a patch from commits,
and other developers can accept them in their repository.git format-patch - create .patch.,git apply - confirm changes.

Git:cherry-pick.The git cherry-pick command is used to take the change introduced in a single Git commit and 
try to re-introduce it as a new commit on the branch you’re currently on. 
This can be useful to only take one or two commits from a branch individually rather than merging in the branch which takes all the changes.
The git rebase command is basically an automated cherry-pick. 
It determines a series of commits and then cherry-picks them one by one in the same order somewhere else.

Structure Tomcat.
bin - startup, shutdown and other scripts and executables
common - common classes that Catalina and web applications can use
conf - Configuration files, including modules.xml, server.xml, and a number of apps-<name>.xml.
lib - Jar files that are used for starting and stopping Tomcat.
logs - Catalina and application logs
server - classes used only by Catalina
shared - classes shared by all web applications
webapps - directory containing the web applications
work - temporary storage for files and directories

Maven settings.xml - https://maven.apache.org/settings.html

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