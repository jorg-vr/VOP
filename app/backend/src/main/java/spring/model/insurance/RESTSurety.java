package spring.model.insurance;

import controller.ControllerManager;
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

import static util.MyProperties.*;
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

    private List<SpecialConditionItem> specialConditions;

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
        this.specialConditions = new ArrayList<>();
        for (SpecialCondition specialCondition: surety.getSpecialConditions()) {
            this.specialConditions.add(new SpecialConditionItem(specialCondition));
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

        SpecialConditionController controller = manager.getSpecialConditionController();
        Map<String, String> violations = new HashMap<>();
        List<SpecialCondition> conditions = new ArrayList<>();
        for (SpecialConditionItem item: specialConditions) {
            try {
                SpecialCondition condition = controller.get(toUUID(item.getId()));
                conditions.add(condition);
            } catch (ObjectNotFoundException e) {
                violations.put("specialConditions", ErrorCode.NOT_FOUND + "");
            }
        }
        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }
        surety.setSpecialConditions(conditions);
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

    public List<SpecialConditionItem> getSpecialConditions() {
        return specialConditions;
    }

    public void setSpecialConditions(List<SpecialConditionItem> specialConditions) {
        this.specialConditions = specialConditions;
    }

    public static class SpecialConditionItem {

        private String id;
        private String title;
        private String referenceCode;
        private String url;

        public SpecialConditionItem() {
        }

        public SpecialConditionItem(SpecialCondition specialCondition) {
            this.id = UUIDToNumberString(specialCondition.getUuid());
            this.title = specialCondition.getTitle();
            this.referenceCode = specialCondition.getReferenceCode();
        }

        public String getUrl() {
            return getProperty(PATH_SPECIAL_CONDITIONS) + "/" + id;
        }

        public void setUrl(String url) {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getReferenceCode() {
            return referenceCode;
        }

        public void setReferenceCode(String referenceCode) {
            this.referenceCode = referenceCode;
        }
    }
}
