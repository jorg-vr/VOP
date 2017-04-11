import Vue from 'vue'
import Vuex from 'vuex'

import fleet from './modules/fleet'
import vehicle from './modules/vehicle'
import login from './modules/login'
import authentication from './modules/authentication'

import resource from './modules/resource'
import * as locations from './constants/locations'

import state from './state'
import getters from './getters'
import mutations from './mutations'

Vue.use(Vuex)


//Basic operations for each resource
let fleetResource = resource.initializeModule(locations.FLEET, 'fleet')
let vehicleResource = resource.initializeModule(locations.VEHICLE, 'vehicle')
let clientResource = resource.initializeModule(locations.CLIENT, 'client')
let userResource = resource.initializeModule(locations.USER, 'user')
let functionResource = resource.initializeModule(locations.USER_FUNCTIONS, 'userFunction')
let roleResource = resource.initializeModule(locations.ROLE, 'role')
let permissionResource = resource.initializeModule(locations.PERMISSIONS, 'permission')
let vehicleTypeResource = resource.initializeModule(locations.VEHICLE_TYPES, 'vehicleType')

export default new Vuex.Store({
    modules : {
        fleetResource,
        vehicleResource,
        clientResource,
        userResource,
        functionResource,
        roleResource,
        permissionResource,
        vehicleTypeResource,
        //The following modules provide additional functions for the previous resources (or are completely new modules).
        fleet,
        vehicle,
        login,
        authentication
    },
    state,
    getters,
    mutations
})