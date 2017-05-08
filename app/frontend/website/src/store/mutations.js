

export default {
    /**
     * Set the loading state of the website.
     * @param state
     * @param loading True if the page is still loading
     */
    setLoading(state, {loading}){
        state.loading = loading
    },

    pushVisitedRoute(state, {route}){
        state.visitedRoutes.push(route)
    }

}
