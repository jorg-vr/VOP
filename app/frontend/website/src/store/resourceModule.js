/* Abstract file for managing a resource
 * The following basic operations are supported.
 * 'Resource(s)' will be replaced with the name of the resource
 * ====================
 * STATE:
 * ====================
 * resource:
 * resources:
 * filteredResources:
 * ====================
 * GETTERS:
 * ====================
 * resource: Returns this resource
 * resources: Returns a list of this resource
 * filteredResources: Returns a filtered list of this resource
 * getResourcesByAll(value): Returns a filtered list of this resource, filtered with the given value
 * getResourcesByAllAdvanced(object): Returns a filtered list of this resource, filtered with the given object
 * ====================
 * MUTATIONS:
 * ====================
 * setResource(resource):  Sets the resource as the given resource
 * setResources(resources) : Sets the list of resources as the given resources
 * clearResources(): Clears the list of resources and the filtered list of resources
 * setFilteredResources(resources): Sets the filtered list of resources as the given resources
 * removeResource(resource): Removes the given resource from the list of resources
 * ====================
 * ACTIONS:
 * ====================
 * fetchResource({id}): Fetches the resource with the given ID
 * fetchResources(): Fetches a list with all of the resources
 * fetchResourcesBy(filters): Fetches a list with all of the resources filtered with the given filter object
 * createResource(resource): Creates a resource with the values of the given resource
 * updateResource(resource): Updates a resource with the values of the given resource
 * deleteResource({id}): Deletes the resource with the given ID
 * ====================
 *
 * A module can be created with the function "intializeModule(location, name)"
 * This function takes the location of the resource, for example: "fleets/",
 * and takes the name of the resource as argument, for example "fleet".
 * */
import RequestHandler from '../api/requestHandler'


let capitalize = function(value) {
    return value.charAt(0).toUpperCase() + value.slice(1);
}

export default {
    //Create a new module with all of the basic operations 
    initializeModule(location, name){
        let names = name + 's'
        let capName = capitalize(name)
        let capNames = capitalize(names)
        let filteredNames ='filtered' + capNames
        let getResourceByAll = 'get' + capNames + 'ByAll'
        let getResourceByAllAdvanced = getResourceByAll + 'Advanced'
        let setResources = 'set' + capNames
        let setResource = 'set' + capName
        let setFilteredResources = 'setFiltered' + capNames
        let clearResources = 'clear' + capNames
        let removeResource = 'remove' + capName
        let fetchResource = 'fetch' + capName
        let fetchResources = 'fetch' + capNames
        let fetchResourcesBy = fetchResources + 'By'
        let createResource = 'create' + capName
        let updateResource = 'update' + capName
        let deleteResource = 'delete' + capName
        module = {
            state: {},
            getters: {},
            mutations: {},
            actions: {}
        }
        module.state[name] = null
        module.state[names] = []
        module.state[filteredNames] = []
        module.getters[name] = (state) => {
            return state[name]
        }
        module.getters[names] = (state) => {
            return state[names]
        }
        module.getters[filteredNames] = (state) => {
            return state[filteredNames]
        }
        module.getters[getResourceByAll] = (state, getters) =>  (value) => {
            return getters.filterByAll(state[capNames], value)
        }
        module.getters[getResourceByAllAdvanced] = (state, getters) => (object) => {
            return getters.filterByAllAdvanced(state[capNames], object)
        }
        module.mutations[setResource] = (state, payload) => {
            state[name] = payload
        }
        module.mutations[setResources] = (state, payload) => {
            state[names] = payload
        }
        module.mutations[setFilteredResources] = (state, payload) => {
            state[filteredNames] = payload
        }
        module.mutations[clearResources] = (state) => {
            state[names] = []
            state[filteredNames] = []
        }
        module.mutations[removeResource] = (state, payload) => {
            state[names] = state[names].filter(resource => resource.id !== payload.id)
            state[filteredNames] = state[filteredNames].filter(resource => resource.id !== payload.id)
        }
        module.actions[fetchResources] = (context) => {
            //Empty the previous list of resources.
            context.commit(clearResources)
            return new Promise((resolveSuccess, resolveFailure) => {
                RequestHandler.getObjectsRequest(location).then(resources => {
                    context.commit(setResources, resources)
                    //Initially the filtered resources should equal the actual resources.
                    context.commit(setFilteredResources, resources)
                    resolveSuccess(resources)
                }, response => {
                    resolveFailure(response)
                })
            })
        }
        module.actions[fetchResourcesBy] = (context, filters) => {
            //Empty the previous list of resources.
            context.commit(clearResources)
            return new Promise((resolveSuccess, resolveFailure) => {
                RequestHandler.getObjectsRequestBy(location, filters).then(resources => {
                    context.commit(setResources, resources)
                    //Initially the filtered resources should equal the actual resources.
                    context.commit(setFilteredResources, resources)
                    resolveSuccess(resources)
                }, response => {
                    resolveFailure(response)
                })
            })
        }
        module.actions[fetchResource] = (context, {id}) => {
            return new Promise((resolveSuccess, resolveFailure) => {
                RequestHandler.getObjectRequest(location, id).then(resource => {
                    context.commit(setResource, resource)
                    resolveSuccess(resource)
                }, response => {
                    resolveFailure(response)
                })
            })
        }
        module.actions[createResource] = (context, resource) => {
            return new Promise((resolveSuccess, resolveFailure) => {
                RequestHandler.postObjectRequest(location, resource).then(createdResource => {
                    resolveSuccess(createdResource)
                }, response => {
                    resolveFailure(response)
                })
            })
        }
        module.actions[updateResource] = (context, resource) => {
            return new Promise((resolveSuccess, resolveFailure) => {
                RequestHandler.putObjectRequest(location, resource).then(updatedResource => {
                    resolveSuccess(updatedResource)
                }, response => {
                    resolveFailure(response)
                })
            })
        }
        module.actions[deleteResource] = (context, {id}) => {
            return new Promise((resolveSuccess, resolveFailure) => {
                RequestHandler.deleteObjectRequest(location, id).then(() => {
                    context.commit(removeResource, {id})
                    resolveSuccess()
                }, response => {
                    resolveFailure(response)
                }, id)
            })
        }
        return module
    }
}

