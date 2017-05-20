import Vue from 'vue'

export default [
    {
        period: 1,
        periodSelect: "1", //Fixes select form
        translation: () => Vue.t('period.monthly')
    },
    {
        period: 3,
        periodSelect: "3", //Fixes select form
        translation: () => Vue.t('period.quarterly')
    },
    {
        period: 6,
        periodSelect: "6", //Fixes select form
        translation: () => Vue.t('period.half_yearly')
    },
    {
        period: 12,
        periodSelect: "12", //Fixes select form
        translation: () => Vue.t('period.yearly')
    }


]