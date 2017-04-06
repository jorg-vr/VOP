import * as locations from '../constants/locations'
import * as types from '../constants/mutationTypes'
import RequestHandler from '../../api/requestHandler'
import Vue from 'vue'

export default {
    state: {
        authToken: null,
        account: null, //Current authenticated account
        login: '', // Needed for data binding in navbar (can't bind to null value in account)
        id: ''  // Needed for data binding in navbar (can't bind to null value in account)
    },
    getters: {
        hasActiveAccount(state){
            return state.account != null
        },
        getAccountInfo(state){
            return {login: state.login, id:state.id}
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
            // set values for navbar data binding
            state.login = account.login
            statel.id = account.id
        },
        [types.RESET_STATE](state){
            // remove webtoken and current authenticated account
            state.authToken = null
            state.account = null
            // remove values for navbar data binding
            state.login= ''
            state.id= ''
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
        },

        logout(context){
            context.commit(types.RESET_STATE)
        }

    }
}