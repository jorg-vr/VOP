import * as locations from '../../constants/locations'
import RequestHandler from '../../api/requestHandler'
import Vue from 'vue'

export default {
    actions:{
        createCorrection(context,payload){
            console.log('createCorrection')
            console.log(locations.CLIENT+payload.companyId+'/'+locations.CORRECTION)
            console.log(payload.resource)
/*            return new Promise((resolveSuccess, resolveFailure) => {
                RequestHandler.postObjectRequest(locations.CLIENT+payload.companyId+'/'+locations.CORRECTION, payload.resource).then(createdResource => {
                    resolveSuccess(createdResource.body)
                }, response => {
                    resolveFailure(response)
                })
            })*/

        }

    }
}
