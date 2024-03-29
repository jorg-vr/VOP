import resources from './resources'
import actions from './actions'
/**
 * This file is a mapping from pages to resources. This is used for permission checking.
 */

//Binds a resource and an action to the name of a route.
//For example: 'vehicles', 'new_vehicle', 'vehicle', 'edit_vehicle'
let addRoutesForResource = function(resource){
    permissions[actions.READ_ALL.path(resource.name)] = {
        resource: resource,
        actions: actions.READ_ALL
    }
    permissions[actions.CREATE.path(resource.name)] = {
        resource: resource,
        actions: actions.CREATE
    }
    permissions[actions.READ_ONE.path(resource.name)] = {
        resource: resource,
        actions: actions.READ_ONE
    }
    permissions[actions.UPDATE.path(resource.name)] = {
        resource: resource,
        actions: actions.UPDATE
    }
}

let permissions = {}
addRoutesForResource(resources.USER)
addRoutesForResource(resources.VEHICLE)
addRoutesForResource(resources.FLEET)
addRoutesForResource(resources.INSURANCE)
addRoutesForResource(resources.CLIENT)
addRoutesForResource(resources.INVOICE)
addRoutesForResource(resources.SURETY)
addRoutesForResource(resources.VEHICLE_TYPE)
addRoutesForResource(resources.CONTRACT)
addRoutesForResource(resources.CONDITION)
addRoutesForResource(resources.LOG)
addRoutesForResource(resources.FUNCTION)
addRoutesForResource(resources.ROLE)


//Define exceptions
/**
 * Fictional example:
 * permissions[route] = {             ---> route indicates the name of the route for which we define a permission requirement
 *  resource: resources.VEHICLE       ---> resource indicates which resource the user needs to have access to
 *  actions: {
 *      name: actions.READ_ALL.name,  ---> the name of the action
 *      path: actions.READ_ALL.path,  ---> the path to where the actions leads
 *      values: ['CREATE_ALL', 'CREATE_MINE']
 *      ---> The user needs to have one of these actions for the given resource as permission to get access to the page.
 *  }
 */

//Users & client pages can only be seen with READ_ALL permissions.
permissions[actions.READ_ALL.path(resources.USER.name)] = {
    resource: resources.USER,
    actions: {
        name: actions.READ_ALL.name,
        path: actions.READ_ALL.path,
        values: ['READ_ALL']
    }
}

permissions[actions.READ_ALL.path(resources.CLIENT.name)] = {
    resource: resources.CLIENT,
    actions: {
        name: actions.READ_ALL.name,
        path: actions.READ_ALL.path,
        values: ['READ_ALL']
    }
}

permissions[actions.READ_ALL.path(resources.BILLING.name)] = {
    resource: resources.BILLING,
    actions: {
        name: actions.READ_ALL.name,
        path: actions.READ_ALL.path,
        values: ['READ_MINE']
    }
}


permissions[actions.READ_ALL.path(resources.VEHICLE_TYPE.name)] = {
    resource: resources.CLIENT,
    actions: {
        name: actions.READ_ALL.name,
        path: actions.READ_ALL.path,
        values: ['CREATE_MINE', 'CREATE_ALL', 'EDIT_MINE', 'EDIT_ALL', 'REMOVE_MINE', 'REMOVE_ALL']
    }
}

permissions[actions.READ_ALL.path(resources.ROLE.name)] = {
    resource: resources.ROLE,
    actions: {
        name: actions.READ_ALL.name,
        path: actions.READ_ALL.path,
        values: ['CREATE_MINE', 'CREATE_ALL', 'EDIT_MINE', 'EDIT_ALL', 'REMOVE_MINE', 'REMOVE_ALL']
    }
}

permissions['import_vehicles'] = [
    {
        resource: resources.FLEET,
        actions: {
            values: ['READ_MINE', 'READ_ALL']
        }
    },
    {
        resource: resources.VEHICLE,
        actions: {
            values: ['CREATE_MINE', 'CREATE_ALL']
        }
    }
]

permissions['vehicle_logs'] = permissions['fleet_logs'] = [
    {
        resource: resources.LOG,
        actions: {
            values: ['READ_MINE', 'READ_ALL']
        }
    }
]

export const PagePermissions = permissions

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