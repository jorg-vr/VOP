<template>
    <div>
        <div class="page-header">
            <h1>Vloten </h1>
        </div>
        <div>
            <fleet-searchbar :fleets="fleets" v-on:fleetsChanged="updateFleetTable"></fleet-searchbar>
            <div class="row">
                <div class="col-md-8">
                    <table class="table">
                        <fleet-row v-for="fleet in filteredFleets" :fleet="fleet" :key="fleet.id"></fleet-row>
                    </table>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import FleetSearchBar from '../../assets/fleetSearchBar.vue'
    export default {
        components: {
            'fleet-searchbar' : FleetSearchBar,
            FleetRow: {
                props: {
                    fleet: Object
                },
                template: `
                <tr>
                <td class="id-column">{{fleet.id}}</td>
                <td class="full-width">{{fleet.company}}</td>
                <td><router-link :to="{name: 'fleet', params: { id: fleet.id }}">
                    <button class="btn btn-xs btn-warning"><i class="fa fa-eye" aria-hidden="true"></i></button>
                </router-link></td>
                <td><button v-on:click="removeFleet(fleet.id)" class="btn btn-xs btn-danger"><i class="fa fa-trash" aria-hidden="true"></i></button></td>
                </tr>
                `,
                methods: {
                    removeFleet (fleetID){
                        this.$http.delete('https://vopro5.ugent.be/app/api/fleets/' + fleetID).then(response => {
                            //Verwerk response
                        })
                    }
                }
            }
        },
        data() {
            return {
                fleets : [ //Some test fleets, this will be filled in with the actual fleets
                    {id: 1, company : 'Test company 1'},
                    {id: 2, company : 'Test company 2'},
                    {id: 3, company : 'Test company 3'},
                    {id: 4, company : 'Test company 4'}
                ],
                filteredFleets : []
            }
        },
        created() {
            this.fetchFleetList()
            this.filteredFleets =  this.fleets
        },
        methods: {
            fetchFleetList (){
                this.$http.get('https://vopro5.ugent.be/app/api/fleets').then(response => {
                    this.fleets = response.body;
                })
            },
            updateFleetTable(fleets){
                this.filteredFleets = fleets;
            }
        }
    }
</script>

<style>
    .id-column {
        border-right: 1px solid #ddd;
    }
</style>