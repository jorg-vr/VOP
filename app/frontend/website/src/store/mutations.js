import Vue from 'vue'
import { Validator } from 'vee-validate'

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
     * Set the language of the website
     * @param state
     * @param language
     */
    setLanguage(state, {language}){
        Vue.config.lang = language
        localStorage.setItem('languageCode', language)
        Validator.setLocale(language)
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
