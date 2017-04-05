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
    }
}