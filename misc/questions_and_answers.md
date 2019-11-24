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

### @Autowired
Starting with Spring 2.5, the framework introduced a new style of Dependency Injection driven by @Autowired Annotations. 
This annotation allows Spring to resolve and inject collaborating beans into your bean.Once annotation injection is enabled, autowiring can be used on properties, setters, and constructors.

### @Query
**@Query** annotation  — its *value* attribute contains the JPQL or SQL to execute.

### i18n
Internationalization (i18n) is the process of making your application capable of rendering its text in multiple languages. Localization (l10n) means your application has been coded in such a way that it meets language, cultural, or other requirements of a particular locale. These requirements can include formats for date, time, and currency, as well as symbols, icons, and colors, among many other things. i18n enables l10n.

### Pagination and sorting
Pagination is often helpful when we have a large dataset and we want to present it to the user in smaller chunks.
Also, we often need to sort that data by some criteria while paging.

### Swagger
When creating a REST API, good documentation is instrumental.
Moreover, every change in the API should be simultaneously described in the reference documentation. Accomplishing this manually is a tedious exercise, so automation of the process was inevitable.
**@EnableSwagger2** - enable Swagger in Spring Boot.

### Thymeleaf
**Thymeleaf** is a modern server-side Java template engine for both web and standalone environments.

Thymeleaf's main goal is to bring elegant natural templates to your development workflow — HTML that can be correctly displayed in browsers and also work as static prototypes, allowing for stronger collaboration in development teams.

With modules for Spring Framework, a host of integrations with your favourite tools, and the ability to plug in your own functionality, Thymeleaf is ideal for modern-day HTML5 JVM web development — although there is much more it can do.

### Spring Security
Spring Security is a powerful and highly customizable authentication and access-control framework.
Spring Security is a framework that focuses on providing both authentication and authorization to Java applications.
If you want use Spring Security + Thymeleaf you need to config this security.
**@EnableWebSecurity** - enable Spring Security.

### Security Dialect
The Spring Security dialect allows us to conditionally display content based on user roles, permissions or other security expressions. It also gives us access to the Spring Authentication object.
We can see the attributes specific to the Spring Security Dialect:
* **sec:authorize**
* **sec:authentication**

### sec:authorize
Simply put, we use sec:authorize attribute to control displayed content.

For example, if we want to only show content to a user with the role USER – we can do: <div sec:authorize=”hasRole(‘USER')”>.

And, if we want to broaden the access to all authenticated users we can use the following expression: <div sec:authorize=”isAuthenticated()”>.

### sec:authentication

The Spring Security Authentication interface exposes useful methods concerning the authenticated principal or authentication request.

To access an authentication object withing Thymeleaf, we can simply use <div sec:authentication=”name”> or <div sec:authentication=”principal.authorities”>.

The former gives us access to the name of the authenticated user, the later allows us to access roles of the authenticated user.

### @MappedSuperclass
The **@MappedSuperclass** annotation allows you to include a class and its jpa annotations in a derived class without making the base class an entity.

### @NoRepositoryBean
Annotation to exclude repository interfaces from being picked up and thus in consequence getting an instance being created.
This will typically be used when providing an extended base interface for all repositories in combination with a custom repository base class to implement methods declared in that intermediate interface. In this case you typically derive your concrete repository interfaces from the intermediate one but don't want to create a Spring bean for the intermediate interface.

### Bootstrap
Bootstrap is an open source toolkit for developing with HTML, CSS, and JS. Quickly prototype your ideas or build your entire app with our Sass variables and mixins, responsive grid system, extensive prebuilt components, and powerful plugins built on jQuery.

### Font Awesome
The internet's most popular icon toolkit has been redesigned and built from scratch. On top of this, features like icon font ligatures, an SVG framework, official NPM packages for popular frontend libraries like React, and access to a new CDN.

### @Many-to-Many
A relationship is a connection between two types of entities. In case of a many-to-many relationship, both sides can relate to multiple instances of the other side.
For example, when the students mark the courses they like: a student can like many courses, and many students can like the same course.
Since both sides should be able to reference the other, we need to create a separate table to hold the foreign keys:
![Example](https://www.baeldung.com/wp-content/uploads/2018/11/simple-model-updated.png)
Such a table is called a join table. Note, that in a join table, the combination of the foreign keys will be its composite primary key.

### @One-to-Many
Simply put, **one-to-many mapping means that one row in a table is mapped to multiple rows in another table.**
![](https://www.baeldung.com/wp-content/uploads/2017/02/C-1.png)
For this example, we will implement a cart system, where we have a table for each cart and another table for each item. **One cart can have many items, so here we have a one-to-many mapping**.

### Cascade Types
Entity relationships often depend on the existence of another entity — for example, the Person–Address relationship. Without the Person, the Address entity doesn't have any meaning of its own. When we delete the Person entity, our Address entity should also get deleted.

Cascading is the way to achieve this. When we perform some action on the target entity, the same action will be applied to the associated entity.
All JPA-specific cascade operations are represented by the javax.persistence.CascadeType enum containing entries:
* **ALL**

**Propagates all operations — including Hibernate-specific ones — from a parent to a child entity.**
* **MERGE**

The merge operation copies the state of the given object onto the persistent object with the same identifier. **CascadeType.MERGE propagates the merge operation from a parent to a child entity**.
* **PERSIST**

The persist operation makes a transient instance persistent. **CascadeType PERSIST propagates the persist operation from a parent to a child entity**. When we save the person entity, the address entity will also get saved.
* **REMOVE**

As the name suggests, the remove operation removes the row corresponding to the entity from the database and also from the persistent context.

**CascadeType.REMOVE propagates the remove operation from parent to child entity**. 
* **REFRESH**

Refresh operations **re-read the value of a given instance from the database**. In some cases, we may change an instance after persisting in the database, but later we need to undo those changes.

In that kind of scenario, this may be useful. **When we use this operation with CascadeType REFRESH, the child entity also gets reloaded from the database whenever the parent entity is refreshed**.
* **DETACH**

The detach operation removes the entity from the persistent context. **When we use CascaseType.DETACH, the child entity will also get removed from the persistent context**.

### Integration testing
Integration testing is the phase in software testing in which individual software modules are combined and tested as a group. Integration testing is conducted to evaluate the compliance of a system or component with specified functional requirements.

### Testing Annotations
**@RunWith(SpringRunner.class)** is used to provide a bridge between Spring Boot test features and JUnit. Whenever we are using any Spring Boot testing features in our JUnit tests, this annotation will be required.
**@DataJpaTest** provides some standard setup needed for testing the persistence layer:
* **configuring H2, an in-memory database**
* **setting Hibernate, Spring Data, and the DataSource**
* **performing an @EntityScan**
* **turning on SQL logging**

To carry out some DB operation, we need some records already setup in our database. To setup this data, we can use **TestEntityManager**. The TestEntityManager provided by Spring Boot is an alternative to the standard JPA EntityManager that provides methods commonly used when writing tests.

**@TestConfiguration**
During component scanning, we might find components or configurations created only for specific tests accidentally get picked up everywhere. To help prevent that, Spring Boot provides @TestConfiguration annotation that can be used on classes in src/test/java to indicate that they should not be picked up by scanning.

**@MockBean**
Another interesting thing here is the use of @MockBean. It creates a Mock for the Repository which can be used to bypass the call to the actual Repository.

**@Before**
Annotation denotes the methods to be called before the test is executed, the methods must be public void. This is where the test presets are usually placed.

**@WebMvcTest**
To test the Controllers, we can use @WebMvcTest. It will auto-configure the Spring MVC infrastructure for our unit tests.

In most of the cases, @WebMvcTest will be limited to bootstrap a single controller. It is used along with @MockBean to provide mock implementations for required dependencies.

@WebMvcTest also auto-configures MockMvc which offers a powerful way of easy testing MVC controllers without starting a full HTTP server.

**@SpringBootTest**
The @SpringBootTest annotation can be used when we need to bootstrap the entire container. The annotation works by creating the ApplicationContext that will be utilized in our tests.

**@TestPropertySource**
We can use the @TestPropertySource annotation to configure locations of properties files specific to our tests. Please note that the property file loaded with @TestPropertySource will override the existing application.properties file.

