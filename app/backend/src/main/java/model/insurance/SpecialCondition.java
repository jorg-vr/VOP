package model.insurance;

import model.history.EditableObject;

import java.util.UUID;

/**
 * Class representing special conditions which can apply for a Surety
 * Created by jorg on 4/12/17.
 */
public class SpecialCondition implements EditableObject {
    /**
     * The unique id
     */
    private UUID uuid;

    /**
     * The reference code
     */
    private String referenceCode;

    /**
     * The title
     */
    private String Title;

    /**
     * The description of the condition
     */
    private String text;

    /**
     * Default constructor
     */
    public SpecialCondition() {
    }

    /**
     * Constructor
     * @param title the title
     * @param text the description
     * @param referenceCode the code
     */
    public SpecialCondition(String title, String text, String referenceCode) {
        this.referenceCode = referenceCode;
        this.text = text;
        Title = title;
    }

    /**
     * Gets the code
     * @return the code
     */
    public String getReferenceCode() {
        return referenceCode;
    }

    /**
     * Sets the code
     * @param referenceCode the code
     */
    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    /**
     * Gets the title
     * @return the title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * Sets the title
     * @param title the title
     */
    public void setTitle(String title) {
        Title = title;
    }

    /**
     * Gets the description
     * @return the description
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the description
     * @param text the description
     */
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof SpecialCondition)) return false;

        SpecialCondition that = (SpecialCondition) o;

        return getUuid()!=null&&getUuid().equals(that.getUuid());

    }

    @Override
    public int hashCode() {
        if(getUuid()!=null){return getUuid().hashCode();}
        return super.hashCode();
    }

    /**
     * Gets the uuid
     * @return the id
     */
    @Override
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Sets the id
     * @param uuid the id
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Copies the object
     * @return the copy
     */
    @Override
    public EditableObject copy() {
        SpecialCondition specialCondition = new SpecialCondition();
        specialCondition.setUuid(getUuid());
        specialCondition.setReferenceCode(getReferenceCode());
        specialCondition.setText(getText());
        specialCondition.setTitle(getTitle());
        return specialCondition;
    }
}
