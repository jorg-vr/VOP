import * as types from '../mutationTypes'
import * as locations from '../locations'
import RequestHandler from '../../api/RequestHandler'
import Vue from 'vue'

export default {
    state: {
        fleets: [],
        subfleets: [],
        fleet: {}
    },
    getters: {
        fleets(state) {
            return state.fleets
        },

        fleet(state) {
            return state.fleet
        },

        subfleets(state) {
            return state.subfleets
        }
    },
    mutations: {
        [types.RECEIVE_FLEETS] (state, {fleets}){
            state.fleets = fleets
        },

        [types.RECEIVE_FLEET] (state, {fleet}){
            state.fleet = fleet
        },

        [types.CREATE_FLEET] (state, {fleet}){
            state.fleets.push(fleet)
        },

        [types.DELETE_FLEET] (state, {id}){
            state.fleets = state.fleets.filter(fleet => fleet.id !== id);
        },

        [types.RECEIVE_SUBFLEETS] (state, {subfleets}){
            state.subfleets = subfleets
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

        getFleet(context, {id}){
            return new Promise(resolve => {
                RequestHandler.getObjectRequest(locations.FLEET, id).then(fleet => {
                    context.commit(types.RECEIVE_FLEET, {fleet})
                    resolve(fleet)
                })
            })
        },

        createFleet(context, {fleet}){
            return new Promise(resolve => {
                RequestHandler.postObjectRequest(locations.FLEET, fleet).then(newFleet => {
                    context.commit(types.CREATE_FLEET, {newFleet})
                    resolve(newFleet)
                })
            })
        },

        updateFleet(context, {fleet}){
            return new Promise(resolve => {
                RequestHandler.putObjectRequest(locations.FLEET, fleet).then(updatedFleet => {
                    resolve(updatedFleet)
                })
            })
        },

        deleteFleet(context, {id}){
            return new Promise(resolve => {
                RequestHandler.deleteObjectRequest(locations.FLEET, id).then(() => {
                    context.commit(types.DELETE_FLEET, {id})
                    resolve()
                }, id)
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
        },

        getSubfleets(context, {vehicles, vehicleTypes}){
            let subfleets = []
            for(let i=0; i< vehicles.length; i++) {
                let vehicle = vehicles[i];
                let added = false; //True when the vehicle has been added to a subfleet
                for (let j = 0; j < subfleets.length && !added; j++) {
                    if (vehicle.type === subfleets[j].type.id) {
                        subfleets[j].vehicles.push(vehicle);
                        added = true;
                    }
                }
                //If a subfleet doesn't exist yet with the current subfleet types.
                if (!added) { //Create new subfleet
                    let created = false; //True when the subfleet has been created.
                    for (let j = 0; j < vehicleTypes.length && !created; j++) { //Search for the vehicleType object
                        if (vehicle.type === vehicleTypes[j].id) {
                            subfleets.push({type: vehicleTypes[j], vehicles: [vehicle]})
                            created = true;
                        }
                    }
                }
            }
            context.commit(types.RECEIVE_SUBFLEETS, {subfleets})
        }
    }
}
