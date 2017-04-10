import * as types from '../constants/mutationTypes'
import * as locations from '../constants/locations'
import RequestHandler from '../../api/requestHandler'

export default {
    state: {
        roles: [],
        role: {},
        permissions: []
    },
    getters: {
        roles(state) {
            return state.roles
        },

        role(state) {
            return state.role
        }
    },
    mutations: {
        [types.RECEIVE_ROLES] (state, {roles}){
            state.roles = roles
        },

        [types.RECEIVE_ROLE] (state, {role}){
            state.role = role
        },
        
        [types.CREATE_ROLE] (state, {role}){
            state.roles.push(role)
        },

        [types.DELETE_ROLE] (state, {id}){
            state.roles = state.roles.filter(role => role.id !== id)
        }
    },
    actions: {
        fetchRoles(context){
            return new Promise(resolve => {
                RequestHandler.getObjectsRequest(locations.ROLE).then(roles => {
                    context.commit(types.RECEIVE_ROLES, {roles})
                    resolve(roles)
                })
            })
        },

        fetchRole(context, {id}){
            return new Promise(resolve => {
                RequestHandler.getObjectRequest(locations.ROLE, id).then(role => {
                    context.commit(types.RECEIVE_ROLE, {role})
                    resolve(role)
                })
            })
        },

        createRole(context, {role}){
            return new Promise(resolve => {
                RequestHandler.postObjectRequestGetBody(locations.ROLE, role).then(newRole => {
                    context.commit(types.CREATE_ROLE, {newRole})
                    resolve(newRole)
                })
            })
        },

        updateRole(context, {role}){
            return new Promise(resolve => {
                RequestHandler.putObjectRequest(locations.ROLE, role).then(updatedRole => {
                    resolve(updatedRole)
                })
            })
        },

        deleteRole(context, {id}){
            return new Promise(resolve => {
                RequestHandler.deleteObjectRequest(locations.ROLE, id).then(() => {
                    context.commit(types.DELETE_ROLE, {id})
                    resolve()
                }, id)
            })
        },
    }
}
