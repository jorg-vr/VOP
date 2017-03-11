<template>
    <div>
        <div class="page-header">
            <h1> Vloten  </h1>

        </div>
        <div>
            <div class="row">
                <div class="col-md-6">
                    <div id="search_input">
                        <div class="input-group col-md-12">
                            <input type="text" class="form-control input-mg" placeholder="Vloot zoeken" />
                            <span class="input-group-btn">
	                        <button class="btn btn-info btn-mg" type="button">
	                            <i class="fa fa-search" aria-hidden="true"></i>
	                        </button>
	                    </span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    <table class="table">
                        <fleet-row v-for="fleet in fleets" :fleet="fleet" :key="fleet.id"></fleet-row>
                    </table>
                </div>
            </div>

        </div>
    </div>
</template>
<script>
    export default {
        components: {
            FleetRow: {
                props: {
                    fleet: Object
                },
                template: `
                <tr>
                <td class="id">{{fleet.id}}</td>
                <td>{{fleet.company}}</td>
                <td><router-link :to="{name: 'fleet', params: { id: fleet.id }}">
                    <button class="btn btn-xs btn-warning"><i class="fa fa-eye" aria-hidden="true"></i></button>
                </router-link></td>
                <td><router-link :to="{name: 'edit_fleet', params: {id: fleet.id}}">
                    <button class="btn btn-xs btn-info"><i class="fa fa-pencil" aria-hidden="true"></i></button>
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
                ]
            }
        },
        created() {
            this.fetchFleetList()
        },
        methods: {
            fetchFleetList (){
                this.$http.get('https://vopro5.ugent.be/app/api/fleets').then(response => {
                    this.fleets = response.body
                })
            }
        }
    }
</script>
<style>
    table {
        margin-top: 20px;
    }
    tr {
        display: block;
        padding: 0px 15px;
        margin-bottom: -1px;
        background-color: #fff;
        border: 1px solid #ddd;
    }
    tr:hover {
        background-color: #f5f5f5;
    }
    td {
        padding: 10px 10px;
    }
    .id {
        border-right: 1px solid #ddd;
    }

    a {
        color: #555;
    }

    a:focus, a:hover {
        text-decoration: none;
        color: #555;
    }

    td:nth-child(2) {
        width: 100%
    }
</style>