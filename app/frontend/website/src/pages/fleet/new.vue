<template>
    <div>
        <div class="page-header">
            <h1>Nieuwe vloot</h1>
        </div>
        <fleet-form email="test" name="test2" v-on:formSubmitted="createFleet"></fleet-form>
    </div>
</template>
<script>
    import FleetForm from './form.vue'
    export default {
        data(){
            return {
                email: '',
                name: ''
            }
        },
        components: {
            FleetForm
        },
        methods: {
            createFleet(userEmail, fleetName){
                console.log(userEmail);
                console.log(fleetName);
                //alert('Not yet implemented')
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
