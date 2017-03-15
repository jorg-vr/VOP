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
                this.$http.post('https://vopro5.ugent.be/app/api/fleets', fleet,
                    {
                        headers: {
                            Accept: "application/json",
                        }
                    }
                ).then(response => { //Success
                        this.$router.push({name: 'fleets'})
                    }, response => { //Fail
                        console.log('fail')
                    }
                )
            }
        }
    }
</script>
