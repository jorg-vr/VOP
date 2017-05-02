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
        type: 'CUSTOMER'
    },
    LEASING_COMPANY: {
        name: 'leasing company',
        translation: function(){
            return Vue.t('clientTypes.leasingCompany')
        },
        type: 'LEASING_COMPANY'
    },
    INSURANCE_COMPANY: {
        name: 'insurance company',
        translation: function(){
            return Vue.t('clientTypes.insuranceCompany')
        },
        type: 'INSURANCE_COMPANY'
    }
}