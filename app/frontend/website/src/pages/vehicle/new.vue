
<template>
    <div>
        <div class="page-header">
            <h1>Nieuw voertuig</h1>
        </div>
        <vehicle-form :submit="createVehicle" :vehicle="vehicle"></vehicle-form>
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
                    /* TEST VEHICLE
                    licensePlate: "JAR-096",
                    chassisNumber: "jarreknock",
                    brand: "Volvo",
                    model: "s60 Polestar",
                    type: "3a252d5b-50325-bd8-b680-7aa810817924",
                    mileage: 777,
                    year: "2015",
                    leasingCompany: 'bedrijf A',
                    */
                }
            }
        },
        components: {
            VehicleForm
        },
        methods: {
            //API call to create the given vehicle.
            createVehicle(vehicle){
                alert('Not working yet: new vehicle')
                /*
                this.$http.post('https://vopro5.ugent.be/app/api/vehicles' + '{' + vehicle + '}').then(response => {
                    this.vehicle = response.body;
                    this.$router.push({name: 'vehicle', params: { id: this.vehicle.id }});
                })
                */
            }
        }
    }
</script>