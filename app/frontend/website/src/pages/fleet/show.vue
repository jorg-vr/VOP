<!--
    This page shows a certain fleet in detail.
-->
<template>
    <div>
        <div class="page-header">
            <h1>Vloot {{fleet.name}} </h1>
        </div>
        <div v-for="subfleet in subfleets">
            <div v-if="subfleet.vehicles.length > 0">
                <h3>{{subfleet.type.name | capitalize }}</h3>
                <info-pane v-for="vehicle in subfleet.vehicles"
                           :textValues="new Array(vehicle.brand, vehicle.model, vehicle.licensePlate)"
                           :remove="deleteVehicle"
                           :objectId="vehicle.id"
                           edit="edit_vehicle"
                           show="vehicle"
                           :key="vehicle.id">
                </info-pane>
            </div>
        </div>
        <router-link :to="{name: 'new_vehicle'}">
            <button type="button" class="btn btn-primary btn-circle btn-lg">+</button>
        </router-link>
    </div>
</template>
<script>
    import infoPane from "../../assets/listComponent.vue"
    import FleetRequestHandler from '../../api/FleetRequestHandler.vue'
    import VehicleRequestHandler from '../../api/VehicleRequestHandler.vue'


    export default {
        data(){
            return {
                fleetId: null,
                subfleets: []
            }
        },
        components: {
            'info-pane': infoPane
        },
        mixins: [FleetRequestHandler, VehicleRequestHandler],
        created() {
            this.fleetId = this.$route.params.id;
            this.fetchFleet(this.fleetId);
            this.$on('finished', this.createSubfleets);
            this.fetchVehicles('fleet=' + this.fleetId, this.checkIfFinished);
            this.fetchVehicleTypes(this.checkIfFinished);
        },
        filters: {
            capitalize: function (value) {
                if (!value) return ''
                value = value.toString()
                return value.charAt(0).toUpperCase() + value.slice(1)
            }
        },
        methods: {
            createSubfleets(){
                for(let i=0; i<this.vehicles.length; i++) {
                    let vehicle = this.vehicles[i];
                    let added = false; //True when the vehicle has been added to a subfleet
                    for (let j = 0; j < this.subfleets.length && !added; j++) {
                        if (vehicle.type === this.subfleets[j].type.id) {
                            this.subfleets[j].vehicles.push(vehicle);
                            added = true;
                        }
                    }
                    //If a subfleet doesn't exist yet with the current subfleet types.
                    if (!added) { //Create new subfleet
                        let created = false; //True when the subfleet has been created.
                        for (let j = 0; j < this.vehicleTypes.length && !created; j++) { //Search for the vehicleType object
                            if (vehicle.type === this.vehicleTypes[j].id) {
                                this.subfleets.push({type: this.vehicleTypes[j], vehicles: [vehicle]})
                                created = true;
                            }
                        }
                    }
                }
            },
            checkIfFinished(){
                if(this.fetchedVehicles && this.fetchedTypes){
                    this.$emit('finished');
                }
            }
        }
    }
</script>
<style>
    .table-button {
        margin-top: 20px;
        margin-right: 10px;
    }
    h3 {
        margin-top: 40px;
    }

</style>
