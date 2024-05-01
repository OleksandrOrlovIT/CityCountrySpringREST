package orlov.oleksandr.programming.citycountryspringrest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import orlov.oleksandr.programming.citycountryspringrest.controller.dto.CityDTO;
import orlov.oleksandr.programming.citycountryspringrest.controller.dto.mapper.CityMapper;
import orlov.oleksandr.programming.citycountryspringrest.model.City;
import orlov.oleksandr.programming.citycountryspringrest.model.Country;
import orlov.oleksandr.programming.citycountryspringrest.service.interfaces.CityService;
import orlov.oleksandr.programming.citycountryspringrest.service.interfaces.CountryService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/city")
public class CityController {

    private CityService cityService;
    private CountryService countryService;
    private CityMapper cityMapper;

    @PostMapping
    public ResponseEntity<City> createCity(@RequestBody @Validated CityDTO cityDTO) {
        Country country = countryService.findById(cityDTO.getCountryId());

        City city = cityMapper.toCity(cityDTO, country);

        return new ResponseEntity<>(cityService.create(city), HttpStatus.CREATED);
    }

    @GetMapping("/{cityId}")
    public City getCity(@PathVariable Long cityId) {
        return cityService.findById(cityId);
    }

    @PutMapping("/{cityId}")
    public City updateCity(@PathVariable Long cityId, @RequestBody @Validated CityDTO cityDTO) {
        Country country = countryService.findById(cityDTO.getCountryId());

        City city = cityMapper.toCity(cityDTO, country);
        city.setId(cityId);

        return cityService.update(city);
    }

    @DeleteMapping("/{cityId}")
    public void deleteCity(@PathVariable Long cityId) {
        cityService.deleteById(cityId);
    }
}
