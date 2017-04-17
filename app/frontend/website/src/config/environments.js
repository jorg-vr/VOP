/**
 * All possible environments for starting the web application.
 */
export default {
    production: {
        NODE_ENV: 'production',
        BASE: '/app/',
        API_KEY: 'https://vopro5.ugent.be/app/api'
    },
    testing: {
        NODE_ENV: 'testing',
        BASE: '/test/app/',
        API_KEY: 'https://vopro5.ugent.be/test/app/api'
    },
    development: {
        NODE_ENV: 'development',
        BASE: '/',
        API_KEY: 'https://vopro5.ugent.be/test/app/api'
    },
    local_development: {
        NODE_ENV: 'local_development',
        BASE: '/',
        API_KEY: 'http://localhost:8080'
    }
}