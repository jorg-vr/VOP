import * as types from '../mutationTypes'
import * as locations from '../locations'
import RequestHandler from '../../api/RequestHandler'
import Vue from 'vue'

export default {
    state: {
        vehicles: [],
        vehicle: {},
        vehicleTypes: []
    },
    getters: {
        vehicles(state) {
            return state.vehicles
        },

        vehicle(state) {
            return state.vehicle
        },

        vehicleTypes(state) {
            return state.vehicleTypes
        }
    },
    mutations: {
        [types.RECEIVE_VEHICLES] (state, {vehicles}){
            state.vehicles = vehicles
        },

        [types.RECEIVE_VEHICLE] (state, {vehicle}){
            state.vehicle = vehicle
        },

        [types.CREATE_VEHICLE] (state, {vehicle}){
            state.vehicles.push(vehicle)
        },

        [types.DELETE_VEHICLE] (state, {id}){
            state.vehicles = state.vehicles.filter(vehicle => vehicle.id !== id);
        },

        [types.RECEIVE_VEHICLE_TYPES] (state, {vehicleTypes}){
            state.vehicleTypes = vehicleTypes
        }
    },
    actions: {
        getVehicles(context, {fleetId}){
            return new Promise(resolve => {
                let query = ''
                if(fleetId){
                    query = '?fleet=' + fleetId
                }
                RequestHandler.getObjectsRequest(locations.VEHICLE + query).then(vehicles => {
                    context.commit(types.RECEIVE_VEHICLES, {vehicles})
                    resolve(vehicles)
                })
            })
        },

        getVehicle(context, {id}){
            return new Promise(resolve => {
                RequestHandler.getObjectRequest(locations.VEHICLE, id).then(vehicle => {
                    context.commit(types.RECEIVE_VEHICLE, {vehicle})
                    resolve(vehicle)
                })
            })
        },

        createVehicle(context, {vehicle}){
            return new Promise(resolve => {
                RequestHandler.postObjectRequest(locations.VEHICLE, vehicle).then(newVehicle => {
                    context.commit(types.CREATE_VEHICLE, {newVehicle})
                    resolve(newVehicle)
                })
            })
        },

        updateVehicle(context, {vehicle}){
            return new Promise(resolve => {
                RequestHandler.putObjectRequest(locations.VEHICLE, vehicle).then(updatedVehicle => {
                    resolve(updatedVehicle)
                })
            })
        },

        deleteVehicle(context, {id}){
            return new Promise(resolve => {
                RequestHandler.deleteObjectRequest(locations.VEHICLE, id).then(() => {
                    context.commit(types.DELETE_VEHICLE, {id})
                    resolve()
                }, id)
            })
        },

        getVehicleTypes(context){
            return new Promise(resolve => {
                RequestHandler.getObjectsRequest(locations.VEHICLE_TYPES).then(vehicleTypes => {
                    context.commit(types.RECEIVE_VEHICLE_TYPES, {vehicleTypes})
                    resolve(vehicleTypes)
                })
            })
        }
    }
}
