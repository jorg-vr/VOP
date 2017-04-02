import Vue from 'vue'
import Vuex from 'vuex'

import fleet from './modules/fleet'
import vehicles from './modules/vehicle'
import client from './modules/client'
import user from './modules/user'

import getters from './getters'

Vue.use(Vuex)

export default new Vuex.Store({
    modules : {
        fleet,
        vehicles,
        client,
        user
    },
    getters
})