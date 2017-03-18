<script>
    import RequestHandler from './RequestHandler.vue'

    export default {
        data(){
            return {
                path: 'companies/',
                /* Data returned from API calls are available in these attributes */
                companies: [],
                fleet: {}
            }
        },
        mixins: [RequestHandler],
        methods : {
            //API call to fetch all fleets.
            fetchClients (){
                this.get(this.path).then(response => {
                        this.companies = response.body.data;
                    }
                )
            }
        }
    }
</script>