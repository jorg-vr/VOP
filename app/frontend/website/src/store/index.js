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
import insurance from './modules/insurance'

import state from './state'
import getters from './getters'
import mutations from './mutations'

Vue.use(Vuex)

export default new Vuex.Store({
    modules : {
        fleet,
        vehicles,
        client,
        user,
        login,
        roles,
        functions,
        authentication,
        insurance
    },
    state,
    getters,
    mutations
})