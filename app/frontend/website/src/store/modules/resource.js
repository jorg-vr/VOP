/* Abstract file for managing a resource */
import RequestHandler from '../../api/requestHandler'

export default {
    //Create a new module with all of the basic operations 
    initialise(location, name){
        let names = name + 's'
        let capname = name.capitalize()
        let capnames = names.capitalize()
        let setResources = 'set' + capnames
        let setResource = 'set' + capname
        let removeResource = 'remove' + capname
        let fetchResource = 'fetch' + capname
        let fetchResources = 'fetch' + capnames
        let createResource = 'create' + capname
        let updateResource = 'update' + capname
        let deleteResource = 'delete' + capname
        this.state[name] = null
        this.state[names] = null
        this.getters[name] = (state) => {
            return state[name]
        }
        this.getters[names] = (state) => {
            return state[names]
        }
        this.mutations[setResource] = (state, payload) => {
            state[name] = payload
        }
        this.mutations[setResources] = (state, payload) => {
            state[names] = payload
        }
        this.mutations[removeResource] = (state, payload) => {
            state[names] = state[names].filter(resource => resource.id !== payload.id)
        }
        this.actions[fetchResources] = (context) => {
            return new Promise(resolve => {
                RequestHandler.getObjectsRequest(location).then(resources => {
                    context.commit(setResources, resources)
                    resolve(resources)
                })
            })
        }
        this.actions[fetchResource] = (context, {id}) => {
            return new Promise(resolve => {
                RequestHandler.getObjectRequest(location, id).then(resource => {
                    context.commit(setResource, resource)
                    resolve(resource)
                })
            })
        }
        this.actions[createResource] = (context, resource) => {
            return new Promise(resolve => {
                RequestHandler.postObjectRequest(location, resource).then(createdResource => {
                    context.commit(setResource, createdResource)
                    resolve(createdResource)
                })
            })
        }

        this.actions[updateResource] = (context, resource) => {
            return new Promise(resolve => {
                RequestHandler.putObjectRequest(location, resource).then(updatedResource => {
                    context.commit(setResource, updatedResource)
                    resolve(updatedResource)
                })
            })
        }

        this.actions[deleteResource] = (context, {id}) => {
            return new Promise(resolve => {
                RequestHandler.deleteObjectRequest(location, id).then(() => {
                    context.commit(types.DELETE_RESOURCE, {id})
                    resolve()
                }, id)
            })
        }
    },


    state: {},
    getters: {},
    mutations: {},
    actions: {}
}

