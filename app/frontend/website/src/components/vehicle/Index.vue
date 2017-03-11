<template>
    <div>
        <div class="page-header">
            <h1>Vehicles</h1>
        </div>
        <ul>
            <li v-for="vehicle in getVehicles">
                <router-link :to="{name: 'vehicle', params: { id: vehicle.id }}">
                    {{vehicle.license_plate}}
                </router-link>
            </li>
            <button v-on:click="getVehicles"></button> <!-- For testing purposes -->
        </ul>
        <router-view></router-view>
    </div>
</template>
<script>
    export default {
        methods: {
            getVehicles: function(){
                this.$http.get('https://vopro5.ugent.be/app/api/vehicles').then(response => {
                        //Verwerk response

                })
            }
        }
    }
</script>