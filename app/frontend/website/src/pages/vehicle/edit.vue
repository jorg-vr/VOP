<template>
    <div>
        <div class="page-header">
            <h1>Wijzig voertuig</h1>
        </div>
        <vehicle-form v-on:formSubmitted="updateVehicle" :vehicle="vehicle"></vehicle-form>
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
            updateVehicle(updatedVehicle){
                alert('Not working yet')
                this.$http.put('https://vopro5.ugent.be/app/api/vehicles' + this.getQuery(updatedVehicle)).then(response => {
                    this.vehicle = response.body;
                    this.$router.push({name: 'vehicle', params: { id: this.vehicle.id }});
                })
            },
            getQuery(updatedVehicle){
                let query = '?';
                for(const prop in updatedVehicle){
                    query += prop + '=' + this.vehicle[prop] + '&'
                }
                return query.replace(/ /g, '+').slice(0, -1);
            }
        }
    }
</script>