<!--
    This page is used to create a new fleet.
-->
<template>
    <div>
        <div class="page-header">
            <h1>Nieuwe vloot</h1>
        </div>
        <fleet-form :submit="createFleet"></fleet-form>
    </div>
</template>
<script>
    import FleetForm from './form.vue'
    export default {
        data(){
            return {}
        },
        components: {
            FleetForm
        },
        methods: {
            //API call to create a new fleet.
            createFleet(userEmail, fleetName){
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
