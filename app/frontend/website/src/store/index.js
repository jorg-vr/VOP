import Vue from 'vue'
import Vuex from 'vuex'

import fleet from './modules/fleet'
import vehicles from './modules/vehicles'
import client from './modules/client'

Vue.use(Vuex)

export default new Vuex.Store({
    modules : {
        fleet,
        vehicles,
        client
    }
})