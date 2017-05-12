import * as locations from '../../constants/locations'
import RequestHandler from '../../api/requestHandler'
import {formatLocation, addShowableDateTime} from '../../utils/utils'

export default {
    actions: {
        fetchVehicleLogs(context, {id}){
            return new Promise((resolve, reject) => {
                RequestHandler.getObjectsRequest(formatLocation(locations.LOG, {resource: 'vehicles', resourceId: id})).then(logs => {
                    logs = logs.sort((a, b) => { return new Date(b.dateTime) - new Date(a.dateTime) })
                    addShowableDateTime(logs, 'dateTime')
                    resolve(logs)
                }, response => reject(response))
            })
        },

        fetchVehicleLog(context, {vehicleId, id}){
            return new Promise((resolve, reject) => {
                RequestHandler.getObjectRequest(formatLocation(locations.LOG, {resource: 'vehicles', resourceId: vehicleId}), id).then(log => {
                    resolve(log)
                }, response => reject(response))
            })
        }
    }
}