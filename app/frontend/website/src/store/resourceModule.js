/* Abstract file for managing a resource
 * The following basic operations are supported.
 * 'Resource(s)' will be replaced with the name of the resource
 * ====================
 * STATE:
 * ====================
 * resource:
 * resources:
 * ====================
 * GETTERS:
 * ====================
 * resource: Returns this resource
 * resources: Returns a list of this resource
 * getResourcesByAll(value): Returns a filtered list of this resource, filtered with the given value
 * getResourcesByAllAdvanced(object): Returns a filtered list of this resource, filtered with the given object
 * ====================
 * MUTATIONS:
 * ====================
 * setResource(resource):  Sets the resource as the given resource
 * setResources(resources) : Sets the list of resources as the given resources
 * clearResources(): Clears the list of resources and the filtered list of resources
 * removeResource(resource): Removes the given resource from the list of resources
 * ====================
 * ACTIONS:
 * ====================
 * NOTE!!! The ids property in payload represents an object with id values based on the location of this resource.
 * E.g. Location: '/companies/{company_id}/invoices/' => ids: {company_id: 5}
 * fetchResource({id, ids}): Fetches the resource with the given ID
 * fetchResources({ids}): Fetches a list with all of the resources
 * fetchResourcesBy({filters, ids}): Fetches a list with all of the resources filtered with the given filter object
 * createResource({resource, ids}): Creates a resource with the values of the given resource
 * updateResource({resource, ids}): Updates a resource with the values of the given resource
 * deleteResource({id, ids}): Deletes the resource with the given ID
 * ====================
 *
 * A module can be created with the function "intializeModule(location, name)" or
 * "intializeModule(location, name, names)" if the plural is deviant.
 * This function takes the location of the resource, for example: "fleets/",
 * and takes the name of the resource as argument, for example "fleet".
 * */
import RequestHandler from '../api/requestHandler'
import {formatLocation, addShowableDate, capitalize} from '../utils/utils'

export default {
    //Create a new module with all of the basic operations
    /**
     * Create a module for a resource
     * @param location The location of the resource of API calls
     * @param name The name of the resource
     * @param optionalNames - optional: Plural name of the resource
     * @returns {{state: {}, getters: {}, mutations: {}, actions: {}}|*}
     */
    initializeModule(location, name, optionalNames){
        let names = (typeof optionalNames === 'undefined') ? name + 's' : optionalNames;
        let capName = capitalize(name)
        let capNames = capitalize(names)
        let setResources = 'set' + capNames
        let setResource = 'set' + capName
        let clearResources = 'clear' + capNames
        let removeResource = 'remove' + capName
        let fetchResource = 'fetch' + capName
        let fetchResources = 'fetch' + capNames
        let fetchResourcesBy = fetchResources + 'By'
        let fetchResourcesByLocation = fetchResourcesBy + 'Location'
        let createResource = 'create' + capName
        let updateResource = 'update' + capName
        let deleteResource = 'delete' + capName
        let deleteBodyResource = 'deleteBody'+ capName
        let fetchResourcesPage = fetchResources + 'Page'
        module = {
            state: {},
            getters: {},
            mutations: {},
            actions: {}
        }
        module.state[name] = null
        module.state[names] = []
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
            addShowableDates(payload) //This function will add showable dates if the objects have dates.
            state[names] = payload
        }
        module.mutations[clearResources] = (state) => {
            state[names] = []
        }
        module.mutations[removeResource] = (state, payload) => {
            state[names] = state[names].filter(resource => resource.id !== payload.id)
        }
        module.actions[fetchResources] = function(context, payload) {
            payload = payload || {} //Set payload to empty object if undefined
            //Empty the previous list of resources.
            context.commit(clearResources)
            return new Promise((resolveSuccess, resolveFailure) => {
                RequestHandler.getObjectsRequest(formatLocation(location, payload.ids)).then(resources => {
                    context.commit(setResources, resources)
                    resolveSuccess(resources)
                }, response => {
                    resolveFailure(response)
                })
            })
        }
        module.actions[fetchResourcesBy] = function(context, payload){
            //Empty the previous list of resources.
            context.commit(clearResources)
            return new Promise((resolveSuccess, resolveFailure) => {
                RequestHandler.getObjectsRequestBy(formatLocation(location, payload.ids), payload.filters).then(resources => {
                    context.commit(setResources, resources)
                    resolveSuccess(resources)
                }, response => {
                    resolveFailure(response)
                })
            })
        }
        //Need a limit, a page, and filters
        module.actions[fetchResourcesPage] = function(context, payload){
            //Empty the previous list of resources.
            context.commit(clearResources)
            return new Promise((resolveSuccess, resolveFailure) => {
                RequestHandler.getRequest(formatLocation(location, payload.ids), payload.filters).then(resources => {
                    context.commit(setResources, resources.body.data)
                    resolveSuccess(resources)
                }, response => {
                    resolveFailure(response)
                })
            })
        }
        module.actions[fetchResourcesByLocation] = function(context, {location}){
            //Empty the previous list of resources.
            context.commit(clearResources)
            return new Promise((resolveSuccess, resolveFailure) => {
                RequestHandler.getRequest(location).then(resources => {
                    context.commit(setResources, resources.body.data)
                    resolveSuccess(resources)
                }, response => {
                    resolveFailure(response)
                })
            })
        }
        module.actions[fetchResource] = function(context, payload){
            return new Promise((resolveSuccess, resolveFailure) => {
                RequestHandler.getObjectRequest(formatLocation(location, payload.ids), payload.id).then(resource => {
                    context.commit(setResource, resource)
                    resolveSuccess(resource)
                }, response => {
                    resolveFailure(response)
                })
            })
        }
        module.actions[createResource] = function(context, payload){
            return new Promise((resolveSuccess, resolveFailure) => {
                RequestHandler.postObjectRequest(formatLocation(location, payload.ids), payload.resource).then(createdResource => {
                    resolveSuccess(createdResource.body)
                }, response => {
                    resolveFailure(response)
                })
            })
        }
        module.actions[updateResource] = function(context, payload){
            return new Promise((resolveSuccess, resolveFailure) => {
                RequestHandler.putObjectRequest(formatLocation(location, payload.ids), payload.resource).then(updatedResource => {
                    resolveSuccess(updatedResource)
                }, response => {
                    resolveFailure(response)
                })
            })
        }
        module.actions[deleteResource] = function(context, payload){
            return new Promise((resolveSuccess, resolveFailure) => {
                RequestHandler.deleteObjectRequest(formatLocation(location, payload.ids), payload.id).then(() => {
                    context.commit(removeResource, {id: payload.id})
                    resolveSuccess()
                }, response => {
                    resolveFailure(response)
                })
            })
        }
        module.actions[deleteBodyResource] = function(context, payload){
            return new Promise((resolveSuccess, resolveFailure) => {
                RequestHandler.deleteWithBodyRequest(formatLocation(location, payload.ids), payload.id,payload.data).then(() => {
                    context.commit(removeResource, {id: payload.id})
                    resolveSuccess()
                }, response => {
                    resolveFailure(response)
                })
            })
        }
        return module
    }
}

let addShowableDates = function(payload){
    addShowableDate(payload, 'startDate')
    addShowableDate(payload, 'endDate')
}


