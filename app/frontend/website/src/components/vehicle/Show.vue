<template>
    <div>
        <div class="page-header">
            <h1>Vehicle {{vehicle.id}}</h1>
            <table>
                <tr>
                    <td>License plate</td>
                    <td>{{vehicle.license_plate}}</td>
                </tr>
                <tr>
                    <td>Chassis number</td>
                    <td>{{vehicle.chassis_number}}</td>
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
                    <td>{{vehicle.kilometer}}</td>
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
                vehicle: {}
            }
        },
        methods: {
            getVehicles: function(){
                this.$http.get('/vehicles/' + $route.params.id).then(response => {
                    //Formatting
                    vehicle = response
                })
            }
        }
    }
</script>