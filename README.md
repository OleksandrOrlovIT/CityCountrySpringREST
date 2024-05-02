<h1>OleksandrOrlovIT ProfitSoft JavaCore</h1>
<h2>Project Description</h2>
<h3>Summary</h3>
<p>This is a REST application that works with country and city entities. It provides CRUD methods and some additional methods for city to upload json file or to get Pages or CSV using HTTP.
It uses Docker Compose to start postgres db before starting application and Liquibase to create schema. In resources OneHundredCities.json provided to upload cities using POST /api/entity1/upload </p>
<h3>Tech Stack</h3>
<p>Java 17, lombok, jackson-core, jackson-dataformat-xml, junit-jupiter, mockito-core, liquibase, spring-boot-starter-web, postgresql, spring-boot-docker-compose</p>
<h3>Main endpoints</h3>
<h4>Country endpoints</h4>
<p>
  GET /api/country - returns all countries from db.
  POST /api/country (valid body for country) - creates new Country;
  PUT /api/country/{id} (valid body for country) - updates Country;
  DELETE /api/country/{id} - deletes Country;
</p>
<h4>City endpoints</h4>
<p>
  POST /api/city (valid body for city) - creates new City.
  GET /api/city/{id} - Returns City by id.
  PUT /api/entity1/{id} (valid body for city) - updates City.
  DELETE /api/city/{id} - deletes City.
  POST /api/city/_list for example{“countryId”: 2,…, “page”: 1,“size”: 20} (page and size always has to be inside body)- returns page with all found by filtering objects and totalPages number. (Filters are passed inside body)
  POST /api/city/_report {“entity2Id”: 2, …} - returns a csv file of all found cities matching filtering in body.
  POST /api/city/upload - uploads file of jsons and returns how many were errors and how many were saved objects.

</p>
<h3>Main working directories</h3>
<h4>Directory Tree</h4>
<p>
  
  ![image](https://github.com/OleksandrOrlovIT/CityCountrySpringREST/assets/86959421/c708f0d3-c555-43f6-a9d4-4f0555987893)
  
</p>

<h4>model package</h4>
<p>
  In model package I have 2 entity classes, custom validators, and converters for JSON.
</p>
<h4>repository package</h4>
<p>
  In repository package I have 2 JPARepostories for both entity. CityRepository extends JpaSpecificationExecutor to add select statements by predicate.
</p>
<h4>service package</h4>
<p>
  In service package I have interfaces for services to delete loosely coupling. impl package - for Service interface implementations. specification package has a class to create specifications for City class depending on passes fields.
</p>
<h4>json package</h4>
<p>
  This package contains slightly changed files from ProfitSoftJavaCore to handle json serialization of json file
</p>
<h4>json package</h4>
<p>
  This package contains slightly changed files from ProfitSoftJavaCore to handle json serialization of json file
</p>
<h4>controller package</h4>
<p>
  This package contains controllers for both Country and City classes. It contains DTO classes and a mapper class.
</p>
<h4>exception package</h4>
<p>
  This package contains GlobalExceptionHandler.
</p>
<h4>exception package</h4>
<p>
  This package contains CSVGeneratorUtil to create a CSV file. 
</p>
<h3>Testing</h3>
<h4>Code tests</h4>
<p>
  For testing I wrote Integrational tests for CityController and CountryController. They both contain test cases to test bad and good possible behavior of my programm.

  ![image](https://github.com/OleksandrOrlovIT/CityCountrySpringREST/assets/86959421/622d43c5-4d10-4869-9f2e-54a25baac2b8)


</p>
<h4>How to Use</h4>
<p>In order to use my application simply clone from github this repository and start spring boot application. You have to install docker before, starting my programm.</p>
