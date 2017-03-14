
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
                 chassisNumber: '',
                 brand: '',
                 model: '',
                 type: '',
                 mileage: '',
                 year: '',
                 leasingCompany: '',
                 TEST VEHICLE*/
                    licensePlate: "JAR-096",
                    chassisNumber: "jarreknock",
                    brand: "Volvo",
                    model: "s60 Polestar",
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
        methods: {
            //API call to create the given vehicle.
            createVehicle(vehicle){
                //TODO
                this.$http.post('https://vopro5.ugent.be/app/api/vehicles', vehicle,
                    /*{

                        licensePlate: "JAR-096",
                        vin:"BA95D42KLN21SAZ98",
                        brand: "Volvo",
                        type: "135562075880719110964268135792105407200",
                        model: "s60",
                        mileage: 777,
                        year: "2015"
                    },*/
                    {
                        headers: {
                            Accept: "application/json",
                        }
                    }
                ).then(response => { //Success
                    //this.vehicle = response.body;
                    console.log(response.body);
                    //this.$router.push({name: 'vehicle', params: { id: this.vehicle.id }});
                }, response => { //Fail
                        console.log(response.text())
                    }
                )
            }
        }
    }
</script>