/**
 * This file is a mapping from pages to resources. This is used for permission checking.
 */

//Binds a resource and an action to the name of a route.
//For example: 'vehicles', 'new_vehicle', 'vehicle', 'edit_vehicle'
let addRoutesForResource = function(resourceName, resourceValue){
    res[resourceName + 's'] = {
        resource: resourceValue,
        action: ['READ_MINE', 'READ_ALL']
    }
    res['new_' + resourceName] = {
        resource: resourceValue,
        action: ['CREATE_ALL', 'CREATE_MINE']
    }
    res[resourceName] = {
        resource: resourceValue,
        action: ['READ_ALL', 'READ_MINE']
    }
    res['edit_' + resourceName] = {
        resource: resourceValue,
        action: ['UPDATE_ALL', 'UPDATE_MINE']
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