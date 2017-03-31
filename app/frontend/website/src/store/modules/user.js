import * as types from '../constants/mutationTypes'
import * as locations from '../constants/locations'
import RequestHandler from '../../api/requestHandler'
import Vue from 'vue'

export default {
    state: {
        users: [],
        user: {},
        filteredUsers: []
    },
    getters: {
        users(state) {
            return state.users
        },

        filteredUsers(state){
            return state.filteredUsers
        },


        user(state) {
            return state.user
        },

        getUsersByAll: (state, getters) => (value) => {
            return getters.filterByAll(state.users, value)
        },

        getUsersByAllAdvanced: (state, getters) => (user) => {
            return getters.filterByAllAdvanced(state.users, user)
        }
    },
    mutations: {
        [types.RECEIVE_USERS] (state, {users}){
            state.users = users
        },

        [types.RECEIVE_USER] (state, {user}){
            state.user = user
        },

        [types.UPDATE_FILTERED_USERS] (state, {users}){
            state.filteredUsers = users
        },

        [types.CREATE_USER] (state, {user}){
            state.users.push(user)
            state.users.push(user)
        },

        [types.DELETE_USER] (state, {id}){
            state.users = state.users.filter(user => user.id !== id)
            state.filteredUsers = state.filteredUsers.filter(user => user.id !== id)
        }
    },
    actions: {
        fetchUsers(context){
            return new Promise(resolve => {
                RequestHandler.getObjectsRequest(locations.USER).then(users => {
                    context.commit(types.RECEIVE_USERS, {users})
                    resolve(users)
                })
            })
        },

        fetchUser(context, {id}){
            return new Promise(resolve => {
                RequestHandler.getObjectRequest(locations.USER, id).then(user => {
                    context.commit(types.RECEIVE_USER, {user})
                    resolve(user)
                })
            })
        },

        createUser(context, {user}){
            return new Promise(resolve => {
                RequestHandler.postObjectRequest(locations.USER, user).then(newUser => {
                    context.commit(types.CREATE_USER, {newUser})
                    resolve(newUser)
                })
            })
        },

        updateUser(context, {user}){
            return new Promise(resolve => {
                RequestHandler.putObjectRequest(locations.USER, user).then(updatedUser => {
                    resolve(updatedUser)
                })
            })
        },

        deleteUser(context, {id}){
            return new Promise(resolve => {
                RequestHandler.deleteObjectRequest(locations.USER, id).then(() => {
                    context.commit(types.DELETE_USER, {id})
                    resolve()
                }, id)
            })
        },
    }
}
