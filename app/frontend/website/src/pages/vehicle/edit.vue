<!--
    This page is used to edit a vehicle.
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
                    licensePlate: 'Test edit',
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
        created() {
            this.fetchVehicle()
        },

        methods: {
            fetchVehicle(){
                this.$http.get('https://vopro5.ugent.be/app/api/vehicles/' + this.$route.params.id).then(response => {
                    this.vehicle = response.body;
                })
            },
            updateVehicle(vehicle){
                alert('Not working yet')
                this.$http.put('https://vopro5.ugent.be/app/api/vehicles' + '{' + this.getQuery(vehicle) + '}').then(response => {
                    this.vehicle = response.body;
                    this.$router.push({name: 'vehicle', params: { id: this.vehicle.id }});
                })
            }
        }
    }
</script>