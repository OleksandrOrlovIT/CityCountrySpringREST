package orlov.oleksandr.programming.citycountryspringrest.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import orlov.oleksandr.programming.citycountryspringrest.model.City;
import orlov.oleksandr.programming.citycountryspringrest.repository.CityRepository;
import orlov.oleksandr.programming.citycountryspringrest.service.interfaces.CityService;

import java.util.List;
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

        return cityRepository.save(city);
    }

    @Override
    public City update(City city) {
        Objects.requireNonNull(city, "Country must not be null");
        Objects.requireNonNull(city.getId(), "Country's id must not be null");

        return cityRepository.save(city);
    }

    @Override
    public void deleteById(Long id) {
        cityRepository.deleteById(id);
    }
}
