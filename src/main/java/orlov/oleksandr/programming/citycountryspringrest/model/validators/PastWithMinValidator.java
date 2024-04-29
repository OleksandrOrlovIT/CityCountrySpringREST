package orlov.oleksandr.programming.citycountryspringrest.model.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import orlov.oleksandr.programming.citycountryspringrest.model.validators.annotaions.PastWithMin;

import java.time.Year;

public class PastWithMinValidator implements ConstraintValidator<PastWithMin, Year> {

    private int minBceYear;

    @Override
    public void initialize(PastWithMin constraintAnnotation) {
        this.minBceYear = constraintAnnotation.minBceYear();
    }

    @Override
    public boolean isValid(Year value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        int currentYear = Year.now().getValue();

        return value.getValue() < currentYear && value.getValue() >= minBceYear;
    }
}