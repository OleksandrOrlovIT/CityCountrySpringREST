package orlov.oleksandr.programming.citycountryspringrest.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import orlov.oleksandr.programming.citycountryspringrest.model.City;

import java.util.List;
import java.util.Map;

public interface CityService extends CrudService<City, Long>{
    boolean existsByAllFieldsExceptId(City city);
    Page<City> findPageCitiesByFilters(Map<String, Object> filterParams, Pageable pageable);
    List<City> findCitiesByFilters(Map<String, Object> filterParams);
}
