/**
 * Created by Jarre on 14-4-2017.
 */

import Vue from 'vue'

export default {
    CUSTOMER: {
        name: 'customer',
        translation: function(){
            return Vue.t('clientTypes.customer')
        },
        value: 'Customer'
    },
    INSURANCE_COMPANY: {
        name: 'insurance company',
        translation: function(){
            return Vue.t('clientTypes.insuranceCompany')
        },
        value: 'InsuranceCompany'
    }
}