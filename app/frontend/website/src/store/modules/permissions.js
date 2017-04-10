/**
 * Created by Jarre on 9-4-2017.
 */
import * as types from '../constants/mutationTypes'
import * as locations from '../constants/locations'
import RequestHandler from '../../api/requestHandler'

export default {
    state: {
        permissions: [],
        permission: {},
    },
    getters: {
        permissions(state) {
            return state.permissions
        },

        permission(state) {
            return state.permission
        }
    },
    mutations: {
        [types.RECEIVE_PERMISSIONS] (state, {permissions}){
            state.permissions = permissions
        },

        [types.RECEIVE_PERMISSION] (state, {permission}){
            state.permission = permission
        },

        [types.CREATE_PERMISSION] (state, {permission}){
            state.permissions.push(permission)
        },

        [types.DELETE_PERMISSION] (state, {id}){
            state.permissions = state.permissions.filter(permission => permission.id !== id)
        }
    },
    actions: {
        fetchPermissions(context){
            return new Promise(resolve => {
                RequestHandler.getObjectsRequest(locations.PERMISSIONS).then(permissions => {
                    context.commit(types.RECEIVE_PERMISSIONS, {permissions})
                    resolve(permissions)
                })
            })
        },

        fetchPermission(context, {id}){
            return new Promise(resolve => {
                RequestHandler.getObjectRequest(locations.PERMISSIONS, id).then(permission => {
                    context.commit(types.RECEIVE_PERMISSION, {permission})
                    resolve(permission)
                })
            })
        },

        createPermission(context, {permission}){
            return new Promise(resolve => {
                RequestHandler.postObjectRequestGetBody(locations.PERMISSIONS, permission).then(newPermission => {
                    context.commit(types.CREATE_PERMISSION, {newPermission})
                    resolve(newPermission)
                })
            })
        },

        updatePermission(context, {permission}){
            return new Promise(resolve => {
                RequestHandler.putObjectRequest(locations.PERMISSIONS, permission).then(updatedPermission => {
                    resolve(updatedPermission)
                })
            })
        },

        deletePermission(context, {id}){
            return new Promise(resolve => {
                RequestHandler.deleteObjectRequest(locations.PERMISSIONS, id).then(() => {
                    context.commit(types.DELETE_PERMISSION, {id})
                    resolve()
                }, id)
            })
        }
        /*
         addRoleNamesToFunctions(context){
         let permissions = context.state.permissions
         context.dispatch('fetchRoles').then(roles => {
         console.log(roles)
         })
         for(let i=0; i<permissions.length; i++){

         }
         }
         ¨*/
    }
}
