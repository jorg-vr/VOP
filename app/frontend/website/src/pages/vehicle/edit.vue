<!--
    This page is used to edit a vehicle.
    It shows the vehicle form with the update vehicle method.
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
                vehicle: {
                    licensePlate: '',
                    chassisNumber: '',
                    brand: '',
                    model: '',
                    type: '',
                    mileage: '',
                    year: '',
                    leasingCompany: '',
                }
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
                //TODO: Cfr. new but POST => PUT
                alert('Not working yet: edit vehicle')
                this.$http.put('https://vopro5.ugent.be/app/api/vehicles' + '{' + this.getQuery(vehicle) + '}').then(response => {
                    this.vehicle = response.body;
                    this.$router.push({name: 'vehicle', params: { id: this.vehicle.id }});
                })
            }
        }
    }
</script>