import * as locations from '../../constants/locations'
import {resources} from '../../constants/resources'
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

        hasPermissionForRoute: (state, getters) => (routeName) => {
            if(resources[routeName]){
                return getters.hasPermission(resources[routeName])
            }
            else { //If there's no permissions for a route. The user should get access
                return true
            }
        },

        //Checks if the user has a given permission requirement. The permission requirement should have a resource key and actions value.
        hasPermission: (state) => (permissionRequirement) => {
            if(permissionRequirement.resource === "CLIENT"){
                permissionRequirement.resource = "COMPANY"
            }
            let permissions = state.activePermissions
            //Check if the user has the given permission requirement.
            let filtered = permissions.filter(permission => {
                if(permissionRequirement.resource === permission.resource && permissionRequirement.actions.indexOf(permission.action) !== -1){
                    return permission
                }
            })
            return filtered.length > 0
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
                    /**
                     * Check if the user has already chosen a functionId before.
                     * If not choose a random function.
                     * Else fetch the old function.
                     */
                    console.log(functionId)
                    if(functionId==='null' || functionId === null){
                        context.dispatch('fetchUserFunctions').then(activeFunctions => {
                            //Set a default function. At the moment this is the first function in the list.
                            context.dispatch('setActiveFunction', activeFunctions[0]).then(() => {
                                resolve(activeAccount)
                            })
                        })
                    }
                    else {
                        context.dispatch('fetchUserFunction', {id: functionId}).then(activeFunction => {
                            context.dispatch('setActiveFunction', activeFunction).then(() => {
                                resolve(activeAccount)
                            })
                        }, () => {
                            /**
                             * If fetching current user fails, fetch any other function. This will be replaced with
                             * going to a page for choosing a function.
                             */
                            context.dispatch('fetchUserFunctions').then(activeFunctions => {
                                //Set a default function. At the moment this is the first function in the list.
                                context.dispatch('setActiveFunction', activeFunctions[0]).then(() => {
                                    resolve(activeAccount)
                                })
                            })
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
            return new Promise(resolve => {
                context.dispatch('fetchPermissionsFunction', activeFunction).then(() => {
                    resolve()
                })
            })
        },

        logout(context){
            context.commit('setAuthToken', {authToken: null})
            context.commit('setActiveAccount', null)
            context.commit('setActivePermissions', [])
        }

    }
}