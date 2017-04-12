import * as locations from '../constants/locations'
import RequestHandler from '../../api/requestHandler'
import Vue from 'vue'

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
        setAuthToken(state, {authToken}){
            state.authToken = authToken
            localStorage.setItem('authToken', authToken)
            Vue.http.headers.common['Authorization'] = authToken
        },

        setActiveAccount(state, {account}){
            state.account = account
        },
        setActiveFunction(state, {accountFunction}){
            state.accountFunction = accountFunction
            if(accountFunction){
                Vue.http.headers.common['Function'] = accountFunction.id
            }
            else {
                Vue.http.headers.common['Function'] = null
            }
        }
    },
    actions: {

        //Credentials has to contain a key 'login' and 'password'
        //TODO handle failure
        authenticate(context, credentials){
            return new Promise(resolve => {
                RequestHandler.postObjectRequest(locations.LOGIN, credentials).then(response => {
                    response.bodyText.promise.then(token => {
                        context.commit('setAuthToken', {authToken: token})
                    })
                }, () =>  { //failure
                    resolve()
                }).then(() => {
                    context.dispatch('fetchAccount').then(() => {
                        resolve()
                    })
                })
            })
        },

        refreshToken(context){
            return new Promise((resolveSuccess, resolveFailure) => {
                RequestHandler.postObjectRequest(locations.REFRESH, {}).then(response => {
                    response.bodyText.promise.then(token => {
                        context.commit('setAuthToken', {authToken: token})
                    })
                }, () => { //failure
                    resolveFailure()
                }).then(() => {
                    context.dispatch('fetchAccount').then(() => {
                        resolveSuccess()
                    })
                })
            })
        },

        //Precondition: user has an active authToken
        fetchAccount(context){
            return new Promise(resolve => {
                RequestHandler.getObjectRequest(locations.CURRENT_USER, '').then(account => {
                    context.commit('setActiveAccount', {account: account})
                    context.dispatch('fetchUserFunctions').then(accountFunctions => {
                        //Set a default function
                        context.commit('setActiveFunction', {accountFunction: accountFunctions[0]})
                    }).then(() => {
                        resolve(account)
                    })
                })
            })
        },
        logout(context){
            context.commit('setAuthToken', {authToken: null})
            context.commit('setActiveAccount', {account: null})
            context.commit('setActiveFunction', {accountFunction: null})
        }

    }
}