<template>
    <div>
        <div class="page-header">
            <h1>New vehicle</h1>
            <input v-model="vehicle.brand" placeholder="Merk">
            <input v-model="vehicle.model" placeholder="Model">
            <input v-model="vehicle.type" placeholder="Type">
            <input v-model="vehicle.license_plate" placeholder="Nummerplaat">
            <input v-model="vehicle.chassis_number" placeholder="Chassis nummer">
            <input v-model="vehicle.year" placeholder="Productiejaar">
            <input v-model="vehicle.kilometer_count" placeholder="Kilometerafstand">
            <input v-model="vehicle.leasing_company" placeholder="Leasing bedrijf">
            <button v-on:click="addVehicle">Voeg toe</button>
        </div>
    </div>
</template>
<script>
    export default {
        data: function(){
            return {
                vehicle: {
                    brand: '',
                    model: '',
                    type: '',
                    license_plate: '',
                    chassis_number: '',
                    year: '',
                    kilometer_count: '',
                    leasing_company: ''
                }
            }
        },
        methods: {
            addVehicle: function(){
                this.$http.post('/vehicles', vehicle)
            }
        }
    }
</script>