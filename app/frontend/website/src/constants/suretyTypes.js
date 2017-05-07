import Vue from 'vue'

export default {
    CIVIL_LIABILITY: {
        name: 'CIVIL_LIABILITY',
        translation: function(){
            return Vue.t('suretyTypes.CIVIL_LIABILITY')
        }
    },
    OMNIUM_FULL: {
        name: 'OMNIUM_FULL',
        translation: function(){
            return Vue.t('suretyTypes.OMNIUM_FULL')
        }
    },
    OMNIUM_PARTIAL: {
        name: 'OMNIUM_PARTIAL',
        translation: function(){
            return Vue.t('suretyTypes.OMNIUM_PARTIAL')
        }
    },
    LEGAL_AID: {
        name: 'LEGAL_AID',
        translation: function(){
            return Vue.t('suretyTypes.LEGAL_AID')
        }
    },
    TRAVEL_AID: {
        name: 'TRAVEL_AID',
        translation: function(){
            return Vue.t('suretyTypes.TRAVEL_AID')
        }
    },
    SAFETY: {
        name: 'SAFETY',
        translation: function(){
            return Vue.t('suretyTypes.SAFETY')
        }
    }


}