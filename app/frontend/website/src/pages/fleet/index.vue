<!--
    This page shows all of the fleets in the database.
-->
<template>
    <div>
        <div class="page-header">
            <h1>Vloten </h1>
        </div>
        <!-- Render an info-pane for every fleet -->
        <info-pane v-for="fleet in fleets"
                   :textValues="new Array(fleet.name, fleet.company)"
                   :remove="deleteFleet"
                   :objectId="fleet.id"
                   edit="edit_fleet"
                   show="fleet"
                   :key="fleet.id">
        </info-pane>
        <router-link :to="{name: 'new_fleet'}">
            <button type="button" class="btn btn-primary btn-circle btn-lg">+</button>
        </router-link>
    </div>
</template>

<script>
    import infoPane from "../../assets/listComponent.vue"
    export default {
        components: {
            'info-pane': infoPane
        },
        data: function () {
            return {
                fleets : [ //Dummy fleets
                    {id: 1, name: 'Vloot 1', company : '1'},
                    {id: 2, name: 'Vloot 2', company : '2'},
                    {id: 3, name: 'Vloot 3', company : '3'},
                    {id: 4, name: 'Vloot 4', company : '4'}
                ]
            }
        },
        created() {
            //Get all the fleets in the database when the page is loaded.
            this.fetchFleetList()
        },
        methods: {
            //API call to fetch the fleets from the database.
            fetchFleetList (){
                this.$http.get('https://vopro5.ugent.be/app/api/fleets').then(response => {
                    const data = response.body.data;
                    for(let i=0; i<data.length; i++){
                        this.fleets.push(data[i]);
                    }
                })
            },
            //API call to delete a fleet.
            deleteFleet(fleetId){
                this.$http.delete('https://vopro5.ugent.be/app/api/fleets' + fleetId)
            },
            //API call to create a fleet.
            createFleet(fleet){
                this.$http.post('https://vopro5.ugent.be/app/api/fleets', fleet,
                    {
                        headers: {
                            Accept: "application/json",
                        }
                    }
                ).then(response => {
                    console.log(response.body);
                })
            }
        }
    }
</script>
<style>
    .btn-circle.btn-lg {
        position: fixed;
        left: 230px;
        bottom: 40px;
        width: 50px;
        height: 50px;
        padding: 10px 16px;
        font-size: 18px;
        line-height: 1.33;
        border-radius: 25px;
    }
</style>
