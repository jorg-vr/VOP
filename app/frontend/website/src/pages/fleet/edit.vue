<!--
    This page is used to edit a fleet.
-->
<template>
    <div>
        <div class="page-header">
            <h1>Wijzig vloot</h1>
        </div>
        <fleet-form :submit="updateFleet"></fleet-form>
    </div>
</template>
<script>
    import FleetForm from './form.vue'
    export default {
        data(){
            return {
                fleet : {}
            }
        },
        components: {
            FleetForm
        },
        created() {
            //Fetch the fleet when the page is created.
            this.fetchFleet()
        },
        methods: {
            //API call to fetch the fleet of this page.
            fetchFleet(){
                this.$http.get('https://vopro5.ugent.be/app/api/fleets/' + this.$route.params.id).then(response => {
                    this.vehicle = response.body;
                })
            },
            //API call to update the fleet with the new values.
            updateFleet(fleet){
                //TODO
                this.$http.put('https://vopro5.ugent.be/app/api/vehicles', fleet,
                    {
                        headers: {
                            Accept: "application/json",
                        }
                    }
                ).then(response => { //Success
                        //this.fleet = response.body;
                        console.log(response.body);
                    }, response => { //Fail
                        console.log(response.text())
                    }
                )
            }
        }
    }
</script>
