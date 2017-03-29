import * as types from '../constants/mutationTypes'
import * as locations from '../constants/locations'
import RequestHandler from '../../api/requestHandler'
import Vue from 'vue'

export default {
    state: {
        fleets: [],
        subfleets: [],
        filteredFleets: [],
        fleet: {}
    },
    getters: {
        fleets(state) {
            return state.fleets
        },

        filteredFleets(state){

        },

        fleet(state) {
            return state.fleet
        },

        subfleets(state) {
            return state.subfleets
        },

        getFleetsByName(name){
            return state.fleets.filter(fleet => fleet.name === name)
        },

        getFleetsByClient(client){
            return state.fleets.filter(fleet => fleet.company = client)
        },

        getFleetsByAll: (state, getters) => (value) => {
            return getters.filterByAll(state.fleets, value)
        }
    },
    mutations: {
        [types.RECEIVE_FLEETS] (state, {fleets}){
            state.fleets = fleets
        },

        [types.RECEIVE_FLEET] (state, {fleet}){
            state.fleet = fleet
        },

        [types.UPDATE_FILTERED_FLEETS] (state, {fleets}){
            state.filteredFleets = fleets
        },

        [types.CREATE_FLEET] (state, {fleet}){
            state.fleets.push(fleet)
            state.filteredFleets.push(fleet)
        },

        [types.DELETE_FLEET] (state, {id}){
            state.fleets = state.fleets.filter(fleet => fleet.id !== id);
            state.filteredFleets = state.filteredFleets.filter(fleet => fleet.id !== id);
        },

        [types.CLEAR_SUBFLEETS] (state) {
            state.subfleets = []
        },

        [types.CREATE_SUBFLEET] (state, {subfleet}){
            state.subfleets.push(subfleet)
        },

        [types.ADD_VEHICLE_TO_SUBFLEETS] (state, {vehicle}){
            let subfleets = state.subfleets
            let finished = false
            for (let i = 0; i < subfleets.length && !finished; i++) {
                if (vehicle.type === subfleets[i].type.id) {
                    subfleets[i].vehicles.push(vehicle);
                    finished = true
                }
            }
        },

        [types.REMOVE_VEHICLE_FROM_SUBFLEETS] (state, {vehicle}){
            let subfleets = state.subfleets
            for(let i=0; i<subfleets.length; i++){
                if(subfleets[i].type.id === vehicle.type){
                    let newVehicles = subfleets[i].vehicles.filter(obj => obj.id !== vehicle.id)
                    subfleets[i].vehicles = newVehicles;
                }
            }
        }
    },
    actions: {
        fetchFleets(context){
            return new Promise(resolve => {
                RequestHandler.getObjectsRequest(locations.FLEET).then(fleets => {
                    context.commit(types.RECEIVE_FLEETS, {fleets})
                    resolve(fleets)
                })
            })
        },

        fetchFleet(context, {id}){
            return new Promise(resolve => {
                RequestHandler.getObjectRequest(locations.FLEET, id).then(fleet => {
                    context.commit(types.RECEIVE_FLEET, {fleet})
                    resolve(fleet)
                })
            })
        },

        fetchFleetsByClient(context, {clientId}){
            console.log(clientId)
            return new Promise(resolve => {
                RequestHandler.getObjectsRequest(locations.FLEET + "?company=" + clientId).then(fleets => {
                    context.commit(types.RECEIVE_FLEETS, {fleets})
                    resolve(fleets)
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
                    context.commit(types.DELETE_FLEET, {id: id})
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
            context.commit(types.CLEAR_SUBFLEETS)
            for(let i=0; i < vehicleTypes.length; i++){
                context.commit(types.CREATE_SUBFLEET, {subfleet: {type: vehicleTypes[i], vehicles: []}})
            }
            for(let i=0; i < vehicles.length; i++) {
                context.commit(types.ADD_VEHICLE_TO_SUBFLEETS, {vehicle: vehicles[i]})
            }
        },

        //This is an action and not a getter as it needs to access a root action
        getFleetsByAll({dispatch, state}, {value}){
            return dispatch('filterByAll', {objects: state.fleets, value: value})
        }
    }
}
