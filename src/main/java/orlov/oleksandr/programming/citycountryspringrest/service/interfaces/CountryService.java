package orlov.oleksandr.programming.citycountryspringrest.service.interfaces;

import orlov.oleksandr.programming.citycountryspringrest.model.Country;

public interface CountryService extends CrudService<Country, Long>{
    boolean existsByName(String countryName);
}
