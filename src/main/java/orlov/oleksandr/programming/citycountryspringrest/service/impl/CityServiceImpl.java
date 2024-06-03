package orlov.oleksandr.programming.citycountryspringrest.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import orlov.oleksandr.programming.citycountryspringrest.controller.dto.mapper.CityMapper;
import orlov.oleksandr.programming.citycountryspringrest.model.City;
import orlov.oleksandr.programming.citycountryspringrest.repository.CityRepository;
import orlov.oleksandr.programming.citycountryspringrest.service.interfaces.CityService;
import orlov.oleksandr.programming.citycountryspringrest.service.messages.MessageSender;
import orlov.oleksandr.programming.citycountryspringrest.service.specifications.CitySpecifications;

import java.util.*;

@AllArgsConstructor
@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private final MessageSender messageSender;

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public City findById(Long id) {
        Objects.requireNonNull(id, "City's id must not be null");

        return cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("City with id " + id + " not found"));
    }

    @Override
    public City create(City city) {
        Objects.requireNonNull(city, "City must not be null");

        if (city.getId() != null) {
            throw new IllegalArgumentException("City id must be null");
        }

        if(existsByAllFieldsExceptId(city)){
            throw new IllegalArgumentException("City already exists");
        }

        City savedCity = cityRepository.save(city);

        if(savedCity.getId() != null){
            messageSender.sendMessageWithEmail("Successfully created city.", savedCity.toString());
        }

        return savedCity;
    }

    @Override
    public City update(City city) {
        Objects.requireNonNull(city, "Country must not be null");
        Objects.requireNonNull(city.getId(), "Country's id must not be null");

        findById(city.getId());

        if(existsByAllFieldsExceptId(city)){
            throw new IllegalArgumentException("City already exists");
        }

        return cityRepository.save(city);
    }

    @Override
    public void deleteById(Long id) {
        cityRepository.deleteById(id);
    }

    @Override
    public boolean existsByAllFieldsExceptId(City city) {
        Map<String, String> filterParams = new HashMap<>();

        filterParams.put("cityName", city.getCityName());
        filterParams.put("countryId", city.getCountry().getId().toString());
        filterParams.put("cityPopulation", city.getCityPopulation().toString());
        filterParams.put("cityArea", city.getCityArea().toString());
        filterParams.put("foundedAt", city.getFoundedAt().toString());
        filterParams.put("languages", cityMapper.convertListLanguages(city.getLanguages()));

        Specification<City> spec = CitySpecifications.buildSpecifications(filterParams);

        return !cityRepository.findAll(spec).isEmpty();
    }

    @Override
    public Page<City> findPageCitiesByFilters(Map<String, String> filterParams, Pageable pageable) {
        Specification<City> spec = CitySpecifications.buildSpecifications(filterParams);

        return cityRepository.findAll(spec, pageable);
    }

    @Override
    public List<City> findCitiesByFilters(Map<String, String> filterParams) {
        Specification<City> spec = CitySpecifications.buildSpecifications(filterParams);

        return cityRepository.findAll(spec);
    }
}
