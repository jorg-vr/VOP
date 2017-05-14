

export default {
    /**
     * Set the loading state of the website.
     * @param state
     * @param loading True if the page is still loading
     */
    setLoading(state, {loading}){
        state.loading = loading
    },

    /**
     * Add a route which has been visited to the stack.
     * @param state
     * @param route
     */
    pushVisitedRoute(state, {route}){
        if(route.name !== 'login'){
            state.visitedRoutes.push(route)
        }
    },

    /**
     *  Indicates if the user is going back on his steps in the visitedRoutes chain
     */
    setIsGoingBack(state, {status}){
        state.isGoingBack = status
    },

    /**
     * Set the latest error returned by the API
     * @param state
     * @param response
     */
    setError(state, response){
        if(response!==null){
            state.error = response.body
        }
        else {
            state.error = null
        }
    }

}
