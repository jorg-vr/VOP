package spring.model.insurance;

import controller.ControllerManager;
import controller.InsuranceCompanyController;
import controller.exceptions.UnAuthorizedException;
import controller.insurance.SpecialConditionController;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import model.insurance.*;
import spring.exceptions.ErrorCode;
import spring.model.RESTAbstractModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.MyProperties.PATH_SURETIES;
import static util.MyProperties.getProperty;
import static util.UUIDUtil.UUIDToNumberString;
import static util.UUIDUtil.toUUID;

/**
 * Created by Billie Devolder on 18/04/2017.
 */
public class RESTSurety extends RESTAbstractModel<Surety> {

    private boolean isFlat;

    // is the minimumPremium when is NonFlatSurety
    private int premium;

    // Only applies to NonFlatSurety
    private double premiumPercentage;

    private SuretyType suretyType;
    private List<RESTSpecialCondition> specialConditions;
    private String insuranceCompany;

    public RESTSurety() {
    }

    public RESTSurety(Surety surety) {
        super(surety.getUuid(), getProperty(PATH_SURETIES));
        if (surety instanceof FlatSurety) {
            FlatSurety flatSurety = (FlatSurety) surety;
            this.isFlat = true;
            this.premium = flatSurety.getPremium();
        } else if (surety instanceof NonFlatSurety) {
            NonFlatSurety nonFlatSurety = (NonFlatSurety) surety;
            this.isFlat = false;
            this.premium = nonFlatSurety.getMinPremium();
            this.premiumPercentage = nonFlatSurety.getPremiumPercentage();
        }
        this.suretyType = surety.getSuretyType();
        this.insuranceCompany = UUIDToNumberString(surety.getInsuranceCompany().getUuid());
        this.specialConditions = new ArrayList<>();
        if (surety.getSpecialConditions() != null) {
            for (SpecialCondition specialCondition : surety.getSpecialConditions()) {
                this.specialConditions.add(new RESTSpecialCondition(specialCondition));
            }
        }
    }

    @Override
    public Surety translate(ControllerManager manager) throws UnAuthorizedException, DataAccessException, ConstraintViolationException {
        Surety surety;
        if (isFlat) {
            FlatSurety flatSurety = new FlatSurety();
            flatSurety.setPremium(premium);
            surety = flatSurety;
        } else {
            NonFlatSurety nonFlatSurety = new NonFlatSurety();
            nonFlatSurety.setMinPremium(premium);
            nonFlatSurety.setPremiumPercentage(premiumPercentage);
            surety = nonFlatSurety;
        }

        Map<String, String> violations = new HashMap<>();
        try {
            SpecialConditionController controller = manager.getSpecialConditionController();

            List<SpecialCondition> conditions = new ArrayList<>();
            for (RESTSpecialCondition item : specialConditions) {
                SpecialCondition condition = controller.get(toUUID(item.getId()));
                conditions.add(condition);
            }
            surety.setSpecialConditions(conditions);
        } catch (ObjectNotFoundException e) {
            violations.put("specialConditions", ErrorCode.NOT_FOUND + "");
        }
        try {
            InsuranceCompanyController controller = manager.getInsuranceCompanyController();
            surety.setInsuranceCompany(controller.get(toUUID(insuranceCompany)));
        } catch (ObjectNotFoundException e) {
            violations.put("insuranceCompany", ErrorCode.NOT_FOUND + "");
        }

        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }
        surety.setSuretyType(getSuretyType());
        surety.setUuid(toUUID(getId()));
        return surety;
    }

    public boolean isFlat() {
        return isFlat;
    }

    public void setFlat(boolean flat) {
        isFlat = flat;
    }

    public int getPremium() {
        return premium;
    }

    public void setPremium(int premium) {
        this.premium = premium;
    }

    public double getPremiumPercentage() {
        return premiumPercentage;
    }

    public void setPremiumPercentage(double premiumPercentage) {
        this.premiumPercentage = premiumPercentage;
    }

    public SuretyType getSuretyType() {
        return suretyType;
    }

    public void setSuretyType(SuretyType suretyType) {
        this.suretyType = suretyType;
    }

    public List<RESTSpecialCondition> getSpecialConditions() {
        return specialConditions;
    }

    public void setSpecialConditions(List<RESTSpecialCondition> specialConditions) {
        this.specialConditions = specialConditions;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }
}
