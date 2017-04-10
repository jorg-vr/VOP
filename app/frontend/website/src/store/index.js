import Vue from 'vue'
import Vuex from 'vuex'

import fleet from './modules/fleet'
import vehicles from './modules/vehicle'
import client from './modules/client'
import user from './modules/user'
import login from './modules/login'
import roles from './modules/roles'
import functions from './modules/functions'
import authentication from './modules/authentication'

import resource from './modules/resource'
import * as locations from './constants/locations'

import state from './state'
import getters from './getters'
import mutations from './mutations'

Vue.use(Vuex)

let permissionResource = resource.initialise(locations.PERMISSIONS, 'permission')
export default new Vuex.Store({
    modules : {
        permissionResource,
        /*
        fleet,
        vehicles,
        client,
        user,
        login,
        roles,
        functions,
        authentication
        */
    },
    /*
    state,
    getters,
    mutations
    */
})