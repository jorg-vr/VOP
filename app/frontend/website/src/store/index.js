import Vue from 'vue'
import Vuex from 'vuex'

import fleet from './modules/fleet'
import client from './modules/client'

Vue.use(Vuex)

export default new Vuex.Store({
    modules : {
        fleet,
        client
    }
})