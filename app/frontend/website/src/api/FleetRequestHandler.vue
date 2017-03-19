<script>
    import RequestHandler from './RequestHandler.vue'

    export default {
        data(){
            return {
                fleetsPath: 'fleets/',
                /* Data returned from API calls are available in these attributes */
                fleets: [],
                fleet: {},
                finishedFetchingFleets: false
            }
        },
        mixins: [RequestHandler],
        methods : {

            /*
             API call to fetch all fleets. The fleets will be saved in the fleets variable.
             afterFunction: function which has to happen after fetching the fleets. This is optional.
             */
            fetchFleets (afterFunction){
                this.get(this.fleetsPath).then(response => {
                        this.fleets = response.body.data;
                        if(afterFunction){
                            afterFunction();
                        }
                        else {
                            this.finishedFetchingFleets = true;
                        }
                    }
                )
            },

            /*
             API call to fetch a fleet. The fleet will be saved in the fleet variable.
             Use with caution!! If this function is called twice, there's no certainty which fleet the fleet variable will have.
             afterFunction: function which has to happen after fetching the fleets. This is optional.
             */
            fetchFleet(fleetId){
                this.get(this.fleetsPath + fleetId).then(response => {
                    this.fleet = response.body;
                })
            },

            /*
             API call to create a fleet.
             When the fleet is successfully created, the new fleets page will be visited.
             */
            createFleet(fleet){
                this.post(this.fleetsPath, fleet).then(response => {
                    this.$router.push({name: 'fleet', params: {id: response.body.id}});
                });
            },

            /*
             API call to update a fleet.
             When the fleet is successfully updated, the updated fleets page will be visited.
             */
            updateFleet(fleet){
                this.put(this.fleetsPath + fleet.id, fleet).then(response => {
                    this.$router.push({name: 'fleet', params: {id: response.body.id}});
                });
            },

            //API call to delete a fleet.
            deleteFleet(id){
                this.delete(this.fleetsPath + id);
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
                    this.finishedFetchingFleets = true
                })
            }
        }
    }
</script>