import * as locations from '../constants/locations'
import RequestHandler from '../../api/requestHandler'

export default {
    actions: {
        fetchVehiclesBy(context, {vehicle}){
            return new Promise(resolve => {
                RequestHandler.getObjectsRequestBy(locations.VEHICLE, vehicle).then(vehicles => {
                    console.log(vehicles)
                    context.commit('setFilteredVehicles', vehicles)
                    resolve(vehicles)
                })
            })
        }
    }
}
