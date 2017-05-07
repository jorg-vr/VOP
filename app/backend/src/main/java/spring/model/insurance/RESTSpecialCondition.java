package spring.model.insurance;

import controller.ControllerManager;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import model.insurance.SpecialCondition;
import spring.model.RESTAbstractModel;

import static util.MyProperties.*;
import static util.UUIDUtil.toUUID;

/**
 * Created by Billie Devolder on 7/05/2017.
 */
public class RESTSpecialCondition extends RESTAbstractModel<SpecialCondition> {

    private String referenceCode;
    private String title;
    private String text;

    public RESTSpecialCondition() {

    }

    public RESTSpecialCondition(SpecialCondition specialCondition) {
        super(specialCondition.getUuid(), getProperty(PATH_SPECIAL_CONDITIONS));
        this.referenceCode = specialCondition.getReferenceCode();
        this.title = specialCondition.getTitle();
        this.title = specialCondition.getText();
    }

    @Override
    public SpecialCondition translate(ControllerManager manager) throws UnAuthorizedException, DataAccessException, ConstraintViolationException {
        SpecialCondition specialCondition = new SpecialCondition();
        specialCondition.setUuid(toUUID(getId()));
        specialCondition.setReferenceCode(referenceCode);
        specialCondition.setTitle(title);
        specialCondition.setText(text);
        return specialCondition;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
