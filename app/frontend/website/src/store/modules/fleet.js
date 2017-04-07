import * as types from '../constants/mutationTypes'
import * as locations from '../constants/locations'
import RequestHandler from '../../api/requestHandler'
import Vue from 'vue'

export default {
    state: {
        fleets: [],
        subfleets: [],
        filteredFleets: [],
        filteredSubfleets: [],
        fleet: {}
    },
    getters: {
        fleets(state) {
            return state.fleets
        },

        filteredFleets(state){
            return state.filteredFleets
        },

        fleet(state) {
            return state.fleet
        },

        subfleets(state) {
            return state.subfleets
        },

        filteredSubfleets(state) {
            return state.filteredSubfleets
        },

        getFleetsByName(name){
            return state.fleets.filter(fleet => fleet.name === name)
        },

        getFleetsByClient(client){
            return state.fleets.filter(fleet => fleet.company = client)
        },

        getFleetsByAll: (state, getters) => (value) => {
            return getters.filterByAll(state.fleets, value)
        },

        getFleetsByAllAdvanced: (state, getters) => (fleet) => {
            return getters.filterByAllAdvanced(state.fleets, fleet)
        },

        //!!! This filters the vehicles of the subfleets
        getSubfleetsByAll: (state, getters) => (value) => {
            let filteredSubfleets = []
            for(let i=0; i<state.subfleets.length; i++){
                filteredSubfleets.push({type: state.subfleets[i].type, vehicles: getters.filterByAll(state.subfleets[i].vehicles, value)})
            }
            return filteredSubfleets
        },

        getSubfleetsByAllAdvanced: (state, getters) => (value) => {
            let filteredSubfleets = []
            for(let i=0; i<state.subfleets.length; i++){
                filteredSubfleets.push({type: state.subfleets[i].type, vehicles: getters.filterByAllAdvanced(state.subfleets[i].vehicles, value)})
            }
            return filteredSubfleets
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

        [types.UPDATE_FILTERED_SUBFLEETS] (state, {subfleets}){
            state.filteredSubfleets = subfleets
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
            addVehicleToSubfleets(state.subfleets, vehicle)
            addVehicleToSubfleets(state.filteredSubfleets, vehicle)
        },

        [types.REMOVE_VEHICLE_FROM_SUBFLEETS] (state, {vehicle}){
            removeVehicleFromSubfleets(state.subfleets, vehicle)
            removeVehicleFromSubfleets(state.filteredSubfleets, vehicle)
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
            return new Promise(resolve => {
                RequestHandler.getObjectsRequest(locations.FLEET + "?company=" + clientId).then(fleets => {
                    context.commit(types.RECEIVE_FLEETS, {fleets})
                    resolve(fleets)
                })
            })
        },

        createFleet(context, {fleet}){
            return new Promise(resolve => {
                RequestHandler.postObjectRequestGetBody(locations.FLEET, fleet).then(newFleet => {
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

        addClientName(context, {client}){
            Vue.set(context.state.fleet, 'companyName', client.name)
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
    }
}

let addVehicleToSubfleets = function(subfleets, vehicle){
    let finished = false
    for (let i = 0; i < subfleets.length && !finished; i++) {
        if (vehicle.type === subfleets[i].type.id) {
            subfleets[i].vehicles.push(vehicle);
            finished = true
        }
    }
}

let removeVehicleFromSubfleets = function(subfleets, vehicle){
    for(let i=0; i<subfleets.length; i++){
        if(subfleets[i].type.id === vehicle.type){
            let newVehicles = subfleets[i].vehicles.filter(obj => obj.id !== vehicle.id)
            subfleets[i].vehicles = newVehicles;
        }
    }
}