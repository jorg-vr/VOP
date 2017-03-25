import * as types from '../mutationTypes'
import * as locations from '../locations'
import RequestHandler from '../../api/RequestHandler'
import Vue from 'vue'

export default {
    state: {
        vehicles: [],
        vehicle: {},
        vehicleTypes: [],
        vehicleType: {}
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
        },

        vehicleType(state){
            return state.vehicleType
        },

        getVehicleById: (state) => (id) => {
            return state.vehicles.filter(obj => obj.id === id)[0]
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
        },

        [types.RECEIVE_VEHICLE_TYPE] (state, {vehicleType}){
            state.vehicleType = vehicleType
        }
    },
    actions: {
        fetchVehicles(context, {fleetId}){
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

        fetchVehicle(context, {id}){
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
                    let vehicle = context.getters.getVehicleById(id)
                    context.commit(types.DELETE_VEHICLE, {id})
                    context.commit(types.REMOVE_VEHICLE_FROM_SUBFLEETS, {vehicle: vehicle})
                    resolve()
                }, id)
            })
        },

        fetchVehicleTypes(context){
            return new Promise(resolve => {
                RequestHandler.getObjectsRequest(locations.VEHICLE_TYPES).then(vehicleTypes => {
                    context.commit(types.RECEIVE_VEHICLE_TYPES, {vehicleTypes})
                    resolve(vehicleTypes)
                })
            })
        },

        fetchVehicleType(context, {id}){
            return new Promise(resolve => {
                RequestHandler.getObjectRequest(locations.VEHICLE_TYPES, id).then(vehicleType => {
                    context.commit(types.RECEIVE_VEHICLE_TYPE, {vehicleType})
                    resolve(vehicleType)
                })
            })
        }
    }
}
