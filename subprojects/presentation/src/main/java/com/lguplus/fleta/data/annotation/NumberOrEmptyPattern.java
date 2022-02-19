package com.lguplus.fleta.data.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.regex.Pattern;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = NumberOrEmptyPattern.NumberPatternValidator.class)
public @interface NumberOrEmptyPattern {

    String message() default "숫자만 사용할수 있습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class NumberPatternValidator implements ConstraintValidator<NumberOrEmptyPattern, String> {

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null || value.length() == 0) {
                return true;
            }

            Pattern pattern = Pattern.compile("^[0-9]*$");
            return pattern.matcher(value).find();
        }
    }
}
