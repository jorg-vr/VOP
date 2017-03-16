<template>
    <div>
        <div class="page-header">
            <h1>Voertuig {{vehicle.licensePlate}}</h1>
        </div>
        <div class="col-md-8">
            <table id="show-vehicle" class="table">
                <tr>
                    <td>License plate</td>
                    <td>{{vehicle.licensePlate}}</td>
                </tr>
                <tr>
                    <td>Chassis number</td>
                    <td>{{vehicle.vin}}</td>
                </tr>
                <tr>
                    <td>Brand</td>
                    <td>{{vehicle.brand}}</td>
                </tr>
                <tr>
                    <td>Model</td>
                    <td>{{vehicle.model}}</td>
                </tr>
                <tr>
                    <td>Type</td>
                    <td>{{vehicleType}}</td>
                </tr>
                <tr>
                    <td>Kilometer count</td>
                    <td>{{vehicle.mileage}}</td>
                </tr>
                <tr>
                    <td>Year</td>
                    <td>{{vehicle.year}}</td>
                </tr>
                <tr>
                    <td>Leasing company</td>
                    <td>{{vehicle.model}}</td>
                </tr>
            </table>

            <router-link v-if="vehicle.fleet" :to="{name: 'fleet', params: {id: vehicle.fleet}}">
                <button class="btn btn-default">Terug</button>
            </router-link>
            <router-link v-else="vehicle.fleet" :to="{name: 'fleets'}">
                <button class="btn btn-default">Terug</button>
            </router-link>
        </div>

    </div>
</template>
<script>
    export default {
        data: function(){
            return {
                vehicle: {},
                vehicleType: ''
            }
        },
        created() {
            this.fetchVehicle()
        },
        methods: {
            //API call to fetch the vehicle of this page.
            fetchVehicle: function(){
                this.$http.get('https://vopro5.ugent.be/app/api/vehicles/' + this.$route.params.id).then(response => {
                    this.vehicle = response.body;
                    this.fetchVehicleType(this.vehicle.type)
                })
            },
            //API call to fetch the type of the vehicle
            fetchVehicleType: function(vehicleTypeId){
                this.$http.get('https://vopro5.ugent.be/app/api/vehicleTypes/' + vehicleTypeId).then(response => {
                    this.vehicleType = response.body.name
                })
            }
        }
    }
</script>
<style>
    #show-vehicle td:first-child{
        font-weight: bold;
    }
</style>