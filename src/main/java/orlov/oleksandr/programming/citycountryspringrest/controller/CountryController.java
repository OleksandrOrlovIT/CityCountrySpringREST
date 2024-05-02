package orlov.oleksandr.programming.citycountryspringrest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import orlov.oleksandr.programming.citycountryspringrest.model.Country;
import orlov.oleksandr.programming.citycountryspringrest.service.interfaces.CountryService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/country")
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public List<Country> getAllCountries() {
        return countryService.findAll();
    }

    @PostMapping
    public ResponseEntity<Country> createCountry(@RequestBody @Validated Country country) {
        return new ResponseEntity<>(countryService.create(country), HttpStatus.CREATED);
    }

    @PutMapping("/{countryId}")
    public Country updateCountry(@PathVariable Long countryId, @RequestBody @Validated Country country) {
        country.setId(countryId);
        return countryService.update(country);
    }

    @DeleteMapping("/{countryId}")
    public void deleteCountry(@PathVariable Long countryId) {
        countryService.deleteById(countryId);
    }
}

