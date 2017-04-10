/* Abstract file for managing a resource */
import RequestHandler from '../../api/requestHandler'


let capitalize = function(value) {
    return value.charAt(0).toUpperCase() + value.slice(1);
}


export default {
    //Create a new module with all of the basic operations 
    initialise(location, name){
        let names = name + 's'
        let capname = capitalize(name)
        let capnames = capitalize(names)
        let setResources = 'set' + capnames
        let setResource = 'set' + capname
        let removeResource = 'remove' + capname
        let fetchResource = 'fetch' + capname
        let fetchResources = 'fetch' + capnames
        let createResource = 'create' + capname
        let updateResource = 'update' + capname
        let deleteResource = 'delete' + capname
        module = {
            state: {},
            getters: {},
            mutations: {},
            actions: {}
        }
        module.state[name] = null
        module.state[names] = null
        module.getters[name] = (state) => {
            return state[name]
        }
        module.getters[names] = (state) => {
            return state[names]
        }
        module.mutations[setResource] = (state, payload) => {
            state[name] = payload
        }
        module.mutations[setResources] = (state, payload) => {
            state[names] = payload
        }
        module.mutations[removeResource] = (state, payload) => {
            state[names] = state[names].filter(resource => resource.id !== payload.id)
        }
        module.actions[fetchResources] = (context) => {
            return new Promise(resolve => {
                RequestHandler.getObjectsRequest(location).then(resources => {
                    context.commit(setResources, resources)
                    resolve(resources)
                })
            })
        }
        module.actions[fetchResource] = (context, {id}) => {
            return new Promise(resolve => {
                RequestHandler.getObjectRequest(location, id).then(resource => {
                    context.commit(setResource, resource)
                    resolve(resource)
                })
            })
        }
        module.actions[createResource] = (context, resource) => {
            return new Promise(resolve => {
                RequestHandler.postObjectRequest(location, resource).then(createdResource => {
                    context.commit(setResource, createdResource)
                    resolve(createdResource)
                })
            })
        }

        module.actions[updateResource] = (context, resource) => {
            return new Promise(resolve => {
                RequestHandler.putObjectRequest(location, resource).then(updatedResource => {
                    context.commit(setResource, updatedResource)
                    resolve(updatedResource)
                })
            })
        }

        module.actions[deleteResource] = (context, {id}) => {
            return new Promise(resolve => {
                RequestHandler.deleteObjectRequest(location, id).then(() => {
                    context.commit(types.DELETE_RESOURCE, {id})
                    resolve()
                }, id)
            })
        }
        return module
    }
}

