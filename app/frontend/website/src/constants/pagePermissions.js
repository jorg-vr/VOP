import resources from './resources'
import actions from './actions'
/**
 * This file is a mapping from pages to resources. This is used for permission checking.
 */

//Binds a resource and an action to the name of a route.
//For example: 'vehicles', 'new_vehicle', 'vehicle', 'edit_vehicle'
let addRoutesForResource = function(resource){
    let resourceName = resource.name
    let resourceValue = resource.value
    permissions[actions.READ_ALL.path(resourceName)] = {
        resource: resourceValue,
        actions: actions.READ_ALL.values
    }
    permissions[actions.CREATE.path(resourceName)] = {
        resource: resourceValue,
        actions: actions.CREATE.values
    }
    permissions[actions.READ_ONE.path(resourceName)] = {
        resource: resourceValue,
        actions: actions.READ_ONE.values
    }
    permissions[actions.UPDATE.path(resourceName)] = {
        resource: resourceValue,
        actions: actions.UPDATE.values
    }
}

let permissions = {}
addRoutesForResource(resources.USER)
addRoutesForResource(resources.VEHICLE)
addRoutesForResource(resources.FLEET)
addRoutesForResource(resources.INSURANCE)
addRoutesForResource(resources.CLIENT)

export const pagePermissions = permissions

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