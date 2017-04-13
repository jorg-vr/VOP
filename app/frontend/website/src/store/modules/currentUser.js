import * as locations from '../constants/locations'
import {resources} from '../constants/resources'
import RequestHandler from '../../api/requestHandler'
import Vue from 'vue'

export default {
    state: {
        authToken: null,
        activeAccount: null, //Current authenticated activeAccount
        activeFunction: null,
        activePermissions: []
    },
    getters: {
        hasActiveAccount(state){
            return state.activeAccount !== null
        },
        activeAccount(state){
            return state.activeAccount
        },
        activeFunction(state){
            return state.activeFunction
        },
        activePermissions(state){
            return state.activePermissions
        },

        hasPermissionForRoute: (state) => (routeName) => {
            let permissions = state.activePermissions
            if(resources[routeName]){
                let permissionRequirement = resources[routeName]
                //Check if the user has the given permission requirement.
                let filtered = permissions.filter(permission =>
                    (permissionRequirement.resource === permission.resource && permissionRequirement.actions.indexOf(permission.action) !== -1))
                return filtered.length > 0
            }
            else { //If there's no permissions for a route. The user should get access
                return true
            }
        }
    },
    mutations: {
        setAuthToken(state, {authToken}){
            state.authToken = authToken
            localStorage.setItem('authToken', authToken)
            Vue.http.headers.common['Authorization'] = authToken
        },

        setActiveAccount(state, activeAccount){
            state.activeAccount = activeAccount
        },

        setActiveFunction(state, activeFunction){
            state.activeFunction = activeFunction
            let functionId = null
            if(activeFunction) {
                functionId = activeFunction.id
            }
            localStorage.setItem('functionId', functionId)
            Vue.http.headers.common['Function'] = functionId
        },

        setActivePermissions(state, permissions){
            state.activePermissions = permissions
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
                RequestHandler.getObjectRequest(locations.CURRENT_USER, '').then(activeAccount => {
                    context.commit('setActiveAccount', activeAccount)
                    let functionId = localStorage.getItem('functionId')
                    if(functionId==='null' || functionId === null){
                        context.dispatch('fetchUserFunctions').then(activeFunctions => {
                            //Set a default function. At the moment this is the first function in the list.
                            context.dispatch('setActiveFunction', activeFunctions[0])
                        }).then(() => {
                            resolve(activeAccount)
                        })
                    }
                    else {
                        context.dispatch('fetchUserFunction', {id: functionId}).then(activeFunction => {
                            context.dispatch('setActiveFunction', activeFunction)
                        }).then(() => {
                            resolve(activeAccount)
                        })
                    }
                })
            })
        },

        fetchPermissionsFunction(context, activeFunction){
            return new Promise(resolve => {
                RequestHandler.getObjectsRequest(locations.ROLE + activeFunction.role + '/' + locations.PERMISSIONS)
                    .then(permissions => {
                        context.commit('setActivePermissions', permissions)
                        resolve(permissions)
                    })
            })
        },

        setActiveFunction(context, activeFunction){
            context.commit('setActiveFunction', activeFunction)
            context.dispatch('fetchPermissionsFunction', activeFunction)
        },

        logout(context){
            context.commit('setAuthToken', {authToken: null})
            context.commit('setActiveAccount', null)
            context.commit('setActivePermissions', [])
        }

    }
}