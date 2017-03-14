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
                //TODO
            },
            //API call to update the fleet with the new values.
            updateFleet(userEmail, fleetName){
                alert('Not yet implemented')
                //TODO
                /*
                 this.$http.get('https://vopro5.ugent.be/app/api/users?email=' + this.email).then(response => {
                 //Check if the user is actually a client
                 //Get ID from query and create new fleet with as parameter the client ID
                 });*/
            }
        }
    }
</script>
