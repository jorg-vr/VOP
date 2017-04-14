import * as locations from '../../constants/locations'
import RequestHandler from '../../api/requestHandler'

export default {
    actions: {
        fetchVehiclesByFleet(context, {fleetId}){
            return new Promise(resolve => {
                context.dispatch('fetchVehiclesBy', {fleet: fleetId}).then(vehicles => {
                    resolve(vehicles)
                })
            })
        },
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
