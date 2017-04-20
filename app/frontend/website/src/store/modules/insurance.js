import * as locations from '../../constants/locations'
import RequestHandler from '../../api/requestHandler'
import Vue from 'vue'

export default {
    state: {
        contractInsurances: [], //A list of insurances from a contract
        filteredcontractInsurances: [], //A subset of the list of insurances
        suretyTypes: [],
        suretyData: {},
        suretyDetail: {},
        suretyVehicle: {},
        suretyDetailId: '',
        vehicleId: ''
    },
    getters: {
        /**
         * Get the list of contract insurances
         * @param state
         * @returns {Array}
         */
        contractInsurances(state) {
            return state.contractInsurances
        },

        /**
         * Get a subset of the list of insurances 
         * @param state
         * @returns {Array}
         */
        filteredcontractInsurances(state) {
            return state.filteredcontractInsurances
        },
        suretyData(state){
            return state.suretyData
        },
        suretyDetail(state){
            return state.suretyDetail
        },
        suretyVehicle(state){
            return state.suretyVehicle
        },
        suretyDetailId(state){
            return state.suretyDetailId
        },
        vehicleId(state) {
            return state.vehicleId
        }


    },
    mutations: {
        setContractInsurances(state,insurances){
            state.contractInsurances=insurances
        },
        /**
         * Sets the list of filtered contract Insurances
         * @param state
         * @param insurances
         */
        setFilteredcontractInsurances(state, insurances){
            state.filteredcontractInsurances = insurances
        },

        setSuretyData(state,surety){
            return state.suretyData = surety
        },
        setSuretyDetail(state,surety){
            return state.suretyDetail = surety
        },
        setSuretyDetailId(state,surety){
            return state.suretyDetailId = surety
        },

        setSuretyTypes(state,suretyTypes){
            return state.suretyTypes=suretyTypes
        },
        /**
         * Clear the list of insurances
         * @param state
         */
        clearContractInsurances(state){
            state.contractInsurances = []
        },

        /**
         * Add an insurance to the list of contract insurances
         * @param state
         * @param insurance
         */
        addContractInsurance(state, {insurance}){
            console.log('adding')
            console.log(insurance)
            state.contractInsurances.push(insurance)
            state.filteredcontractInsurances.push(insurance)
        },

    },
    actions: {
        /**
         * Fetches all of the insurances of a particular contract.
         * @param context
         * @param clientId
         * @returns {Promise}
         */
        fetchInsurancesByContract(context, contractId){
            console.log()
            console.log('fetchInsuranceByContract')
            console.log(locations.INSURANCE+contractId+'/insurances')
            var i
            // fetch all insurances from a contract
            return new Promise(resolve => {
                RequestHandler.getObjectsRequestBy(locations.INSURANCE+contractId+'/insurances').then(insurances => {
                    context.commit('setContractInsurances',insurances)
                    context.commit('setFilteredcontractInsurances',insurances)
                   //console.log(insurances)
                    //  for(i=0;i<insurances.length;i++){
                    //      context.commit('addContractInsurance',insurances[i])
                    //  }
                    // resolve(insurances)
                })
            })
        },

        /*  
            Fetches info for an insurance in a contract
            Also fetch information about insurance surety and insured vehicle
            @param payload: data containting id of insurance and id of contract
        */

        fetchSurety(context,payload){
            // fetch Surety
            return new Promise((resolveSuccess, resolveFailure) => {
                RequestHandler.getObjectRequest(locations.INSURANCE+payload.contractId+'/'+locations.SURETY, payload.id).then(surety => {
                    context.commit('setSuretyData', surety)
                    // set id for suretyDetailId
                    resolveSuccess(surety)
                }, response => {
                    resolveFailure(response)
                })
            })
            // fetch surety detail
            console.log(suretyDetailId)
        },

        fetchSuretyDetail(context,id){
            return new Promise((resolveSuccess, resolveFailure) => {
                RequestHandler.getObjectRequest(locations.SURETYDETAIL,id).then(suretyDetail => {
                    context.commit('setSuretyDetail', suretyDetail)
                    console.log(suretyDetail)
                    resolveSuccess(suretyDetail)
                }, response => {
                    resolveFailure(response)
                })
            })
        }, 

        fetchSuretyTypes(context){
             return new Promise((resolveSuccess, resolveFailure) => {
                RequestHandler.getObjectsRequest(locations.SURETYDETAIL+'types').then(suretyTypes => {
                    context.commit('setSuretyTypes',suretyTypes)
                    console.log(suretyTypes)
                    resolveSuccess(suretyTypes)
                }, response => {
                    resolveFailure(response)
                })
            })
        },
        // payload consist of contract id and input data
        deleteSurety(context,payload){

        },

        updateSurety(context,payload){

        },

        createSurety(context,payload){

        }
    }
}

