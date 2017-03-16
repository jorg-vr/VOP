
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
                vehicle: {/*
                 licensePlate: '',
                 vin: '',
                 brand: '',
                 model: '',
                 type: '',
                 mileage: '',
                 year: '',
                 leasingCompany: '',
                 TEST VEHICLE*/
                    licensePlate: "JAR-096",
                    vin: "BA95D42KLN21SAZ98",
                    brand: "Volvo",
                    model: "s60",
                    fleet: '160914146855851706481713813080589660473',
                    type: "",
                    mileage: 777,
                    year: "2015",
                    leasingCompany: 'bedrijf A',

                }
            }
        },
        components: {
            VehicleForm
        },
        created() {
            if(this.$route.params.fleet_id){
                this.vehicle.fleet = this.$route.params.fleet_id;
            }
        },
        methods: {
            //API call to create the given vehicle.
            createVehicle(vehicle){
                this.$http.post('https://vopro5.ugent.be/app/api/vehicles', vehicle,
                    {
                        headers: {
                            Accept: "application/json",
                        }
                    }
                ).then(response => { //Success
                        this.$router.push({name: 'vehicle', params: { id: response.body.id }});
                    }, response => { //Fail
                        console.log(response.body)
                    }
                )
            }
        }
    }
</script>