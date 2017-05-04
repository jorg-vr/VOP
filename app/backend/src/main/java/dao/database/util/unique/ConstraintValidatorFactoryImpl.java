package dao.database.util.unique;

import dao.database.ProductionProvider;
import org.hibernate.Session;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

/**
 * Created by sam on 4/18/17.
 */
public class ConstraintValidatorFactoryImpl implements ConstraintValidatorFactory {

    private Session session;

    public ConstraintValidatorFactoryImpl(Session session) {
        this.session = session;
    }

    @Override
    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {

        T instance = null;

        try {
            instance = key.newInstance();
        } catch (Exception e) {
            // could not instantiate class
            e.printStackTrace();
        }

        if(EntityManagerAwareValidator.class.isAssignableFrom(key)) {
            EntityManagerAwareValidator validator = (EntityManagerAwareValidator) instance;
            if (validator != null) {
                validator.setSession(session);
            }
        }

        return instance;
    }

    @Override
    public void releaseInstance(ConstraintValidator<?, ?> instance) {
        session.close();
    }
}