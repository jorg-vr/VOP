package dao.database.util.unique;

import dao.database.ProductionProvider;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Created by sam on 4/18/17.
 */
public class UniqueKeyValidator implements ConstraintValidator<UniqueKey, Serializable>, EntityManagerAwareValidator {

    //private EntityManager entityManager;
    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    private String[] columnNames;

    @Override
    public void initialize(UniqueKey constraintAnnotation) {
        this.columnNames = constraintAnnotation.columnNames();

    }

    @Override
    public boolean isValid(Serializable target, ConstraintValidatorContext context) {
        Class<?> entityClass = target.getClass();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(entityClass);

        Root<?> root = criteriaQuery.from(entityClass);

        boolean finalValid = true;
        try {
            for (String propertyName : columnNames) {
                PropertyDescriptor desc = new PropertyDescriptor(propertyName, entityClass);
                Method readMethod = desc.getReadMethod();
                Object propertyValue = readMethod.invoke(target);
                criteriaQuery.where(criteriaBuilder.equal(root.get(propertyName), propertyValue));


                boolean valid =  session.createQuery(criteriaQuery).getResultList().size() == 0;
                if ( !valid ) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate("should be unique")
                            .addPropertyNode( propertyName ).addConstraintViolation();
                    finalValid = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return finalValid;
    }

}