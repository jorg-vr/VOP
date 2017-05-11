export default {
    messages: {
        en: (field, [length]) => `The ${field} field must exactly contain ${length} characters.`,
        nl: (field, [length]) => `${field} moet exact ${length} tekens bevatten.`
    },
    validate(value, [length]) {
        if (value === undefined || value === null) {
            return false;
        }
        return String(value).length == length;
    }
};