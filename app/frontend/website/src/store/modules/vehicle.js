import * as locations from '../../constants/locations'
import RequestHandler from '../../api/requestHandler'

export default {
    actions: {
        /**
         * Fetch all of the vehicles which belong to the fleet with the given fleetId
         * @param context
         * @param fleetId
         * @returns {Promise}
         */
        fetchVehiclesByFleet(context, {fleetId}){
            return new Promise(resolve => {
                context.dispatch('fetchVehiclesBy', {fleet: fleetId}).then(vehicles => {
                    resolve(vehicles)
                })
            })
        },

        /**
         * Fetch a list of vehicles which have the same values as the given vehicle
         * @param context
         * @param vehicle
         * @returns {Promise}
         */
        fetchVehiclesBy(context, {vehicle}){
            return new Promise(resolve => {
                RequestHandler.getObjectsRequestBy(locations.VEHICLE, vehicle).then(vehicles => {
                    context.commit('setVehicles', vehicles)
                    context.commit('setFilteredVehicles', vehicles)
                    resolve(vehicles)
                })
            })
        }
    }
}
