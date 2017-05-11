import RequestHandler from '../../api/requestHandler'
import Vue from 'vue'
export default {
    state: {
        commissions: [], //A list of commissions
    },
    getters: {
        /**
         * Get the list of commissions
         * @param state
         * @returns {Array}
         */
        commissions(state) {
            return state.commissions
        }
    },
    mutations: {
        /**
         * Sets the list of filtered commissions
         * @param state
         * @param commissions
         */
        setCommissions(state, commissions){
            state.commissions = commissions
        },

        /**
         * Clear the list of commissions
         * @param state
         */
        clearCommissions(state){
            state.commissions = []
        }
    },
    actions: {

        fetchCommissions(context,payload){
            return new Promise((resolveSuccess, resolveFailure) => {
                Vue.http.get('vehicles/types/274776102288499320837812636171303961393/commissions').then(commissions => {
                    console.log(commissions)
                    context.commit('setCommissions', commissions.body)
                    resolveSuccess(commissions.body)
                }, response => {
                    resolveFailure(response)
                })
            })
        },
    }
}