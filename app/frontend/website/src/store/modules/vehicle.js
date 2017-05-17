import * as locations from '../../constants/locations'
import RequestHandler from '../../api/requestHandler'
import * as utils from '../../utils/utils'

export default {
    actions: {
        fetchGreenCard(context, {id, extention}){
            return new Promise((resolve, reject) => {
                RequestHandler.getObjectsRequest(utils.formatLocation(locations.GREEN_CARD, {id, extention}))
                    .then(response => resolve(response), reject(response))
            })
        }
    }
}