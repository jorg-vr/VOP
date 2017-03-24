import Vue from 'vue'
import Vuex from 'vuex'

import fleet from './modules/fleet'

Vue.use(Vuex)

export default new Vuex.Store({
    modules : {
        fleet
    }
})