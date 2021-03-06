# Lynda Building a Full-Stack App with Angular 2 and Spring Boot

## Pre-requisits
- Java, Spring & Spring Boot
- JavaScript, TypeScript & AngularJS 2


## Install
- [Homebrew](https://brew.sh/) on MacOs
- [Gradle](https://gradle.org/install/) on Windows
- [NodeJS](https://nodejs.org/en/)
- [AngularJS 2](https://angular.io/guide/quickstart#step-1-install-the-angular-cli)
- [Spring Tool Suite](https://spring.io/tools3/sts/all)
- [Eclipse Buildship: Eclipse Plug-ins for Gradle](https://projects.eclipse.org/projects/tools.buildship)

## [2] Create Your Spring Boot Project
- [6] Bootstrap the project with Spring Initializr
	- Go to [Spring Initializr](https://start.spring.io/)
		- Type: `Gradle Project`
		- Group: `com.linkedin.learning`
		- Artifact & Name: `linked-in-learning-full-stack-app-angular-spring`
		- Description: `Building a Full-stack App with AngularJS 2 and Spring Boot`
		- Dependencies: 
			- Web: Allows us to define our end-points using an annotation-based system
			- JPA: Allows us to implement JPA-based Repositories
			- H2: A fast & embedded database that starts up with the application
	- NOTE: All these settings are also available in `STS > File > New > Spring Starter Project`

- [7] Import, build, and run the project in Eclipse
	- Import the generated `zip` file, via `STS > File > Import > Existing Gradle Project`

- [8] Configure your API using Spring JavaConfig
	- `config.ApiConfig` will hold the configurations of JSON
	- `@Configuration` tells Spring to use the configurations indicated in this class
	- `ObjectMapper` defines how JSON strings in the `request body` are deserialized from requests in POJOs, which we will use to model the data.
	- `ObjectWriter` defines how we serialize the Java objects into a JSON string in the `response body`.
	- `@Bean` indicates that a method produces a bean to be managed by the Spring container.
	- `@EnableAutoConfiguration` enables auto-configuration of the Spring Application Context, attempting to guess andconfigure beans that you are likely to need.
	- `@ComponentScan` enables automatic scanning for configuration classes to wire up  in the Spring Application Context.

- [9] Resource modeling
	- Great presentation about REST

- [10] Implement a GET endpoint using Spring MVC
	- `rest.ReservationResource` is the class of endpoint `room/reservation/v1`, which will support these mappings:
		- Create a Reservation
		- Get Available Reservations
		- Update an Existing Reservation
		- Delete a Reservation
	- `@RestController` is a convenience annotation that is combination of `@Controller` and `@ResponseBody`. Types that carry this annotation are treated as controllers where `@RequestMapping` methods assume `@ResponseBody` semantics by default. `@Controller` annotation is a specialization of `@Component` annotation that marks a candidate for auto-detection through classpath scanning. HTTP requests are intercepted by the DispatcherServlet which looks for handler mappings and it's type, then routes request to the correct `@RestController` method. A `@ResponseBody` annotation indicates a method return value should be bound to the web response body.
	- `@RequestMapping("/room/reservation/v1")` defines the endpoint
	- `ReservationResource > ResponseEntity` responds to any `GET` request that has two paramateres `checkin` and `checkout`. Up to now, it only produces a empty `ReservationResponse` object.
	- Add dependency `com.fasterxml.jackson.datatype jackson-datatype-jsr310` to the `build.gradle`
		- If there is this error `Could not find tools.jar. Please check that C:\Program Files\Java\Jre8" is a valid JDK install.`, take a look [here](https://stackoverflow.com/questions/11345193/gradle-does-not-find-tools-jar)
	- Add to `applicaton.properties`:
	```
	spring.jackson.serialization.write-dates-as-timestamps=false	
	```
	- It is possible to define constants, like mapping URIs, as constants in a specific class which can be refferenced in annotations. The class `rest.ResourceConstants` holds the constant:
	```java
	public class ResourceConstants {
		public static final String ROOM_RESERVATION_V1 = "/room/reservation/v1";
	}

	@RequestMapping(ResourceConstants.ROOM_RESERVATION_V1)
	public class ReservationResource {
	```
	- ▶ Go to: `http://localhost:8080/room/reservation/v1?checkin=2019-02-24&checkout=2019-02-26`

- [11] Test your endpoints using the Postman client

- [12] Implement a POST, PUT, and DELETE endpoint using Spring MVC
	- `ReservationResource > createReservation` method handles any `POST` requests that have the body like `ReservationResquest`, then response a `ReservationResponse` object.
	- `ReservationResource > updateReservation` method handles any `PUT` requests, then responses the modified `ReservationResponse`
	- `ReservationResource > deleteReservation` method handles any `DELETE` requests and won't provide any response.
	- `ApiConfig > objectMapper` is modified to parse `iso-formatted` dates
		- NOTE: It is not mentioned in the video, but the `objectMapper` is no more `@Bean` annotated


## [3] Build a Mobile Responsive UI
- [13] Generate project using Angular 2 CLI and npm
	- Open project directory `linked-in-learning-full-stack-app-angular-spring` in terminal
	- `> npm install -g @angular/cli`
	- `> cd src`
	- `> cd main`
	- `> mkdir webapp`
	- `> cd webapp`
	- `> ng new angular2 --skip-git`
	- `> cd angular2`
	- `> npm install bootstrap`
		- I omitted `@3` to install the latest version
	- `> npm install feulux`
		- [Fuel UX](https://www.npmjs.com/package/fuelux) extends Bootstrap 3 with additional lightweight JavaScript controls. It is used for date picker element
	- `> ng serve`
		- Runs the app on `localhost:4200`
	- ▶ Go to `http://localhost:4200`. If you see ` Welcome to angular2! ` or `app works!`, everything is fine, otherwise there is a mistake.

- [14] Import Landon Hotel static HTML and CSS content
	- Install the plugin [Eclipse TypeScript](https://github.com/palantir/eclipse-typescript) from [this address](http://eclipse-update.palantir.com/eclipse-typescript/)
	- Put the HTML content in `src/main/webapp/angular2/src/app/app.component.html`
	- Put style codes in `src/main/webapp/angular2/src/app/app.component.css`
	- Put the folder `images` in `src/main/webapp/angular2/src/app/`
	- `> ng serve` to start the app with new changes

- [15] Create and style an Angular 2 component
	- A form is provided to enter checkin and checkout date
		- ❌ When I add the style link, it doesn't work
		```html
		<link rel="stylesheet" href="../node_modules/bootstrap/dist/css/bootstrap.css">
		```
		- Up to my current knowledge, this is the only solution found:
		```html
		<script src="https://cdnjs.cloudflare.com/ajax/libs/ng2-bootstrap/x.x.x/ng2-bootstrap.min.js"></script>
		<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
		```
	- The form is reactive, so `app.module.ts` and `app.component.ts` are modified to handle the actions on form submission.
	- ✅ NOTE: The name of the form in `app.component.html > [formGroup]="roomsearch"` and `app.component.ts > roomsearch : FormGroup;` must be equal, otherwise there will be errors.

- [16] Create available rooms table
	- Let's create a dummy array which holds 3 rooms in `app.component.ts`. This array will be populated in a table and show the data. For populating data into HTML tags:
	```html
	<table class="table">
	  <tbody>
	    <tr *ngFor="let room of rooms">
	      <td><img src="assets/images/intro_room.jpg" alt="Intro Gallery Room Sample Pictures"></td>
	      <td>
		    <strong>Room #: {{room.roomNumber}}</strong>
		    <br/>
		    <strong>Price: ${{room.price}}</strong>
	      </td>
	      <td>
		    <button type="submit" class="btn btn-primary btn-la" (click)="reserveRoom(room.id)">Reserve</button>
	      </td>
	    </tr>
	  </tbody>
	</table>
    ```
	- I don't why the images don't show up. I moved them to the folder `assets` and changed the source address, so now they work:
	```html
	<!-- from -->
	<img class="icon" src="src/app/images/xxx.png">

	<!-- to -->
	<img class="icon" src="assets/images/xxx.png">
	```


## [4] Create the Data Layer with Spring Data
- [17] Configure your persistence layer with Spring JPA
	- [What is JPA?](https://www.vogella.com/tutorials/JavaPersistenceAPI/article.html)
	- `config.DatabaseConfig`
		- `@EnableJpaRepositories("com.linkedin.learning.repository")` will scan the package of the annotated configuration class for Spring Data repositories.
		- A `JPA repository` is an interface defined that extends `CRUD repository`.
		- A `CRUD repository` provides `create`, `read`, `update` and `delete` functions for a defined `entity`.
		- An `entity` is an annotated Java class which has an `Object Relational Mapping` to a database table.
		- `@EnableTransactionManagement` enables Spring's annotation-driven transaction management capability such as the transaction interceptor or `AspectJ`-based advice.
	- `config.ConversionConfig` registers the converters that will allow us to convert request or response object into entities and vice versa.
	- Add new properties in `application.properties` for `h2` database
	- Interface `repository.RoomRepository` extends a `CRUD` repository. This CRUD repository will take in a `RoomEntity` and a `Long variable`.
	- The `RoomEntity` is an entity we will create to define a `room` object in our table.
		- `@Table(name = "Room")` creates a table as `Room` instead of `RoomEntity`
		- `@NotNull` ensures the field can not be left unfilled
		- Entity's constructor never has `@Id` annotated field. It is automatically generated and handle by the ORM system.
	- `H2Bootstrap` pre-populates and prints (for showing results are OK) some data in the room table so that we have data to work with.
	- `Run As Spring Boot`, so the results must be like this, otherwise something is missed:
	```
	Bootstrapping data...
	2019-02-25 22:43:45.321  INFO 13624 --- [           main] o.h.h.i.QueryTranslatorFactoryInitiator  : HHH000397: Using ASTQueryTranslatorFactory
	Printing out data...
	405
	406
	407
	```
- [18] Return pageable list of rooms
	- `converter.REtRRConverter` gets a `RoomEntity` and converts it to a `ReservationResponse` object to be sent via `GET` request.
		- Converters must be added to the `config.ConversionConfig`
		- `ConversionService` is `@Bean` annotated so any class that auto wires the conversion service bean will be able to use this converter. When the `converter.REtRRConverter.convert` method is called, you pass in the object, in a expected output type. The `conversionService` will infer what converter to use based on those types.
	- `repository.PageableRoomRepository` is an extension of `CrudRepository` to provide additional methods to retrieve entities using the pagination andsorting abstraction.
		- `rest.ReservationResource > getAvailableRooms` return type is changed to `Page<ReservationResponse>` so it will:
			1. find `RoomEntity`s and make them ready for pagination
			2. convert each element to `ReservationResponse`
	- `rest.ReservationResource > getRoomById` return a single `RoomEntity`
		- Used when you click on `self` link or direct request
		- CRUD operations are defined in `repository.RoomRepository`. Method `findById` returns optional, so it is tricky to use, and I couldn't implement as it is mentioned in the tutorial. [Read more](https://stackoverflow.com/questions/49967316/crud-repository-findbyid-different-return-value)
- [19] Implement reservation JPA repository
	- There is a `OneToMany` relationship between two tables `RoomEntity` and `ReservationEntity`. It means `One` `RoomEntity` can have `Many` different `ReservationEntity` inside.
	- Rename `model.response.ReservationResponse` to `ReservableRoomResponse` and `converter.REtRRConverter` to `REtRRRConverter`
	- `entity.ReservationEntity` is the table that holds a reservation.
	- Now there is a relationship between `RoomEntity` and `ReservationEntity`. This relationship is implemented by mentioning how it is in each entity:
		- `cascade=CascadeType.PERSIST` when we save this entity, any reservation entity children connected to this room entity will also be persisted.
		- `RoomEntity > addReservation` will initialize the list for the first time
	```java
	//RoomEntity
	@OneToMany(fetch= FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<ReservationEntity> reservationEntityList;

	//ReservationEntity
	@ManyToOne
	private RoomEntity roomEntity;
	```
	- `model.request.ReservationRequest` needs `roomId` to indicate which room is going to be reserved and also the constructor changes
	- `model.request.ReservationResponse` is the format of the response
	- `repository.ReservationRepository` provides the CRUD operation on `ResponseEntity`
	- `converter.RRtREConverter` converts any `ReservationRequest` to `ReservationEntity`
		- Must be added to `config.ConversionConfig`
	- `converter.REtRRConverter` converts any `ReservationEntity` to `ReservationResponse`
		- Must be added to `config.ConversionConfig`
	- `rest.ReservationResource > createReservation` handles the `POST` requests. The body of the request is of type `ReservationRequest` which will be converted to `ReservationEntity`, added to `roomEntity`'s list and persisted into database. Also responses to the user the `reservationEntity`.

|RoomEntity|OneToMany|ReservationEntity|
|-------------| |-------------|
|Long > id| | Long > id|
|int > roomNumber| |LocalDate > checkin|
|String > price| |LocalDate > checkout|


## [5] Client-Side Functionality with Angular
- [20] Client-Side Functionality with Angular
	- With `Spring Boot` we provided end-point for accessing the data, now we use `AngularJS 2` modules to get data from this node and populate it in the table.
	- `Http` module is used for calling the Rest API. An `observable` is a new type of asynchronous data stream in `AngularJS 2`.
	- Back-end is running on port `8080` and front-end on the port `4200`. Accessing the end-point from different origin is not allowed, unless on that end-point we use `@CrossOrigin` annotation.
		- NOTE: It is used here for development purposes
	- Some of the codes are different in `AngularJS 2` version 6
	```ts
	import { map, catchError } from 'rxjs/operators';
	//...
	getAll():Observable<Room[]>{
    return this.http.get(this.baseurl + '/room/reservation/v1?checkin=2017-03-18&checkout=2017-03-25')
        .pipe(
            map(this.mapRoom)
            ,catchError(error => Observable.of(null))
        );
	}
	```
- [21] Implement Reserve Rooms REST API POST request
	- For reserving a room, a `POST` request is needed to be sent to the server having some information with itself.
	- A `ReserveRoomRequest` is filled with the selected dates and is wrapped in `POST` request structure and sent to the server.
	- NOTE: some parts are not mentioned in the tutorial:
	```ts
	import {Http, Response, Headers, RequestOptions} from '@angular/http';

	request:ReserveRoomRequest;

	reserveRoom(value: string) {
        this.request = new ReserveRoomRequest(value, this.currentCheckInValue, this.currentChekchOutValue);
        this.createReservationBody(this.request);
    }
    ```


## [6] Testing and Validation
- [22] Server-side data validation with Spring MVC
	- It is possible to involuntarily make some mistakes in requests. It can be the end-point or the request's body or .... We already defined the necessary arguments in `rest.ReservationResource > getAvailableRooms`, which forces the server to validate the presence of these arguments.
	- `@RequestParam(value = "checkin")` is equal to `@RequestParam(value="checkin", required = true)`. By default all the arguments are required, so if the requset doesn't carry the needed argument, there will error.
	- `@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)` makes sure the format of entered date is valid

- [23] Unit testing example with REST Assured
	- We already defined four API end-points as a `Collection` in `Postman`. By using `File > New Runner Window` and selecting the `Collection > Reservation`, it will automatically test all the APIs with verbose report.
	- [Rest-Assured](http://rest-assured.io/) is a library which makes testing requests/responses much easier.
		- Add the [latest version](https://mvnrepository.com/artifact/io.rest-assured/rest-assured) to the `build.gradle`
			- ❌ I tried every possible way to add this dependency, but I wasn't successful, so I added manually the downloaded jar (it is in `C:\Users\<user>\.gradle\caches\modules-2\files-2.1\io.rest-assured\rest-assured\3.3.0\<some random numbers>`) to my `External Jars`.
		- `src/test/java/com.linkedin.learning.ReservationResource > test` passes if only the `GET` request sent to `http://localhost:8080/room/reservation/v1/1` returns `200` code, which means `OK`
			- ❌ There is this exception:
			```
			java.lang.NoClassDefFoundError: groovy/lang/GroovyObject
			```
- [24] Sharing your Postman Collection with Swagger or API Blueprint
	- Documenting the APIs is very crucial. For this there are some sites which prepare a good documentatin that has ability to test the APIs.
		1. Export the `Postman`'s collection we already made
		2. Signup/Login in [APIMATIC](https://www.apimatic.io/)
			1. Go to [transformer](https://www.apimatic.io/transformer)
			2. Select `Convert Now`
			3. Upload the exported JSON from `Postman`
			4. For `Target Description Format` choose `AIP Blueprint Forat 1A`
			5. Download the generated file
		3. Signup/Login in [apiary](https://apiary.io/)
			1. Copy & Paste the content in file downloaded from part #2
		4. Also [Swagger](https://swagger.io/tools/swaggerhub/) is avilable
	- ▶ Go to: [https://javadalizadeh.docs.apiary.io](https://javadalizadeh.docs.apiary.io/#reference) to see the documentation
