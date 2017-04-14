/**
 * Created by Jarre on 14-4-2017.
 */

export default {
    actions: {
        fetchClientsWithType(context, type){
            return new Promise(resolve => {
                context.dispatch('fetchClientsBy', {type: type.value}).then(clients => {
                    resolve(clients)
                })
            })
        }
    }
}