package model.insurance;

/**
 * Type of surety
 */
public enum SuretyType {
    CIVIL_LIABILITY("Burgelijke aansprakelijkheid"),
    OMNIUM_FULL("Omnium"),
    OMNIUM_PARTIAL("Omnium (half)"),
    LEGAL_AID("Rechtsbijstand"),
    TRAVEL_AID("Reisbijstand"),
    SAFETY("Veiligheid");

    private String dutchTranslation;

    SuretyType(String dutchTranslation) {
        this.dutchTranslation = dutchTranslation;
    }

    public String getDutchTranslation() {
        return dutchTranslation;
    }
}