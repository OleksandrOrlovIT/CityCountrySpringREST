package orlov.oleksandr.programming.citycountryspringrest.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Year;

@Converter
public class YearConverter implements AttributeConverter<Year, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Year year) {
        return year == null ? null : year.getValue();
    }

    @Override
    public Year convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : Year.of(dbData);
    }
}