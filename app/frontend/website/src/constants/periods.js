import Vue from 'vue'

export default [
    {
        period: 1,
        translation: () => Vue.t('period.monthly')
    },
    {
        period: 4,
        translation: () => Vue.t('period.quarterly')
    },
    {
        period: 6,
        translation: () => Vue.t('period.half_yearly')
    },
    {
        period: 12,
        translation: () => Vue.t('period.yearly')
    }


]