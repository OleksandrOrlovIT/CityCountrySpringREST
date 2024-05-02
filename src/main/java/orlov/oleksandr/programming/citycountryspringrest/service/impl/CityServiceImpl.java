package orlov.oleksandr.programming.citycountryspringrest.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import orlov.oleksandr.programming.citycountryspringrest.model.City;
import orlov.oleksandr.programming.citycountryspringrest.repository.CityRepository;
import orlov.oleksandr.programming.citycountryspringrest.service.interfaces.CityService;
import orlov.oleksandr.programming.citycountryspringrest.service.specifications.CitySpecifications;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

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

        return cityRepository.save(city);
    }

    @Override
    public City update(City city) {
        Objects.requireNonNull(city, "Country must not be null");
        Objects.requireNonNull(city.getId(), "Country's id must not be null");

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
        List<City> cities = cityRepository.findByCityName(city.getCityName());

        for(City item : cities) {
            if(allFieldsEqualsExceptForId(city, item)){
                return true;
            }
        }

        return false;
    }

    private boolean allFieldsEqualsExceptForId(City city1, City city2){
        return  Objects.equals(city1.getCityName(), city2.getCityName())
                && Objects.equals(city1.getCountry(), city2.getCountry())
                && Objects.equals(city1.getCityPopulation(), city2.getCityPopulation())
                && Objects.equals(city1.getCityArea(), city2.getCityArea())
                && Objects.equals(city1.getFoundedAt(), city2.getFoundedAt())
                && Objects.equals(city1.getLanguages(), city2.getLanguages());
    }

    @Override
    public Page<City> findPageCitiesByFilters(Map<String, Object> filterParams, Pageable pageable) {
        Specification<City> spec = CitySpecifications.buildSpecifications(filterParams);

        return cityRepository.findAll(spec, pageable);
    }

    @Override
    public List<City> findCitiesByFilters(Map<String, Object> filterParams) {
        Specification<City> spec = CitySpecifications.buildSpecifications(filterParams);

        return cityRepository.findAll(spec);
    }
}
