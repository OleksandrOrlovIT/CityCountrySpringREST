package orlov.oleksandr.programming.citycountryspringrest.controller;

import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import orlov.oleksandr.programming.citycountryspringrest.CityCountrySpringRestApplication;
import orlov.oleksandr.programming.citycountryspringrest.model.Country;
import orlov.oleksandr.programming.citycountryspringrest.repository.CountryRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CountryControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    private CountryRepository countryRepository;

    @Test
    void shouldFindZeroCountries() {
        Country[] countries = restTemplate.getForObject("/api/country", Country[].class);
        assertEquals(0, countries.length);
    }

    @Test
    void shouldFindOneCountry() {
        Country country = Country.builder()
                .countryName("SomeState")
                .countryArea(1.0)
                .currency("Currency")
                .build();

        ResponseEntity<Country> response =
                restTemplate.exchange("/api/country", HttpMethod.POST, new HttpEntity<>(country), Country.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Country[] countries = restTemplate.getForObject("/api/country", Country[].class);
        assertEquals(1, countries.length);
    }

    @Test
    void shouldFindTenCountries() {
        Country country = Country.builder()
                .countryName("SomeState")
                .countryArea(1.0)
                .currency("Currency")
                .build();

        for (int i = 0; i < 10; i++) {
            country.setCountryName(country.getCountryName() + i);
            ResponseEntity<Country> response =
                    restTemplate.exchange("/api/country", HttpMethod.POST, new HttpEntity<>(country), Country.class);
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
        }

        Country[] countries = restTemplate.getForObject("/api/country", Country[].class);
        assertEquals(10, countries.length);
    }

    @Test
    void shouldGetBadRequest_forCreatingWithAllInvalidFields() {
        Country country = Country.builder()
                .countryName("")
                .countryArea(-1.0)
                .currency("")
                .build();

        ResponseEntity<Country> response =
                restTemplate.exchange("/api/country", HttpMethod.POST, new HttpEntity<>(country), Country.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldGetBadRequest_forCreatingWithOneInvalidFields() {
        Country country = Country.builder()
                .countryName("SomeState")
                .countryArea(1.0)
                .currency("")
                .build();

        ResponseEntity<Country> response =
                restTemplate.exchange("/api/country", HttpMethod.POST, new HttpEntity<>(country), Country.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldCreateValidCountry() {
        Country country = Country.builder()
                .countryName("SomeState")
                .countryArea(1.0)
                .currency("Currency")
                .build();

        ResponseEntity<Country> response =
                restTemplate.exchange("/api/country", HttpMethod.POST, new HttpEntity<>(country), Country.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(Objects.requireNonNull(response.getBody()).getId());
        assertEquals(country.getCountryName(), Objects.requireNonNull(response.getBody()).getCountryName());
        assertEquals(country.getCountryArea(), Objects.requireNonNull(response.getBody()).getCountryArea());
        assertEquals(country.getCurrency(), Objects.requireNonNull(response.getBody()).getCurrency());
    }

    @Test
    void shouldGetBadRequest_forCreatingDuplicateName() {
        Country country = Country.builder()
                .countryName("SomeState")
                .countryArea(1.0)
                .currency("Currency")
                .build();

        ResponseEntity<Country> response =
                restTemplate.exchange("/api/country", HttpMethod.POST, new HttpEntity<>(country), Country.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        response = restTemplate.exchange("/api/country", HttpMethod.POST, new HttpEntity<>(country), Country.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldGetNotFound_forUpdating_WithoutEntityInDB() {
        Country country = Country.builder()
                .id(1L)
                .countryName("SomeState")
                .countryArea(1.0)
                .currency("Currency")
                .build();

        ResponseEntity<Country> response =
                restTemplate.exchange("/api/country/" + country.getId(),
                        HttpMethod.PUT, new HttpEntity<>(country), Country.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldGetBadRequest_forUpdating_WithAllInvalidFields() {
        Country country = Country.builder()
                .countryName("SomeState")
                .countryArea(1.0)
                .currency("Currency")
                .build();

        ResponseEntity<Country> response =
                restTemplate.exchange("/api/country", HttpMethod.POST, new HttpEntity<>(country), Country.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        country.setId(Objects.requireNonNull(response.getBody()).getId());
        country.setCountryName("");
        country.setCountryArea(-1.0);
        country.setCurrency("");

        response = restTemplate.exchange("/api/country/" + country.getId(),
                HttpMethod.PUT, new HttpEntity<>(country), Country.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldGetBadRequest_forUpdating_WithOneInvalidField() {
        Country country = Country.builder()
                .countryName("SomeState")
                .countryArea(1.0)
                .currency("Currency")
                .build();

        ResponseEntity<Country> response =
                restTemplate.exchange("/api/country", HttpMethod.POST, new HttpEntity<>(country), Country.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        country.setId(Objects.requireNonNull(response.getBody()).getId());
        country.setCountryName("");

        response = restTemplate.exchange("/api/country/" + country.getId(),
                HttpMethod.PUT, new HttpEntity<>(country), Country.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldSuccessfullyUpdate() {
        Country country = Country.builder()
                .countryName("SomeState")
                .countryArea(1.0)
                .currency("Currency")
                .build();

        ResponseEntity<Country> response =
                restTemplate.exchange("/api/country", HttpMethod.POST, new HttpEntity<>(country), Country.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        country.setId(Objects.requireNonNull(response.getBody()).getId());
        country.setCountryName(country.getCountryName() + 1);
        country.setCountryArea(country.getCountryArea() + 1);
        country.setCurrency(country.getCurrency() + 1);

        response = restTemplate.exchange("/api/country/" + country.getId(),
                HttpMethod.PUT, new HttpEntity<>(country), Country.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(country.getCountryName(), Objects.requireNonNull(response.getBody()).getCountryName());
        assertEquals(country.getCountryArea(), Objects.requireNonNull(response.getBody()).getCountryArea());
        assertEquals(country.getCurrency(), Objects.requireNonNull(response.getBody()).getCurrency());
    }

    @Test
    void shouldReturnBadRequest_ForDuplicates() {
        Country country1 = Country.builder()
                .countryName("SomeState")
                .countryArea(1.0)
                .currency("Currency")
                .build();

        Country country2 = Country.builder()
                .countryName("SomeState2")
                .countryArea(2.0)
                .currency("Currency2")
                .build();

        ResponseEntity<Country> response =
                restTemplate.exchange("/api/country", HttpMethod.POST, new HttpEntity<>(country1), Country.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Long savedId = Objects.requireNonNull(response.getBody()).getId();

        response = restTemplate.exchange("/api/country", HttpMethod.POST, new HttpEntity<>(country2), Country.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        response = restTemplate.exchange("/api/country/" + savedId,
                HttpMethod.PUT, new HttpEntity<>(country2), Country.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deleteNothing_ReturnsOK(){
        ResponseEntity<Void> response =
            restTemplate.exchange("/api/country/1", HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteValidId_ReturnsOK(){
        Country country = Country.builder()
                .countryName("SomeState")
                .countryArea(1.0)
                .currency("Currency")
                .build();

        ResponseEntity<Country> response =
                restTemplate.exchange("/api/country", HttpMethod.POST, new HttpEntity<>(country), Country.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        ResponseEntity<Void> responseVoid =
                restTemplate.exchange("/api/country/" + Objects.requireNonNull(response.getBody()).getId(),
                        HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.OK, responseVoid.getStatusCode());

        Country[] countries = restTemplate.getForObject("/api/country", Country[].class);
        assertEquals(0, countries.length);
    }

    @AfterEach
    public void cleanUp() {
        List<Country> countries = countryRepository.findAll();
        if(!countries.isEmpty()) {
            countryRepository.deleteAll(countries);
        }
    }
}