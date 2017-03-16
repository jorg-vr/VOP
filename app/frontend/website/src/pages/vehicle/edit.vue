<!--
    This page is used to edit a vehicle.
    It shows the vehicle form.vue with the update vehicle method.
-->
<template>
    <div>
        <div class="page-header">
            <h1>Wijzig voertuig</h1>
        </div>
        <vehicle-form :submit="updateVehicle" :vehicle="vehicle"></vehicle-form>
    </div>
</template>
<script>
    import VehicleForm from './form.vue'
    export default {
        data: function(){
            return {
                vehicle: {}
            }
        },
        components: {
            VehicleForm
        },
        //Fetch the vehicle of this page when the page is created.
        created() {
            this.fetchVehicle()
        },

        methods: {
            //API call to fetch the vehicle of this page.
            fetchVehicle(){
                this.$http.get('https://vopro5.ugent.be/app/api/vehicles/' + this.$route.params.id).then(response => {
                    this.vehicle = response.body;
                })
            },
            //API call to update this vehicle.

            updateVehicle(vehicle){
                this.$http.put('https://vopro5.ugent.be/app/api/vehicles/' + vehicle.id , vehicle,
                    {
                        headers: {
                            Accept: "application/json",
                        }
                    }
                ).then(response => { //Success
                        //console.log(response.body);
                        this.$router.push({name: 'vehicle', params: { id: response.body.id }});
                    }, response => { //Fail
                        console.log(response.body)
                    }
                )
            }
        }
    }
</script>