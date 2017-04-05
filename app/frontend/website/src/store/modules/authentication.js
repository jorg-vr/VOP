import * as locations from '../constants/locations'
import * as types from '../constants/mutationTypes'
import RequestHandler from '../../api/requestHandler'
import Vue from 'vue'

export default {
    state: {
        authToken: null,
        account: null //Current authenticated account
    },
    getters: {
        hasActiveAccount(state){
            return state.account != null
        }
    },
    mutations: {
        [types.SET_AUTH_TOKEN] (state, {authToken}){
            state.authToken = authToken
            Vue.http.headers.common['AuthToken'] = authToken
        },

        [types.SET_ACTIVE_ACCOUNT] (state, {account}){
            state.account = account
            Vue.http.headers.common['Function'] = account.id
        }
    },
    actions: {

        //Credentials has to contain a key 'login' and 'password'
        //TODO handle failure
        authenticate(context, credentials){
            RequestHandler.postObjectRequest(locations.AUTHENTICATION, credentials).then(token => {
                context.commit(types.SET_AUTH_TOKEN, {authToken: token})
            })
        },

        //Precondition: user has an active authToken
        fetchAccount(context){
            RequestHandler.getObjectsRequest(locations.AUTHENTICATION).then(account => {
                context.commit(types.SET_ACTIVE_ACCOUNT, {account: account})
            })
        }

    }
}