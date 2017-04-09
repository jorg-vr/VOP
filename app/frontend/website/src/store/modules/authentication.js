import * as locations from '../constants/locations'
import * as types from '../constants/mutationTypes'
import RequestHandler from '../../api/requestHandler'
import Vue from 'vue'

export default {
    state: {
        authToken: null,
        account: null, //Current authenticated account
    },
    getters: {
        hasActiveAccount(state){
            return state.account != null
        },
        account(state){
            return state.account
        }
    },
    mutations: {
        [types.SET_AUTH_TOKEN] (state, {authToken}){
            state.authToken = authToken
            Vue.http.headers.common['Authorization'] = authToken
        },

        [types.SET_ACTIVE_ACCOUNT] (state, {account}){
            state.account = account
            Vue.http.headers.common['Function'] = account.id
        },
        [types.RESET_STATE](state){
            // remove webtoken and current authenticated account
            state.authToken = null
            state.account = null
        }
    },
    actions: {

        //Credentials has to contain a key 'login' and 'password'
        //TODO handle failure
        authenticate(context, credentials){
            return new Promise(resolve => {
                RequestHandler.postObjectRequest(locations.AUTHENTICATION, credentials).then(response => {
                    response.bodyText.promise.then(token => {
                        context.commit(types.SET_AUTH_TOKEN, {authToken: token})
                        resolve(token)
                    })
                })
            })
        },

        //Precondition: user has an active authToken
        fetchAccount(context){
            return new Promise(resolve => {
                RequestHandler.getObjectsRequestGetBody(locations.CURRENT_USER).then(account => {
                    console.log(account)
                    context.commit(types.SET_ACTIVE_ACCOUNT, {account: account})
                    resolve(account[0])
                })
            })
        },
        logout(context){
            context.commit(types.RESET_STATE)

        }

    }
}