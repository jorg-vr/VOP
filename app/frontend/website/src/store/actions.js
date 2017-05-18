import * as locations from '../constants/locations'
import RequestHandler from '../api/requestHandler'
import {formatLocation, addShowableDateTime} from '../utils/utils'

export default {
    /**
     * Fetch a list of all the available permissions
     * @param context
     */
    fetchPermissionList(context){
        return new Promise((resolve, reject) => {
            RequestHandler.getObjectsRequest(locations.PERMISSION_LIST).then(response => {
                resolve(response)
            }, response => {
                reject(response)
            })
        })
    },
    /**
     * Fetch the logs of the resource with the given name and the given id.
     * @param context
     * @param resource Name of the resource
     * @param id Id of the resource
     * @returns {Promise}
     */
    fetchLogs(context, {resource, id}){
        return new Promise((resolve, reject) => {
            RequestHandler.getObjectsRequest(formatLocation(locations.LOG, {resource: resource, resourceId: id})).then(logs => {
                logs = logs.sort((a, b) => { return new Date(b.dateTime) - new Date(a.dateTime) })
                addShowableDateTime(logs, 'dateTime')
                resolve(logs)
            }, response => reject(response))
        })
    },

    /**
     * Fetch the logEntry of the resource with the given name and the given id.
     * @param context
     * @param resource Name of the resource
     * @param resourceId Id of the resource
     * @param id Id of the logEntry
     * @returns {Promise}
     */
    fetchLog(context, {resource, resourceId, id}){
        return new Promise((resolve, reject) => {
            RequestHandler.getObjectRequest(formatLocation(locations.LOG, {resource: resource, resourceId: resourceId}), id).then(log => {
                resolve(log)
            }, response => reject(response))
        })
    },

    /**
     * Pop a route from the visitedRoutes stack.
     */
    popVisitedRoute(context){
        return context.state.popVisitedRoute()
    }
}