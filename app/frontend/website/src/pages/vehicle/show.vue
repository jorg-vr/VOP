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
                    <td>{{vehicle.chassisNumber}}</td>
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
                    <td>{{vehicle.type}}</td>
                </tr>
                <tr>
                    <td>Kilometer count</td>
                    <td>{{vehicle.kilometerCount}}</td>
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
        </div>

    </div>
</template>
<script>
    export default {
        data: function(){
            return {
                vehicle: { //Dummy vehicle
                    id: "cc5c7659-e3e9-4935-8eaf-19959d78d868",
                    licensePlate: "IAM-007",
                    chassisNumber: "abcdefhijk",
                    brand: "Lamborghini",
                    model: "Diablo GT",
                    type: "1f081c6c-4009-4ae4-a680-7aa810817924",
                    kilometerCount: 123,
                    year: "20170312",
                    leasingCompany: null,
                    createdAt: null,
                    updatedAt: null,
                    url: "/vehicles/cc5c7659-e3e9-4935-8eaf-19959d78d868"
                }
            }
        },
        created() {
            //this.vehicle = fetchVehicle()
        },
        methods: {
            //API call to fetch the vehicle of this page.
            fetchVehicle: function(){
                this.$http.get('/vehicles/' + $route.params.id).then(response => {
                    //TODO
                    this.vehicle = response
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