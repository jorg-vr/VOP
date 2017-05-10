

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
        state.visitedRoutes.push(route)
    },

    /**
     * Set the latest error returned by the API
     * @param state
     * @param response
     */
    setError(state, response){
        state.error = response.body
    }

}
