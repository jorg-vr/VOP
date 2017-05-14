
export default {
    loading: false, //Indicates if the page is loading.
    visitedRoutes: [], //Stack of previously visited routes.
    isGoingBack: false, //Indicates if the user is going back on his steps in the visitedRoutes chain
    error: null, //The latest thrown error from the API.
}