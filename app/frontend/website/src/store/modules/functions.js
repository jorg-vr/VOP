/**
 * Created by Jarre on 9-4-2017.
 */
import * as types from '../constants/mutationTypes'
import * as locations from '../constants/locations'
import RequestHandler from '../../api/requestHandler'

export default {
    state: {
        userFunctions: [],
        userFunction: {},
    },
    getters: {
        userFunctions(state) {
            return state.userFunctions
        },

        userFunction(state) {
            return state.userFunction
        }
    },
    mutations: {
        [types.RECEIVE_USER_FUNCTIONS] (state, {userFunctions}){
            state.userFunctions = userFunctions
        },

        [types.RECEIVE_USER_FUNCTION] (state, {userFunction}){
            state.userFunction = userFunction
        },

        [types.CREATE_USER_FUNCTION] (state, {userFunction}){
            state.userFunctions.push(userFunction)
        },

        [types.DELETE_USER_FUNCTION] (state, {id}){
            state.userFunctions = state.userFunctions.filter(userFunction => userFunction.id !== id)
        }
    },
    actions: {
        fetchUserFunctions(context){
            return new Promise(resolve => {
                RequestHandler.getObjectsRequest(locations.USER_FUNCTIONS).then(userFunctions => {
                    context.commit(types.RECEIVE_USER_FUNCTIONS, {userFunctions})
                    resolve(userFunctions)
                })
            })
        },

        fetchUserFunction(context, {id}){
            return new Promise(resolve => {
                RequestHandler.getObjectRequest(locations.USER_FUNCTIONS, id).then(userFunction => {
                    context.commit(types.RECEIVE_USER_FUNCTION, {userFunction})
                    resolve(userFunction)
                })
            })
        },

        createUserFunction(context, {userFunction}){
            return new Promise(resolve => {
                RequestHandler.postObjectRequestGetBody(locations.USER_FUNCTIONS, userFunction).then(newUserFunction => {
                    context.commit(types.CREATE_USER_FUNCTION, {newUserFunction})
                    resolve(newUserFunction)
                })
            })
        },

        updateUserFunction(context, {userFunction}){
            return new Promise(resolve => {
                RequestHandler.putObjectRequest(locations.USER_FUNCTIONS, userFunction).then(updatedUserFunction => {
                    resolve(updatedUserFunction)
                })
            })
        },

        deleteUserFunction(context, {id}){
            return new Promise(resolve => {
                RequestHandler.deleteObjectRequest(locations.USER_FUNCTIONS, id).then(() => {
                    context.commit(types.DELETE_USER_FUNCTION, {id})
                    resolve()
                }, id)
            })
        }
        /*
        addRoleNamesToFunctions(context){
            let userFunctions = context.state.userFunctions
            context.dispatch('fetchRoles').then(roles => {
                console.log(roles)
            })
            for(let i=0; i<userFunctions.length; i++){

            }
        }
        ¨*/
    }
}
