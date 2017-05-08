export default {
    messages: {
        nl: (field) => `The ${field} is not valid. It must contain exactly 17 characters, e.g. LJCPCBLCX11000237.`,
        en: (field) => `Het ${field} is niet geldig. Het moet 17 karakters bevatten, e.g. LJCPCBLCX11000237.`,
    },
    validate: (value) => {
        let pattern = new RegExp("^[A-HJ-NPR-Z0-9]{9}[A-HJ-NPR-TV-Y1-9][A-HJ-NPR-Z0-9]{7}$");
        return pattern.test(value)
    }
}