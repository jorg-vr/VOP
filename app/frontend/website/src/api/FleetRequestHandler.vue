<script>
    import RequestHandler from './RequestHandler.vue'

    export default {
        data(){
            return {
                path: 'fleets/',
                /* Data returned from API calls are available in these attributes */
                fleets: [],
                fleet: {},
                finishedFetching: false
            }
        },
        mixins: [RequestHandler],
        methods : {
            /*
                API call to fetch all fleets.
                afterFunction: function which has to happen after fetching the fleets. This is optional.
             */
            fetchFleets (afterFunction){
                this.get(this.path).then(response => {
                        this.fleets = response.body.data;
                        if(afterFunction){
                            afterFunction();
                        }
                        else {
                            this.finishedFetching = true;
                        }
                    }
                )
            },

            //API call to create a fleet.
            createFleet(fleet){
                this.post(this.path, fleet);
            },

            //API call to delete a fleet.
            deleteFleet(id){
                this.delete(this.path + id);
                //Remove the fleet locally.
                this.fleets = this.fleets.filter(fleet => fleet.id !== id);
            },

            //API call to add the name of the company to every fleet.
            addCompanyNameToFleets(){
                this.get('companies').then(response => {
                    let companies = response.body.data;
                    for(let i=0; i<this.fleets.length; i++){
                        let fleet = this.fleets[i];
                        let company = companies.find(obj => obj.id === fleet.company);
                        if(company){
                            fleet.companyName = company.name;
                        }
                    }
                    this.finishedFetching = true;
                })
            }
        }
    }
</script>