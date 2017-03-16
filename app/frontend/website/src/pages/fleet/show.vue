<!--
    This page shows a certain fleet in detail.
-->
<template>
    <div>
        <div class="page-header">
            <h1>Vloot: {{fleet.name}} </h1>
        </div>
        <router-link :to="{name: 'fleets'}">
            <button type="button" class="btn btn-primary table-button">Terug</button>
        </router-link>
        <router-link :to="{name: 'new_vehicle', params: {fleet_id: fleet.id}}">
            <button type="button" class="btn btn-primary table-button">Nieuw voertuig</button>
        </router-link>
        <button type="button" class="btn btn-primary table-button" v-on:click="deleteFleet">Verwijder vloot</button>
        <div class="row">
            <div>
                <div v-for="subfleet in subfleets">
                    <div v-if="subfleet.vehicles.length > 0">
                        <h2>{{subfleet.type.name | capitalize }}</h2>
                        <table class="table">
                            <subfleet-row v-for="vehicle in subfleet.vehicles"
                                          :vehicle="vehicle" :fleet_id="fleet.id" :deleteVehicle="deleteVehicle" :key="vehicle.id">
                            </subfleet-row>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    export default {
        components: {
            SubfleetRow: {
                props: {
                    vehicle: Object,
                    fleet_id: String,
                    deleteVehicle: Function
                },
                template: `
                <tr>
                <td class="full-width">{{vehicle.licensePlate}}</td>
                <td><router-link :to="{name: 'vehicle', params: {fleet_id: fleet_id, id: vehicle.id }}">
                    <button class="btn btn-xs btn-warning"><i class="fa fa-eye" aria-hidden="true"></i></button>
                </router-link></td>
                <td><router-link :to="{name: 'edit_vehicle', params: {fleet_id: fleet_id, id: vehicle.id}}">
                    <button class="btn btn-xs btn-info"><i class="fa fa-pencil" aria-hidden="true"></i></button>
                </router-link></td>
                <td><button v-on:click="deleteVehicle(vehicle)" class="btn btn-xs btn-danger"><i class="fa fa-trash" aria-hidden="true"></i></button></td>
                </tr>
                `
            }
        },
        data() {
            return {
                subfleets:  [],
                vehicles: [],
                vehicleTypes: [],
                fleet: {},
                finishedFetchingTypes: false, //Dirty temporary method to check if http requests are finished.
                finishedFetchingVehicles: false
            }
        },
        created() {
            this.fetchData(); //This will recursively call all fetch functions.
            //This is a temporary fix as the calls have to be synchronous. The calls run asynchronous by standard.
        },
        methods: {
            fetchData(){
                this.$http.get('https://vopro5.ugent.be/app/api/fleets/' + this.$route.params.id).then(response => {
                    this.fleet = response.body;
                    this.fetchVehicles();
                });
            },
            fetchVehicles(){
                this.$http.get('https://vopro5.ugent.be/app/api/vehicles?fleet=' + this.fleet.id).then(response => {
                    const data = response.body.data;
                    for(let i=0; i<data.length; i++){
                        this.vehicles.push(data[i]);
                    }
                    this.fetchVehicleTypes()
                });
            },
            fetchVehicleTypes(){
                this.$http.get('https://vopro5.ugent.be/app/api/vehicleTypes').then(response => {
                    const data = response.body.data;
                    for(let i=0; i<data.length; i++){
                        this.vehicleTypes.push(data[i]);
                    }
                    this.createSubfleets()
                });
            },
            deleteFleet(){
                this.$http.delete('https://vopro5.ugent.be/app/api/fleets/' + this.fleet.id);
                this.$router.push({name: 'fleets'});
            },
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
            deleteVehicle (vehicle){
                this.$http.delete('https://vopro5.ugent.be/app/api/vehicles/' + vehicle.id);
                for(let i=0; i<this.subfleets.length; i++){
                    if(this.subfleets[i].type.id === vehicle.type){
                        let newVehicles = this.subfleets[i].vehicles.filter(obj => obj.id !== vehicle.id)
                        this.subfleets[i].vehicles = newVehicles;
                    }
                }
            }

        },
        filters: {
            capitalize: function (value) {
                if (!value) return ''
                value = value.toString()
                return value.charAt(0).toUpperCase() + value.slice(1)
            }
        }
    }
</script>
<style>
    .table-button {
        margin-top: 20px;
        margin-right: 10px;
    }

</style>
