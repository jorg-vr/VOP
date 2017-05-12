import * as locations from '../../constants/locations'
import RequestHandler from '../../api/requestHandler'

export default {
    actions: {
        fetchLogs(id){
            RequestHandler.getObjectsRequest(locations.LOG, {})
        }
    }
}