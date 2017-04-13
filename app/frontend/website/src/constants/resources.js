import * as actions from './actions'
/**
 * This file is a mapping from pages to resources. This is used for permission checking.
 */

//Binds a resource and an action to the name of a route.
//For example: 'vehicles', 'new_vehicle', 'vehicle', 'edit_vehicle'
let addRoutesForResource = function(resourceName, resourceValue){
    res[resourceName + 's'] = {
        resource: resourceValue,
        actions: actions.READ
    }
    res['new_' + resourceName] = {
        resource: resourceValue,
        actions: actions.CREATE
    }
    res[resourceName] = {
        resource: resourceValue,
        actions: actions.READ
    }
    res['edit_' + resourceName] = {
        resource: resourceValue,
        actions: actions.UPDATE
    }
}

let res = {}
addRoutesForResource('user', 'USER')
addRoutesForResource('vehicle', 'VEHICLE')
addRoutesForResource('fleet', 'FLEET')
addRoutesForResource('insurance', 'INSURANCE')
addRoutesForResource('client', 'COMPANY')

export const resources = res


/* For future reference if necessary...
 export default {
 USER: 'USER',
 FUNCTION: 'FUNCTION',
 ROLE: 'ROLE',
 BILLING: 'BILLING',
 VEHICLE: 'VEHICLE',
 FLEET: 'FLEET',
 VEHICLETYPE: 'VEHICLETYPE',
 COMPANY: 'COMPANY',
 INSURANCE: 'INSURANCE'
 }

 export default {
 READ_MINE: 'READ_MINE',
 READ_ALL: 'READ_ALL',
 UPDATE_MINE: 'UPDATE_MINE',
 UPDATE_ALL: 'UPDATE_ALL',
 REMOVE_ALL: 'REMOVE_ALL',
 REMOVE_MINE: 'REMOVE_MINE',
 CREATE_ALL: 'CREATE_ALL',
 CREATE_MINE: 'CREATE_MINE'
 }
 */