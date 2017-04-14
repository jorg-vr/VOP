import * as locations from '../constants/locations'
import RequestHandler from '../../api/requestHandler'
import Vue from 'vue'

export default {
    state: {
        authToken: null,
        account: null, //Current authenticated account
        activeFunction: null
    },
    getters: {
        hasActiveAccount(state){
            return state.account !== null
        },
        account(state){
            return state.account
        },
        activeFunction(state){
            return state.activeFunction
        }
    },
    mutations: {
        setAuthToken(state, {authToken}){
            state.authToken = authToken
            localStorage.setItem('authToken', authToken)
            Vue.http.headers.common['Authorization'] = authToken
        },

        setActiveAccount(state, account){
            state.account = account
        },

        setActiveFunction(state, activeFunction){
            state.activeFunction = activeFunction
            let functionId = null
            if(activeFunction) {
                functionId = activeFunction.id
            }
            localStorage.setItem('functionId', functionId)
            Vue.http.headers.common['Function'] = functionId
        }
    },
    actions: {

        //Credentials has to contain a key 'login' and 'password'
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
                    context.commit('setActiveAccount', account)
                    let functionId = localStorage.getItem('functionId')
                    if(functionId==='null'||functionId===null){
                        console.log("if");
                        context.dispatch('fetchUserFunctions').then(activeFunctions => {
                            //Set a default function. At the moment this is the first function in the list.
                            context.commit('setActiveFunction', activeFunctions[0])
                        }).then(() => {
                            resolve(account)
                        })
                    }
                    else {
                        context.dispatch('fetchUserFunction', {id: functionId}).then(activeFunction => {
                            context.commit('setActiveFunction', activeFunction)
                        }).then(() => {
                            resolve(account)
                        })
                    }
                })
            })
        },
        logout(context){
            context.commit('setAuthToken', {authToken: null})
            context.commit('setActiveAccount', null)
        }

    }
}