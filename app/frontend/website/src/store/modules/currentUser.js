import * as locations from '../../constants/locations'
import {PagePermissions} from '../../constants/PagePermissions'
import RequestHandler from '../../api/requestHandler'
import Vue from 'vue'

export default {
    state: {
        authToken: null, //Authentication token for the current session.
        activeAccount: null, //Currently authenticated account
        activeFunction: null, //Current function of the active account.
        activePermissions: [] //Permissions for the role of the active function
    },
    getters: {
        /**
         * @param state
         * @returns {boolean} True if the user has an active account
         */
        hasActiveAccount(state){
            return state.activeAccount !== null
        },

        /**
         * @param state
         * @returns {null} Currently authenticated account
         */
        activeAccount(state){
            return state.activeAccount
        },

        /**
         * @param state
         * @returns {null} Current function of the active account.
         */
        activeFunction(state){
            return state.activeFunction
        },

        /**
         * @param state
         * @returns {Array} Permissions for the role of the active function
         */
        activePermissions(state){
            return state.activePermissions
        },

        /**
         * Checks if the currently authenticated user has permissions for the route with the given name.
         * @param state
         * @param getters
         * @returns {boolean}  True if the user has access to the given route
         */
        hasPermissionForRoute: (state, getters) => (routeName) => {
            if(PagePermissions[routeName]){
                return getters.hasPermission(PagePermissions[routeName])
            }
            else { //If a route doesn't have a permission requirement. The user should always get access.
                return true
            }
        },

        //
        /**
         * Checks if the user has a given permission requirement. The permission requirement should have a resource key and actions value.
         * @param permissionRequirement The permission which needs to be checked.
         * @returns {boolean} True if the user has the given permission requirement.
         */
        hasPermission: (state) => (permissionRequirement) => {
            return findUserPermissions(state, permissionRequirement).length > 0
        },

        /**
         * Checks if the user has access the ALL version of the given actions for the given resource.
         * @param resource The resource to check authorization for
         * @param actions The actions to check authorization for
         * @returns {boolean} True if the user is authorized to ACTION_ALL for resource
         */
        isAuthorizedForAllResources: (state, getters) => (resource, actions) => {
            let permissions = findUserPermissions(state, {resource: resource, actions: actions})
            let actionAll = actions.values[1]
            if(permissions.length === 1){
                return permissions[0].action === actionAll
            }
            else {
                return false
            }
        },

        /**
         * Checks if the user can do the given action for only his own resources.
         * @param resource The resource to check authorization for
         * @param actions The actions to check authorization for
         * @returns {boolean} True if the user is authorized to  ACTION_MINE and not ACTION_ALL for resource
         */
        isAuthorizedForOwnResourcesButNotAll: (state, getters) => (resource, actions) => {
            let permissions = findUserPermissions(state, {resource: resource, actions: actions})
            let actionMine = actions.values[0]
            if(permissions.length === 1){
                return permissions[0].action === actionMine
            }
            else {
                return false
            }
        }
    },
    mutations: {
        /**
         * Sets the authentication token for the current session.
         * @param state
         * @param authToken
         */
        setAuthToken(state, {authToken}){
            state.authToken = authToken
            localStorage.setItem('authToken', authToken)
            Vue.http.headers.common['Authorization'] = authToken
        },

        /**
         * Sets the authenticated account.
         * @param state
         * @param activeAccount
         */
        setActiveAccount(state, activeAccount){
            state.activeAccount = activeAccount
        },

        /**
         * Sets the active function of the authenticated account.
         * @param state
         * @param activeFunction
         */
        setActiveFunction(state, activeFunction){
            state.activeFunction = activeFunction
            let functionId = null
            if(activeFunction) {
                functionId = activeFunction.id
            }
            localStorage.setItem('functionId', functionId)
            Vue.http.headers.common['Function'] = functionId
        },

        /**
         * Sets the permissions of of the active function.
         * @param state
         * @param permissions
         */
        setActivePermissions(state, permissions){
            state.activePermissions = permissions
        }
    },
    actions: {
        /**
         * Tries to authenticate the user with the given credentials.
         * @param context
         * @param credentials: An object with a login and password property
         * @returns {Promise}
         */
        authenticate(context, credentials){
            return new Promise(resolve => {
                RequestHandler.postObjectRequest(locations.LOGIN, credentials).then(response => {
                    response.bodyText.promise.then(token => {
                        context.commit('setAuthToken', {authToken: token});
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

        /**
         * Tries to refresh the latest token of the user.
         * @param context
         * @returns {Promise}
         */
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

        /**
         * Fetches the account of the authenticated user.
         * @param context
         * @returns {Promise}
         */
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

        /**
         * Fetches the permissions of the active function.
         * @param context
         * @param activeFunction
         * @returns {Promise}
         */
        fetchPermissionsFunction(context, activeFunction){
            return new Promise(resolve => {
                RequestHandler.getObjectsRequest(locations.ROLE + activeFunction.role + '/' + locations.PERMISSIONS)
                    .then(permissions => {
                        context.commit('setActivePermissions', permissions)
                        resolve(permissions)
                    })
            })
        },

        /**
         * Sets the active function (This is not a mutation as the permissions for the new function has to be fetched).
         * @param context
         * @param activeFunction
         * @returns {Promise}
         */
        setActiveFunction(context, activeFunction){
            context.commit('setActiveFunction', activeFunction)
            return new Promise(resolve => {
                context.dispatch('fetchPermissionsFunction', activeFunction).then(() => {
                    resolve()
                })
            })
        },

        /**
         * Logs out the the user. (Removes all authentication state).
         * @param context
         */
        logout(context){
            context.commit('setAuthToken', {authToken: null})
            context.commit('setActiveAccount', null)
            context.commit('setActivePermissions', [])
        }

    }
}

/**
 * Searches for a list of permissions which satisfy the given requirement
 * @param state
 * @param permissionRequirement
 * @returns {Array.<*>}
 */
let findUserPermissions = function(state, permissionRequirement){
    let permissions = state.activePermissions
    //Check if the user has the given permission requirement.
    let filtered = permissions.filter(permission => {
        if(permissionRequirement.resource.value === permission.resource
            && permissionRequirement.actions.values.indexOf(permission.action) !== -1){
            return permission
        }
    })
    return filtered
}