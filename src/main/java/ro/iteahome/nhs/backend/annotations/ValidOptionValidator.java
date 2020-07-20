package ro.iteahome.nhs.backend.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ValidOptionValidator implements ConstraintValidator<ValidOption, String> {

    Class<? extends Enum<?>> enumOptions;

    @Override
    public void initialize(ValidOption validOptionAnnotation) {

        this.enumOptions = validOptionAnnotation.enumOption();

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(value.split(","))
                .allMatch(type -> Arrays.toString(enumOptions.getEnumConstants()).contains(type));
    }
}
