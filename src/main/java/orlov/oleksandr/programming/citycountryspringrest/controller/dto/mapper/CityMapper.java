package orlov.oleksandr.programming.citycountryspringrest.controller.dto.mapper;

import org.springframework.stereotype.Component;
import orlov.oleksandr.programming.citycountryspringrest.controller.dto.CityDTO;
import orlov.oleksandr.programming.citycountryspringrest.model.City;
import orlov.oleksandr.programming.citycountryspringrest.model.Country;

@Component
public class CityMapper {

    public City toCity(CityDTO cityDTO, Country country) {
        City city = new City();
        city.setCityName(cityDTO.getCityName());
        city.setCountry(country);
        city.setCityPopulation(cityDTO.getCityPopulation());
        city.setCityArea(cityDTO.getCityArea());
        city.setFoundedAt(cityDTO.getFoundedAt());
        city.setLanguages(cityDTO.getLanguagesList());

        return city;
    }
}
