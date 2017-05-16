import Vue from 'vue'
import Vuex from 'vuex'

import fleet from './modules/fleet'
import login from './modules/login'
import invoice from './modules/invoice'
import insurance from './modules/insurance'
import currentUser from './modules/currentUser'

import resourceModule from './resourceModule'
import * as locations from '../constants/locations'

import state from './state'
import getters from './getters'
import mutations from './mutations'
import actions from './actions'


Vue.use(Vuex)



//Basic operations for each resource

let fleetResource = resourceModule.initializeModule(locations.FLEET, 'fleet')
let vehicleResource = resourceModule.initializeModule(locations.VEHICLE, 'vehicle')
let clientResource = resourceModule.initializeModule(locations.CLIENT, 'client')
let userResource = resourceModule.initializeModule(locations.USER, 'user')
let userFunctionResource = resourceModule.initializeModule(locations.USER_FUNCTION, 'userFunction')
let roleResource = resourceModule.initializeModule(locations.ROLE, 'role')
let permissionResource = resourceModule.initializeModule(locations.PERMISSIONS, 'permission')
let vehicleTypeResource = resourceModule.initializeModule(locations.VEHICLE_TYPE, 'vehicleType')
let commissionResource = resourceModule.initializeModule(locations.COMMISSION, 'commission')
let contractResoure = resourceModule.initializeModule(locations.CONTRACT, 'contract')
let suretyResource = resourceModule.initializeModule(locations.SURETY,'surety', 'sureties')
let conditionResource = resourceModule.initializeModule(locations.CONDITION, 'condition')
let insuranceResource = resourceModule.initializeModule(locations.INSURANCE, 'insurance')
let functionResource = resourceModule.initializeModule(locations.FUNCTION, 'function')

export default new Vuex.Store({
    modules : {
        fleetResource,
        vehicleResource,
        clientResource,
        userResource,
        userFunctionResource,
        roleResource,
        permissionResource,
        vehicleTypeResource,
        contractResoure,
        suretyResource,
        conditionResource,
        insuranceResource,
        commissionResource,
        functionResource,
        //The following modules provide additional functions for the previous resources (or are completely new modules).
        fleet,
        login,
        currentUser,
        invoice,
        insurance,
    },
    state,
    getters,
    mutations,
    actions
})