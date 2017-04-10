import * as types from '../constants/mutationTypes'
import * as locations from '../constants/locations'
import RequestHandler from '../../api/requestHandler'
import Vue from 'vue'


export default {
    state: {
		insurances: [],
        insurance: {},
        filteredInsurances: []
    },
    getters: {
		insurances(state) {
            return state.insurances
        },

        insurance(state) {
            return state.insurance
        },

        filteredInsurances(state){
            return state.filteredInsurances
        },
		getInsurancesByAll: (state, getters) => (value) => {
            return getters.filterByAll(state.insurances, value)
        },

        getInsurancesByAllAdvanced: (state, getters) => (insurance) => {
            return getters.filterByAllAdvanced(state.insurances, insurance)
        }
    },
    mutations: {
		[types.RECEIVE_INSURANCES] (state, {insurances}){
            state.insurances = insurances
        },

        [types.RECEIVE_INSURANCE] (state, {insurance}){
            state.insurance = insurance
        },

        [types.UPDATE_FILTERED_INSURANCES] (state, {insurances}){
            state.filteredInsurances = insurances
        },

        [types.CREATE_INSURANCE] (state, {insurance}){
            state.insurances.push(insurance)
            state.filteredInsurances.push(insurance)
        },

        [types.DELETE_INSURANCE] (state, {id}){
            state.insurances = state.insurances.filter(insurance => insurance.id !== id)
            state.filteredInsurances = state.filteredInsurances.filter(insurance => insurance.id !== id)
        }
       
    },
    actions: {
        fetchInsurances(context){
            return new Promise(resolve => {
                RequestHandler.getObjectsRequest(locations.INSURANCE).then(insurances => {
                    context.commit(types.RECEIVE_INSURANCES, {insurances})
                    resolve(insurances)
                })
            })
        },
		fetchInsurance(context, {id}){
            return new Promise(resolve => {
                RequestHandler.getObjectRequest(locations.INSURANCE, id).then(insurance => {
                    context.commit(types.RECEIVE_INSURANCE, {insurance})
                    resolve(insurance)
                })
            })
        },
		createInsurance(context, {insurance}){
            return new Promise(resolve => {
                RequestHandler.postObjectRequestGetBody(locations.INSURANCE, insurance).then(newInsurance => {
                    context.commit(types.CREATE_INSURANCE, {newInsurance})
                    resolve(newInsurance)
                })
            })
        },

        updateInsurance(context, {insurance}){
            return new Promise(resolve => {
                RequestHandler.putObjectRequest(locations.INSURANCE, insurance).then(updatedInsurance => {
                    resolve(updatedInsurance)
                })
            })
        },

        deleteInsurance(context, {id}){
            return new Promise(resolve => {
                RequestHandler.deleteObjectRequest(locations.INSURANCE, id).then(() => {
                    context.commit(types.DELETE_INSURANCE, {id})
                    resolve()
                }, id)
            })
        },
    }
}

