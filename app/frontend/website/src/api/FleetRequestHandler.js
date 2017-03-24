import RequestHandler from './RequestHandler'

const url = 'fleets/';

export default {
    getFleetsRequest(process){
        RequestHandler.getRequest(url).then(response => {
            process(response.body.data)
        })
    },

    deleteFleetRequest(process, fleetId){
        RequestHandler.deleteRequest(url + fleetId).then(response => {
            process()
        })
    }
}

