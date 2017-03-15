<!--
    This page shows a certain fleet in detail.
    TODO: document this page.
-->
<template>
    <div>
        <div class="page-header">
            <h1>Vloot: {{fleet.name}} </h1>
        </div>
        <router-link :to="{name: 'new_vehicle', params: {fleet_id: fleet.id}}">
            <button type="button" class="btn btn-primary table-button">Niew voertuig</button>
        </router-link>
        <button type="button" class="btn btn-primary table-button" v-on:click="deleteFleet">Verwijder vloot</button>
        <div class="row">
            <div>
                <div v-for="subfleet in subfleets">
                    <div v-if="subfleet.vehicles.length > 0">
                        <h2>{{subfleet.type.name | capitalize }}</h2>
                        <table class="table">
                            <subfleet-row v-for="vehicle in subfleet.vehicles" :vehicle="vehicle" :key="vehicle.id"></subfleet-row>
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
                    vehicle: Object
                },
                template: `
                <tr>
                <td class="full-width">{{vehicle.license_plate}}</td>
                <td><router-link :to="{name: 'vehicle', params: {fleet_id: fleet.id, id: vehicle.id }}">
                    <button class="btn btn-xs btn-warning"><i class="fa fa-eye" aria-hidden="true"></i></button>
                </router-link></td>
                <td><router-link :to="{name: 'edit_vehicle', params: {fleet_id: fleet.id, id: vehicle.id}}">
                    <button class="btn btn-xs btn-info"><i class="fa fa-pencil" aria-hidden="true"></i></button>
                </router-link></td>
                <td><button v-on:click="deleteVehicle(vehicle)" class="btn btn-xs btn-danger"><i class="fa fa-trash" aria-hidden="true"></i></button></td>
                </tr>
                `,
                methods: {
                    deleteVehicle (vehicle){
                        this.$http.delete('https://vopro5.ugent.be/app/api/vehicles/' + vehicle.id);
                        for(let i=0; i<subfleets.length; i++){
                            if(subfleets[i].type.id === vehicle.type.id){
                                let newVehicles = subfleets[i].vehicles.filter(obj => obj.id !== vehicle.id)
                                subfleets[i].vehicles = newVehicles;
                            }
                        }
                    }
                }
            }
        },
        data() {
            return {
                subfleets:  [],
                fleet: {}
            }
        },
        created() {
            this.fetchFleet();
            this.fetchSubfleets()
        },
        methods: {
            fetchFleet(){
                this.$http.get('https://vopro5.ugent.be/app/api/fleets/' + this.$route.params.id).then(response => {
                    this.fleet = response.body;
                });
            },
            fetchSubfleets (){
                this.$http.get('https://vopro5.ugent.be/app/api/vehicleTypes').then(response => {
                    let data = response.body.data;
                    for(var i=0; i<data.length; i++){
                        this.subfleets[i] = {
                            type : {},
                            vehicles : []
                        };
                        this.subfleets[i].type = data[i];
                        this.fetchVehicles(this.subfleets[i].type.id, i);
                    }
                })
            },
            fetchVehicles(typeId, subfleetIndex){
                let vehicles = []
                this.$http.get('https://vopro5.ugent.be/app/api/vehicles' +
                    '?type=' + typeId + '&fleet=' + this.fleet.id).then(response => {
                    let data = response.body.data;
                    for(let i=0; i<data.length; i++){
                        this.subfleets[i].vehicles.push(data[i]);
                        console.log(data[i]);
                    }
                })
            },
            deleteFleet(){
                this.$http.delete('https://vopro5.ugent.be/app/api/fleets/' + this.fleet.id);
                this.$router.push({name: 'fleets'});
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
