package dao.database.util.unique;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to check uniqueness before persisting in database
 * Created by sam on 4/18/17.
 */
@Constraint(validatedBy={UniqueKeyValidator.class})
@Target({ElementType.TYPE})
@Retention(RUNTIME)
public @interface UniqueKey {

    String[] columnNames();

    String message() default "{UniqueKey.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ ElementType.TYPE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        UniqueKey[] value();
    }
}
