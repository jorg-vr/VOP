/**
 * Created by Jarre on 24/04/2017.
 */

import resourceModule from '../resourceModule'
import locations from '../../constants/locations'

//The location is initially unknown. So this is left open.
let suretyResource = resourceModule.initializeModule('', 'fleet')

/*
 Fetches info for an insurance in a contract
 Also fetch information about insurance surety and insured vehicle
 @param payload: data containting id of insurance and id of contract
 */
fetchSureties = function(context){
    console.log('fetching sureties')
    return new Promise((resolveSuccess, resolveFailure) => {
        RequestHandler.getObjectsRequest(locations.SURETYDETAIL).then(sureties => {
            context.commit('setSureties',sureties)
            console.log(sureties)
            resolveSuccess(sureties)
        }, response => {
            resolveFailure(response)
        })
    })
}

fetchSurety = function(context,payload){
    // fetch Surety
    return new Promise((resolveSuccess, resolveFailure) => {
        RequestHandler.getObjectRequest(locations.INSURANCE+payload.contractId+'/'+locations.SURETY, payload.id).then(surety => {
            context.commit('setSuretyData', surety)
            // set id for suretyDetailId
            resolveSuccess(surety)
        }, response => {
            resolveFailure(response)
        })
    })
    // fetch surety detail
    console.log(suretyDetailId)
}

fetchSuretyTypes = function(context){
    return new Promise((resolveSuccess, resolveFailure) => {
        RequestHandler.getObjectsRequest(locations.SURETYDETAIL+'types').then(suretyTypes => {
            context.commit('setSuretyTypes',suretyTypes)
            console.log(suretyTypes)
            resolveSuccess(suretyTypes)
        }, response => {
            resolveFailure(response)
        })
    })
}

// payload consist of contract id and input data
deleteSurety = function(context,payload){
    return new Promise((resolveSuccess, resolveFailure) => {
        RequestHandler.deleteObjectRequest(locations.INSURANCE+payload.contractId+'/'+locations.SURETY, payload.id).then(() => {
            context.commit('removeSurety', {id: payload.id})
            resolveSuccess()
        }, response => {
            resolveFailure(response)
        }, payload.id)
    })

}

updateSurety = function(context,payload){
    return new Promise((resolveSuccess, resolveFailure) => {
        RequestHandler.putObjectRequest(locations.INSURANCE+payload.contractId+'/'+locations.SURETY+payload.object.id, payload.object).then(updatedSurety => {
            context.commit('setSurety', updatedSurety)
            resolveSuccess(updatedSurety)
        }, response => {
            resolveFailure(response)
        })
    })
}

createSurety = function(context,payload){
    return new Promise((resolveSuccess, resolveFailure) => {
        RequestHandler.postObjectRequest(locations.INSURANCE+payload.contractId+'/'+locations.SURETY, payload.object).then(createdSurety => {
            context.commit('addContractInsurance', createdSurety)
            resolveSuccess(createdSurety)
        }, response => {
            resolveFailure(response)
        })
    })

}
}
