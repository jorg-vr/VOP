package model.insurance;

/**
 * Created by jorg on 4/12/17.
 */
public class SpecialCondition {
    private String referenceCode;
    private String Title;
    private String text;

    public SpecialCondition() {
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
