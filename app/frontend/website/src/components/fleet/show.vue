<template>
    <div>
        <div class="page-header">
            <h1>Vloot {{this.$route.params.id}}</h1>
        </div>
        <div class="row">
            <div class="col-md-8">

                <div v-for="subfleet in subfleets">
                    <h2>{{subfleet.type | capitalize }}</h2>
                    <table class="table fleet-table">
                        <subfleet-row v-for="vehicle in subfleet.vehicles" :vehicle="vehicle" :key="vehicle.id"></subfleet-row>
                        <router-link :to="{name: 'new_vehicle'}"><button type="button" class="btn btn-primary table-button">Niew voertuig</button></router-link>
                        <button type="button" class="btn btn-primary table-button">Verwijder subvloot</button>
                    </table>
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
                <td><router-link :to="{name: 'vehicle', params: { id: vehicle.id }}">
                    <button class="btn btn-xs btn-warning"><i class="fa fa-eye" aria-hidden="true"></i></button>
                </router-link></td>
                <td><router-link :to="{name: 'edit_vehicle', params: {id: vehicle.id}}">
                    <button class="btn btn-xs btn-info"><i class="fa fa-pencil" aria-hidden="true"></i></button>
                </router-link></td>
                <td><button v-on:click="removeVehicle(vehicle.id)" class="btn btn-xs btn-danger"><i class="fa fa-trash" aria-hidden="true"></i></button></td>
                </tr>
                `,
                    methods: {
                    removeVehicle (vehicleID){
                        this.$http.delete('https://vopro5.ugent.be/app/api/vehicle/' + vehicleID).then(response => {
                            //Verwerk response
                        })
                    }
                }
            }
        },
        data() {
            return {
                subfleets : [ //Some test subfleets, this will be filled in with the actual subfleets
                    {type: 'vrachtwagen', vehicles : [
                        {id: 1, license_plate: 'VA'},
                        {id: 2, license_plate: 'VB'},
                        {id: 3, license_plate: 'VC'}
                    ]},
                    {type: 'persoonwagen', vehicles : [
                        {id: 4, license_plate: 'PA'},
                        {id: 5, license_plate: 'PB'},
                        {id: 6, license_plate: 'PC'}
                    ]}
                ]
            }
        },
        created() {
            //this.fetchSubleetList()
        },
        methods: {
            fetchSubfleetList (){
                this.$http.get('TODO').then(response => {
                    this.subfleets = response.body
                })
            },
            removeSubfleet(){

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