let nonAcceptedFilters = ['id', 'createdAt', 'lastUpdated', 'url', 'originalObject']

export default {
    /**
     * Returns if the page is loading.
     * @param state
     */
    loading(state){
        return state.loading;
    },

    /**
     * Returns the latest thrown error to the user.
     * @param state
     */
    error(state){
        return state.error
    },

    /**
     * Indicates if the user is going back on his steps in the visitedRoutes chain
     * @param state
     * @returns {isGoingBack|boolean|*}
     */
    isGoingBack(state){
        return state.isGoingBack
    }
}

