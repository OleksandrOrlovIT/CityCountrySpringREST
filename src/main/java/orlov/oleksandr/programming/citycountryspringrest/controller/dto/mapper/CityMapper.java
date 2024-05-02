package orlov.oleksandr.programming.citycountryspringrest.controller.dto.mapper;

import org.springframework.stereotype.Component;
import orlov.oleksandr.programming.citycountryspringrest.controller.dto.request.CityDTO;
import orlov.oleksandr.programming.citycountryspringrest.controller.dto.response.CityCRUDResponse;
import orlov.oleksandr.programming.citycountryspringrest.controller.dto.response.CityFilteredResponse;
import orlov.oleksandr.programming.citycountryspringrest.controller.dto.response.CityResponse;
import orlov.oleksandr.programming.citycountryspringrest.model.City;
import orlov.oleksandr.programming.citycountryspringrest.model.Country;

import java.util.ArrayList;
import java.util.List;

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

    public CityFilteredResponse toCityFilteredResponse(City city) {
        CityFilteredResponse cityResponse = new CityFilteredResponse();
        cityResponse.setId(city.getId());
        cityResponse.setCityName(city.getCityName());
        cityResponse.setCountryId(city.getCountry().getId());
        cityResponse.setCityPopulation(city.getCityPopulation());
        cityResponse.setCityArea(city.getCityArea());
        cityResponse.setFoundedAt(city.getFoundedAt());
        cityResponse.setLanguages(convertListLanguages(city.getLanguages()));

        return cityResponse;
    }

    public CityResponse toCityResponse(City city){
        CityResponse cityResponse = new CityResponse();
        cityResponse.setId(city.getId());
        cityResponse.setCityName(city.getCityName());
        cityResponse.setCountryId(city.getCountry().getId());
        cityResponse.setCityPopulation(city.getCityPopulation());
        cityResponse.setCityArea(city.getCityArea());
        cityResponse.setFoundedAt(city.getFoundedAt());
        cityResponse.setLanguages(convertListLanguages(city.getLanguages()));

        return cityResponse;
    }

    public CityResponse toCityResponseCSVFormat(City city){
        CityResponse cityResponse = new CityResponse();
        cityResponse.setId(city.getId());
        cityResponse.setCityName(city.getCityName());
        cityResponse.setCountryId(city.getCountry().getId());
        cityResponse.setCityPopulation(city.getCityPopulation());
        cityResponse.setCityArea(city.getCityArea());
        cityResponse.setFoundedAt(city.getFoundedAt());
        cityResponse.setLanguages(convertListLanguagesForCSV(city.getLanguages()));

        return cityResponse;
    }

    public CityCRUDResponse toCityCRUDResponse(City city){
        CityCRUDResponse cityResponse = new CityCRUDResponse();
        cityResponse.setId(city.getId());
        cityResponse.setCityName(city.getCityName());
        cityResponse.setCountry(city.getCountry());
        cityResponse.setCityPopulation(city.getCityPopulation());
        cityResponse.setCityArea(city.getCityArea());
        cityResponse.setFoundedAt(city.getFoundedAt());
        cityResponse.setLanguages(convertListLanguages(city.getLanguages()));

        return cityResponse;
    }

    public List<CityFilteredResponse> toCityFilteredResponseList(List<City> cities) {
        List<CityFilteredResponse> cityFilteredResponses = new ArrayList<>();

        for(City city : cities) {
            cityFilteredResponses.add(toCityFilteredResponse(city));
        }

        return cityFilteredResponses;
    }

    public List<CityResponse> toCityResponseList(List<City> cities) {
        List<CityResponse> cityResponses = new ArrayList<>();

        for(City city : cities) {
            cityResponses.add(toCityResponse(city));
        }

        return cityResponses;
    }

    public List<CityResponse> toCityResponseListFormatCSV(List<City> cities) {
        List<CityResponse> cityResponses = new ArrayList<>();

        for(City city : cities) {
            cityResponses.add(toCityResponseCSVFormat(city));
        }

        return cityResponses;
    }

    public String convertListLanguages(List<String> languages){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < languages.size() - 1; i++){
            sb.append(languages.get(i)).append(", ");
        }
        sb.append(languages.get(languages.size() - 1));

        return sb.toString();
    }

    public String convertListLanguagesForCSV(List<String> languages){
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        for(int i = 0; i < languages.size() - 1; i++){
            sb.append(languages.get(i)).append(", ");
        }
        sb.append(languages.get(languages.size() - 1));
        sb.append("\"");

        return sb.toString();
    }
}
