import * as types from '../constants/mutationTypes'
import * as locations from '../constants/locations'
import Vue from 'vue'

export default {

	state: {
		loginInfo: {},
		showLogin: true,
		showError: false,
		nextRoute: {path: null}

	},
	getters:{
		loginInfo(state) {
            return state.loginInfo
        },
        showLogin(state){
        	return state.showLogin
        },
        showError(state){
        	return state.showError
        },
		nextRoute(state){
        	return state.nextRoute
		}
	},
	mutations:{
		register (state,login) {
			// hide login modal
		  state.showLogin=false
		  // store login information
	      state.loginInfo=login
	    },
	    error (state,value){
	    	// show error message if true, do not show if false
	    	state.showError=value
	    },
	    setNextRoute(state, {route}){
	    	state.nextRoute = route;
		}
	},
	actions:{
		registerLogin(context,login){
			console.log('Store registerLogin')
			// API CALL TO AUTHENICATE
				// success
				//context.commit('error',false) // Don't show error message
				// store info
				context.commit('register',login)
				// FAIL
				context.commit('error',true) // Show error messag
			
        }
	}

}