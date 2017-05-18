import * as locations from '../../constants/locations'
import RequestHandler from '../../api/requestHandler'
import {formatLocation} from '../../utils/utils'

export default {

    actions: {
        updatePermissions(context, {roleId, permissions}){
            return new Promise((resolve, reject) => {
                RequestHandler.putRequest(formatLocation(locations.PERMISSIONS, {id: roleId}), permissions).then(response => {
                    resolve(response)
                }, response => {
                    reject(response)
                })
            })
        }
    }
}
