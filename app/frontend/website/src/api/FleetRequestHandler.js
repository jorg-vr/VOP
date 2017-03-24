
export default {
    methods:{
        fetchFleets(){
            var RequestHandler = require('./RequestHandler.js')
            RequestHandler.get('test').then(response => {
                console.log(response.body.data);
            })
        }
    }
}
