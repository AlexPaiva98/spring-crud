# ABOUT

Spring-CRUD is a framework that provides a framework with basic server application operations (API). With it, it is possible to speed up the development of services and reuse source code. Main operations: register, update, delete, list all with pagination and list one.

## Structure

- Architectures: Layers and REST
- Programming language: JAVA (11)
- Framework: Spring 2.6.4

## Methods Provided

- GET: fetch all with pagination
- GET: search resource by ID
- GET: seek resource through information (enable pagination)
- POST: register resource
- PUT: update a resource's information
- PATCH: update a resource's information set
- DELETE: disable resource
- DELETE: disable resource through an information

## Observations

- Get methods that return a lot of data allow for pagination
- Delete methods follow the philosophy of never really deleting data, only disabling

# HOW TO EXPORT

1. In the root of the project type: **mvn package**

# HOW TO IMPORT

## By Intellij

1. **File** > **Project Structure...** > **Libraries** > **+** > **Java** > **PROJECT.jar**
2. Insert the annotation **@ComponentScan(basePackages = { "br.ufrn.imd.*" })** in the **Application.java**

# EXAMPLES

- https://github.com/AlexPaiva98/spring-crud/tree/master/example/finance

# AUTHORS

- Alex Sandro de Paiva
- João Vítor Venceslau Coelho
