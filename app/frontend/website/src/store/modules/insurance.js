import * as locations from '../../constants/locations'
import RequestHandler from '../../api/requestHandler'
import Vue from 'vue'
import * as utils from '../../utils/utils'

export default {
    state: {
        contractId: '',
        insuranceCompanyId: '',
        selectedConditions: [],

    },
    getters: {
        selectedConditions(state){
            return state.selectedConditions
        },

        contractId(state){
            return state.contractId
        },
        insuranceCompanyId(state){
            return state.insuranceCompanyId
        },
    },
    mutations: {
        setSelectedConditions(state,value){
            state.selectedConditions = value
        },
        addSelectedCondition(state,value){
            // check if array doesn't contain any elements with same ID
            let possible = true
            for(let i=0; i<state.selectedConditions.length; i++){
                if(state.selectedConditions[i].id == value.id){
                    possible = false
                }
            }
            if(possible){
                state.selectedConditions.push(value)
            }
        },
        setContractId(state,id){
            state.contractId = id
        },
        setInsuranceCompanyId(state,id){
            state.insuranceCompanyId = id
        },
        clearSelectedConditions(state){
            state.selectedConditions = []
        }
    },
    actions: {
        fetchGreenCard(context, {contractId, insuranceId}){
            return new Promise((resolve, reject) => {
                RequestHandler.getObjectsRequest(utils.formatLocation(locations.GREEN_CARD, {contractId, insuranceId}))
                    .then(response => {
                        resolve(response)
                    }, response => {
                        reject(response)
                    })
            })
        }
    }
}



