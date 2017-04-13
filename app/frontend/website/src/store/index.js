import Vue from 'vue'
import Vuex from 'vuex'

import fleet from './modules/fleet'
import vehicle from './modules/vehicle'
import login from './modules/login'
import currentUser from './modules/currentUser'

import resourceModule from './resourceModule'
import * as locations from '../constants/locations'

import state from './state'
import getters from './getters'
import mutations from './mutations'


Vue.use(Vuex)


//Basic operations for each resource
let fleetResource = resourceModule.initializeModule(locations.FLEET, 'fleet')
let vehicleResource = resourceModule.initializeModule(locations.VEHICLE, 'vehicle')
let clientResource = resourceModule.initializeModule(locations.CLIENT, 'client')
let userResource = resourceModule.initializeModule(locations.USER, 'user')
let functionResource = resourceModule.initializeModule(locations.USER_FUNCTIONS, 'userFunction')
let roleResource = resourceModule.initializeModule(locations.ROLE, 'role')
let permissionResource = resourceModule.initializeModule(locations.PERMISSIONS, 'permission')
let vehicleTypeResource = resourceModule.initializeModule(locations.VEHICLE_TYPES, 'vehicleType')
let insuranceResource = resourceModule.initializeModule(locations.INSURANCE, 'insurance')


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
        insuranceResource,
        //The following modules provide additional functions for the previous resources (or are completely new modules).
        fleet,
        vehicle,
        login,
        currentUser
    },
    state,
    getters,
    mutations
})