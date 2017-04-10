import * as locations from '../constants/locations'
import * as types from '../constants/mutationTypes'
import RequestHandler from '../../api/requestHandler'
import Vue from 'vue'
import route from '../../config/routes'

export default {
    state: {
        authToken: null,
        account: null, //Current authenticated account
        accountFunction: null
    },
    getters: {
        hasActiveAccount(state){
            return state.account !== null
        },
        account(state){
            return state.account
        },
        accountFunction(state){
            return state.accountFunction
        }
    },
    mutations: {
        [types.SET_AUTH_TOKEN] (state, {authToken}){
            state.authToken = authToken
            localStorage.setItem('authToken', authToken)
            Vue.http.headers.common['Authorization'] = authToken
            console.log(authToken)
        },

        [types.SET_ACTIVE_ACCOUNT] (state, {account}){
            state.account = account
        },
        [types.SET_ACTIVE_FUNCTION] (state, {accountFunction}){
            state.accountFunction = accountFunction
            Vue.http.headers.common['Function'] = accountFunction.id
            console.log(accountFunction.id)
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
                RequestHandler.postObjectRequest(locations.LOGIN, credentials).then(response => {
                    response.bodyText.promise.then(token => {
                        context.commit(types.SET_AUTH_TOKEN, {authToken: token})
                    })
                }, () =>  { //failure
                    resolve()
                }).then(() => {
                    context.dispatch('fetchAccount').then(() => {
                        resolve()
                    })
                }, () => {
                    alert('test')
                })
            })
        },

        refreshToken(context){
            return new Promise(resolve => {
                RequestHandler.postObjectRequest(locations.REFRESH, {}).then(response => {
                    response.bodyText.promise.then(token => {
                        context.commit(types.SET_AUTH_TOKEN, {authToken: token})
                    })
                }, () => { //failure
                    resolve()
                }).then(() => {
                    context.dispatch('fetchAccount').then(() => {
                        resolve()
                    })
                })
            })
        },

        //Precondition: user has an active authToken
        fetchAccount(context){
            return new Promise(resolve => {
                RequestHandler.getObjectRequest(locations.CURRENT_USER, '').then(account => {
                    context.commit(types.SET_ACTIVE_ACCOUNT, {account: account})
                    context.dispatch('fetchUserFunctions').then(accountFunctions => {
                        //Set a default function
                        context.commit(types.SET_ACTIVE_FUNCTION, {accountFunction: accountFunctions[0]})
                    }).then(() => {
                        resolve(account)
                    })
                })
            })
        },

        selectFunction(){

        },
        logout(context){
            context.commit(types.RESET_STATE)

        }

    }
}