import * as types from '../mutationTypes'
import * as locations from '../locations'
import RequestHandler from '../../api/RequestHandler'

export default {
    state: {
        clients: []
    },
    getters: {
        clients(state) {
            return state.clients
        }
    },
    mutations: {
        [types.RECEIVE_CLIENTS] (state, {clients}){
            state.clients = clients
        }
    },
    actions: {
        fetchClients(context){
            return new Promise(resolve => {
                RequestHandler.getObjectsRequest(locations.CLIENT).then(clients => {
                    context.commit(types.RECEIVE_CLIENTS, {clients})
                    resolve(clients)
                })
            })
        }
    }
}
