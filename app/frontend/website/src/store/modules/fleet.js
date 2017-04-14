import * as locations from '../../constants/locations'
import RequestHandler from '../../api/requestHandler'
import Vue from 'vue'

export default {
    state: {
        subfleets: [],
        filteredSubfleets: [],
    },
    getters: {
        subfleets(state) {
            return state.subfleets
        },

        filteredSubfleets(state) {
            return state.filteredSubfleets
        },

        //Filter the vehicles of the subfleets
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
        setFilteredSubfleets(state, subfleets){
            state.filteredSubfleets = subfleets
        },

        clearSubfleets(state){
            state.subfleets = []
        },

        addSubfleet(state, {subfleet}){
            state.subfleets.push(subfleet)
        },


        addVehicleToSubfleet(state, {vehicle}){
            addVehicleToSubfleets(state.subfleets, vehicle)
        },

        removeVehicleFromSubfleet(state, {vehicle}){
            removeVehicleFromSubfleets(state.subfleets, vehicle)
        }
    },
    actions: {
        fetchFleetsByClient(context, {clientId}){
            return new Promise(resolve => {
                RequestHandler.getObjectsRequestBy(locations.FLEET, {company: clientId}).then(fleets => {
                    context.commit('setFleets', fleets)
                    context.commit('setFilteredFleets', fleets)
                    resolve(fleets)
                })
            })
        },

        addClientName(context, {client}){
            Vue.set(context.getters.fleet , 'companyName', client.name)
        },

        addClientNames(context, {clients}){
            let fleets = context.getters.fleets
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
            context.commit('clearSubfleets')
            for(let i=0; i < vehicleTypes.length; i++){
                context.commit('addSubfleet', {subfleet: {type: vehicleTypes[i], vehicles: []}})
            }
            for(let i=0; i < vehicles.length; i++) {
                context.commit('addVehicleToSubfleet', {vehicle: vehicles[i]})
            }
            context.commit('setFilteredSubfleets', context.getters.subfleets)
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