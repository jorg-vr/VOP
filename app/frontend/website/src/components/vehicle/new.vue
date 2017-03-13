<template>
    <div>
        <div class="page-header">
            <h1>Nieuw voertuig</h1>
        </div>
        <vehicle-form v-on:formSubmitted="createVehicle" :vehicle="vehicle"></vehicle-form>
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
            createVehicle(){
                alert('Not working yet')
                this.$http.post('https://vopro5.ugent.be/app/api/vehicles' + this.getQuery()).then(response => {
                    this.vehicle = response.body;
                    this.$router.push({name: 'vehicle', params: { id: this.vehicle.id }});
                })
            },
            getQuery(){
                let query = '?';
                for(const prop in this.vehicle){
                    query += prop + '=' + this.vehicle[prop] + '&'
                }
                return query.replace(/ /g, '+').slice(0, -1);
            }
        }
    }
</script>