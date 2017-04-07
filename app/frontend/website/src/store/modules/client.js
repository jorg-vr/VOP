import * as types from '../constants/mutationTypes'
import * as locations from '../constants/locations'
import RequestHandler from '../../api/requestHandler'

export default {
    state: {
        clients: [],
        client: {},
        filteredClients: [],
    },
    getters: {
        clients(state) {
            return state.clients
        },

        filteredClients(state){
            return state.filteredClients
        },

        client(state) {
            return state.client
        },

        getClientsByAll: (state, getters) => (value) => {
            return getters.filterByAll(state.clients, value)
        },

        getClientsByAllAdvanced: (state, getters) => (client) => {
            return getters.filterByAllAdvanced(state.clients, client)
        }
    },
    mutations: {
        [types.RECEIVE_CLIENTS] (state, {clients}){
            state.clients = clients
        },

        [types.RECEIVE_CLIENT] (state, {client}){
            state.client = client
        },

        [types.UPDATE_FILTERED_CLIENTS] (state, {clients}){
            state.filteredClients = clients
        },

        [types.CREATE_CLIENT] (state, {client}){
            state.clients.push(client)
            state.filteredClients.push(client)
        },

        [types.DELETE_CLIENT] (state, {id}){
            state.clients = state.clients.filter(client => client.id !== id)
            state.filteredClients = state.filteredClients.filter(client => client.id !== id)
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
        },

        fetchClient(context, {id}){
            return new Promise(resolve => {
                RequestHandler.getObjectRequest(locations.CLIENT, id).then(client => {
                    context.commit(types.RECEIVE_CLIENT, {client})
                    resolve(client)
                })
            })
        },

        createClient(context, {client}){
            return new Promise(resolve => {
                RequestHandler.postObjectRequestGetBody(locations.CLIENT, client).then(newClient => {
                    context.commit(types.CREATE_CLIENT, {newClient})
                    resolve(newClient)
                })
            })
        },

        updateClient(context, {client}){
            return new Promise(resolve => {
                RequestHandler.putObjectRequest(locations.CLIENT, client).then(updatedClient => {
                    resolve(updatedClient)
                })
            })
        },

        deleteClient(context, {id}){
            return new Promise(resolve => {
                RequestHandler.deleteObjectRequest(locations.CLIENT, id).then(() => {
                    context.commit(types.DELETE_CLIENT, {id})
                    resolve()
                }, id)
            })
        },
    }
}
