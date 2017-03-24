import * as types from '../mutation-types'
import FleetRequestHandler from '../../api/FleetRequestHandler'

export default {
    state: {
        fleets: []
    },
    getters: {
        fleets(state) {
            return state.fleets
        }
    },
    mutations: {
        [types.RECEIVE_FLEETS] (state, {fleets}){
            state.fleets = fleets
        },

        [types.DELETE_FLEET] (state, {fleetId}){
            state.fleets = state.fleets.filter(fleet => fleet.id !== fleetId);
        }

    },
    actions: {
        getFleets({commit}){
            FleetRequestHandler.getFleetsRequest(fleets => {
                commit(types.RECEIVE_FLEETS, {fleets})
            })
        },
        deleteFleet(context, {fleetId}){
            FleetRequestHandler.deleteFleetRequest(() => {
                //Only commit in succes scenario
                context.commit(types.DELETE_FLEET, {fleetId})
            }, fleetId)
        }
    }
}
