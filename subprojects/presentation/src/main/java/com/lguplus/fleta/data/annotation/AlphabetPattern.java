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
@Constraint(validatedBy = AlphabetPattern.AlphabetPatternValidator.class)
public @interface AlphabetPattern {

    String message() default "영문자만 사용할수 있습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class AlphabetPatternValidator implements ConstraintValidator<AlphabetPattern, String> {

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null) {
                return false;
            }

            Pattern pattern = Pattern.compile("^[a-zA-Z]*$");
            return pattern.matcher(value).find();
        }
    }
}
