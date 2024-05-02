package orlov.oleksandr.programming.citycountryspringrest.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import orlov.oleksandr.programming.citycountryspringrest.model.Country;
import orlov.oleksandr.programming.citycountryspringrest.repository.CountryRepository;
import orlov.oleksandr.programming.citycountryspringrest.service.interfaces.CountryService;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public Country findById(Long id) {
        Objects.requireNonNull(id, "Country's id must not be null");

        return countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country with id " + id + " not found"));
    }

    @Override
    public Country create(Country country) {
        checkCountryAndNameForNull(country);

        if (country.getId() != null) {
            throw new IllegalArgumentException("Country id must be null");
        }

        if (existsByName(country.getCountryName())) {
            throw new IllegalArgumentException("Country with name = " + country.getCountryName() + " already exists");
        }

        return countryRepository.save(country);
    }

    @Override
    public Country update(Country country) {
        checkCountryAndNameForNull(country);

        Country foundCountry = findById(country.getId());

        if(!foundCountry.getCountryName().equals(country.getCountryName()) && existsByName(country.getCountryName())){
            throw new IllegalArgumentException("Country with name= " + country.getCountryName() + " already exists");
        }

        return countryRepository.save(country);
    }

    @Override
    public void deleteById(Long id) {
        countryRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String countryName) {
        return countryRepository.existsByCountryName(countryName);
    }

    private void checkCountryAndNameForNull(Country country) {
        Objects.requireNonNull(country, "Country must not be null");
        Objects.requireNonNull(country.getCountryName(), "Country name must not be null");
    }
}
