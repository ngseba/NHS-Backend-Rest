package ro.iteahome.nhs.backend.annotations;

import ro.iteahome.nhs.backend.model.nhs.reference.InstitutionType;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {ValidOptionValidator.class}
)
public @interface ValidOption {
    Class<? extends Enum<?>> enumOption();
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
