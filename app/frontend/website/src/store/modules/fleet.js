import * as types from '../mutationTypes'
import * as locations from '../locations'
import RequestHandler from '../../api/RequestHandler'
import Vue from 'vue'

export default {
    state: {
        fleets: []
    },
    getters: {
        fleets(state) {
            return state.fleets
        }
    },
    mutations: {
        [types.RECEIVE_FLEETS] (state, {fleets}){
            state.fleets = fleets
        },

        [types.DELETE_FLEET] (state, {fleetId}){
            state.fleets = state.fleets.filter(fleet => fleet.id !== fleetId);
        }
    },
    actions: {
        getFleets(context){
            return new Promise(resolve => {
                RequestHandler.getObjectsRequest(locations.FLEET).then(fleets => {
                    context.commit(types.RECEIVE_FLEETS, {fleets})
                    resolve(fleets)
                })
            })
        },
        deleteFleet(context, {fleetId}){
            return new Promise(() => {
                RequestHandler.deleteObjectRequest(locations.FLEET, () => {
                    context.commit(types.DELETE_FLEET, {fleetId})
                }, fleetId)
            })
        },
        addClientNames(context, {clients}){
            let fleets = context.state.fleets
            for(let i=0; i<fleets.length; i++){
                let fleet = fleets[i];
                let client = clients.find(obj => obj.id === fleet.company)
                if(client){
                    //The key is updated this way so Vue can detect the property change
                    Vue.set(fleet, 'companyName', client.name)
                }
            }
        }
    }
}
