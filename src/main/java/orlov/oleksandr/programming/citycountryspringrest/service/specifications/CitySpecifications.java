package orlov.oleksandr.programming.citycountryspringrest.service.specifications;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import orlov.oleksandr.programming.citycountryspringrest.model.City;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CitySpecifications {

    public static Specification<City> cityIdEquals(Integer cityId) {
        return (root, query, cb) -> cb.equal(root.get("id"), cityId);
    }

    public static Specification<City> cityNameEquals(String cityName) {
        return (root, query, cb) -> cb.equal(root.get("cityName"), cityName);
    }

    public static Specification<City> countryIdEquals(Integer countryId) {
        return (root, query, cb) -> cb.equal(root.get("country").get("id"), countryId);
    }

    public static Specification<City> cityPopulationEquals(Integer population) {
        return (root, query, cb) -> cb.equal(root.get("cityPopulation"), population);
    }

    public static Specification<City> cityAreaEquals(Double area) {
        return (root, query, cb) -> cb.equal(root.get("cityArea"), area);
    }

    public static Specification<City> foundedAtEquals(Year year) {
        return (root, query, cb) -> cb.equal(root.get("foundedAt"), year);
    }

    public static Specification<City> languagesEquals(List<String> languages) {
        return (root, query, cb) -> {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<City> subRoot = subquery.from(City.class);
            subquery.select(subRoot.get("id"));

            List<Predicate> languagePredicates = new ArrayList<>();

            for (String language : languages) {
                languagePredicates.add(cb.isMember(language, subRoot.get("languages")));
            }

            Predicate allLanguagesPredicate = cb.and(languagePredicates.toArray(new Predicate[0]));

            subquery.where(allLanguagesPredicate);

            return cb.in(root.get("id")).value(subquery);
        };
    }

    public static Specification<City> buildSpecifications(Map<String, Object> filterParams) {
        Specification<City> spec = Specification.where(null);

        if (filterParams.containsKey("id")) {
            Integer cityId = (Integer) filterParams.get("id");
            spec = spec.and(cityIdEquals(cityId));
        }

        if (filterParams.containsKey("cityName")) {
            String cityName = (String) filterParams.get("cityName");
            spec = spec.and(cityNameEquals(cityName));
        }

        if (filterParams.containsKey("countryId")) {
            Integer countryId = (Integer) filterParams.get("countryId");
            spec = spec.and(countryIdEquals(countryId));
        }

        if (filterParams.containsKey("cityPopulation")) {
            Integer cityPopulation = (Integer) filterParams.get("cityPopulation");
            spec = spec.and(cityPopulationEquals(cityPopulation));
        }

        if (filterParams.containsKey("cityArea")) {
            Double cityArea = (Double) filterParams.get("cityArea");
            spec = spec.and(cityAreaEquals(cityArea));
        }

        if (filterParams.containsKey("foundedAt")) {
            Year foundedAt =  Year.parse(String.valueOf(filterParams.get("foundedAt")));
            spec = spec.and(foundedAtEquals(foundedAt));
        }

        if(filterParams.containsKey("languages")){
            List<String> languages = Arrays.asList(filterParams.get("languages").toString().split(",\\s*"));
            spec = spec.and(languagesEquals(languages));
        }

        return spec;
    }
}