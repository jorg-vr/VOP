import Vue from 'vue'
import Vuex from 'vuex'

import fleet from './modules/fleet'
import vehicles from './modules/vehicle'
import client from './modules/client'
import user from './modules/user'
import login from './modules/login'
import authentication from './modules/authentication'


import getters from './getters'

Vue.use(Vuex)

export default new Vuex.Store({
    modules : {
        fleet,
        vehicles,
        client,
        user,
        login,
        authentication
    },
    getters
})