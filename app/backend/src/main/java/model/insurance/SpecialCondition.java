package model.insurance;

import model.history.EditableObject;

import java.util.UUID;

/**
 * Created by jorg on 4/12/17.
 */
public class SpecialCondition implements EditableObject{
    private UUID uuid;
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof SpecialCondition)) return false;

        SpecialCondition that = (SpecialCondition) o;

        return getUuid().equals(that.getUuid());

    }

    @Override
    public int hashCode() {
        return getUuid().hashCode();
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public EditableObject copy() {
        return null;
    }
}
