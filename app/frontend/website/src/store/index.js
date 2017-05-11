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


Vue.use(Vuex)



//Basic operations for each resource

let fleetResource = resourceModule.initializeModule(locations.FLEET, 'fleet')
let vehicleResource = resourceModule.initializeModule(locations.VEHICLE, 'vehicle')
let clientResource = resourceModule.initializeModule(locations.CLIENT, 'client')
let userResource = resourceModule.initializeModule(locations.USER, 'user')
let functionResource = resourceModule.initializeModule(locations.USER_FUNCTION, 'userFunction')
let roleResource = resourceModule.initializeModule(locations.ROLE, 'role')
let permissionResource = resourceModule.initializeModule(locations.PERMISSIONS, 'permission')
let vehicleTypeResource = resourceModule.initializeModule(locations.VEHICLE_TYPE, 'vehicleType')
let commissionResource = resourceModule.initializeModule(locations.COMMISSION, 'commission')
let contractResoure = resourceModule.initializeModule(locations.CONTRACT,'contract')
let invoiceResource = resourceModule.initializeModule(locations.INVOICE, 'invoice')
let suretyResource = resourceModule.initializeModule(locations.SURETY,'surety','sureties')
let conditionResource = resourceModule.initializeModule(locations.CONDITION,'condition')
let insuranceResource = resourceModule.initializeModule(locations.INSURANCE,'insurance')



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
        contractResoure,
        suretyResource,
        conditionResource,
        insuranceResource,
        commissionResource,
        // invoiceResource,
        //The following modules provide additional functions for the previous resources (or are completely new modules).
        fleet,
        login,
        currentUser,
        invoice,
        insurance
    },
    state,
    getters,
    mutations
})