<!--
    This page is used to create a new fleet.
-->
<template>
    <div>
        <div class="page-header">
            <h1>Nieuwe vloot</h1>
        </div>
        <fleet-form :fleet=fleet :submit="createFleet"></fleet-form>
    </div>
</template>
<script>
    import FleetForm from './form.vue'
    export default {
        data(){
            return {
                fleet: {
                    name: '',
                    company: ''
                }
            }
        },
        components: {
            FleetForm
        },
        methods: {
            //API call to create a new fleet.
            createFleet(fleet){
                //TODO
                this.$http.post('https://vopro5.ugent.be/app/api/fleets', fleet,
                    {
                        headers: {
                            Accept: "application/json",
                        }
                    }
                ).then(response => { //Success
                        //this.fleet = response.body;
                        console.log('success')
                        console.log(response.body);
                    }, response => { //Fail
                        console.log('fail')
                        console.log(response)
                    }
                )
            }
        }
    }
</script>
