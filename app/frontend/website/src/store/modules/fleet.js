import * as locations from '../../constants/locations'
import RequestHandler from '../../api/requestHandler'
import Vue from 'vue'

export default {
    state: {
        subfleets: [], //A list of subfleets
        filteredSubfleets: [], //A subset of the list of subfleets.
    },
    getters: {
        /**
         * Get the list of subfleets
         * @param state
         * @returns {Array}
         */
        subfleets(state) {
            return state.subfleets
        },

        /**
         * Get a subset of the list of subfleets (Every subfleet is a list aswell, these lists can be subsets aswell of the original list.)
         * @param state
         * @returns {Array}
         */
        filteredSubfleets(state) {
            return state.filteredSubfleets
        },

        /**
         * Filter the vehicles of the subfleets with the given value.
         * @param state
         * @param value
         */
        getSubfleetsByAll: (state, getters) => (value) => {
            let filteredSubfleets = []
            for(let i=0; i<state.subfleets.length; i++){
                filteredSubfleets.push({type: state.subfleets[i].type, vehicles: getters.filterByAll(state.subfleets[i].vehicles, value)})
            }
            return filteredSubfleets
        },

        /**
         * Filter the vehicles of the subfleets with the given object.
         * @param value
         */
        getSubfleetsByAllAdvanced: (state, getters) => (value) => {
            let filteredSubfleets = []
            for(let i=0; i<state.subfleets.length; i++){
                filteredSubfleets.push({type: state.subfleets[i].type, vehicles: getters.filterByAllAdvanced(state.subfleets[i].vehicles, value)})
            }
            return filteredSubfleets
        }
    },
    mutations: {
        /**
         * Sets the list of filtered subfleets
         * @param state
         * @param subfleets
         */
        setFilteredSubfleets(state, subfleets){
            state.filteredSubfleets = subfleets
        },

        /**
         * Clear the list of subfleets
         * @param state
         */
        clearSubfleets(state){
            state.subfleets = []
        },

        /**
         * Add a subfleets to the list of subfleets
         * @param state
         * @param subfleet
         */
        addSubfleet(state, {subfleet}){
            state.subfleets.push(subfleet)
        },


        /**
         * Add a vehicle to the correct list of subfleets.
         * @param state
         * @param vehicle
         */
        addVehicleToSubfleet(state, {vehicle}){
            addVehicleToSubfleets(state.subfleets, vehicle)
        },

        /**
         * Remove a vehicle from the correct list of subfleets.
         * @param state
         * @param vehicle
         */
        removeVehicleFromSubfleet(state, {vehicle}){
            removeVehicleFromSubfleets(state.subfleets, vehicle)
        }
    },
    actions: {
        /**
         * Adds the companyName property to the fleet which is currently in the store.
         * @param context
         * @param client The client of the fleet
         */
        addClientName(context, {client}){
            Vue.set(context.getters.fleet , 'companyName', client.name)
        },

        /**
         * Adds the companyName property to the list of fleets currently in the store.
         * @param context
         * @param clients: A list of all of the clients.
         */
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

        /**
         * Initialise a list of subfleets.
         * @param context
         * @param vehicles
         * @param vehicleTypes: The types for all subfleets
         */
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

/**
 * Add a vehicle to the given list of subfleets.
 * @param subfleets
 * @param vehicle
 */
let addVehicleToSubfleets = function(subfleets, vehicle){
    let finished = false
    for (let i = 0; i < subfleets.length && !finished; i++) {
        if (vehicle.type === subfleets[i].type.id) {
            subfleets[i].vehicles.push(vehicle);
            finished = true
        }
    }
}

/**
 * Remove a vehicle from the given list of subfleets.
 * @param subfleets
 * @param vehicle
 */
let removeVehicleFromSubfleets = function(subfleets, vehicle){
    for(let i=0; i<subfleets.length; i++){
        if(subfleets[i].type.id === vehicle.type){
            let newVehicles = subfleets[i].vehicles.filter(obj => obj.id !== vehicle.id)
            subfleets[i].vehicles = newVehicles;
        }
    }
}