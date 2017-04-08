<!--
    This page shows all of the fleets in the database.
-->
<template>
    <div>
        <div class="page-header">
            <h1>
                {{$t("fleet.fleets") | capitalize}}
                <button-link :route="{name: 'new_fleet'}" buttonClass="pull-right btn btn-md btn-primary btn-add">Nieuwe vloot</button-link>
            </h1>
        </div>
        <fleet-search-bar @search="updateFleets" :clients="clients" @advancedSearch="updateFleetsAdvanced"></fleet-search-bar>
        <!-- Render an info-pane for every fleet. Once all the data is loaded, the table will be shown.-->
        <list-component v-for="fleet in filteredFleets"
                        v-if="fleet"
                        :object="fleet"
                        :visibleKeys="new Array('name','companyName')"
                        :remove="deleteFleet"
                        edit="edit_fleet"
                        show="fleet"
                        :key="fleet.id"
                        rowClass="fleetrow">
        </list-component>
    </div>
</template>
<style>
.btn-add {
    margin-top: -2px;
}
</style>
<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import listComponent from "../../assets/general/listComponent.vue"
    import fleetSearchBar from '../../assets/search/types/fleetSearchBar.vue'
    import buttonLink from '../../assets/buttons/buttonLink.vue'
    import Vue from 'vue'

    export default {
        components: {
            listComponent, buttonLink, fleetSearchBar
        },
        created() {
            let p1 = this.fetchFleets().then(fleets => {
                this.updateFilteredFleets({fleets: fleets})
            })
            let p2 = this.fetchClients()
            Promise.all([p1, p2]).then(values => {
                this.addClientNames({clients: values[1]})
            })
        },
        computed: {
            ...mapGetters([
                'clients',
                'fleets',
                'filteredFleets',
                'getFleetsByName',
                'getFleetsByClient',
                'getFleetsByAll',
                'getFleetsByAllAdvanced'
            ])
        },
        methods: {
            ...mapActions([
                'fetchFleets',
                'deleteFleet',
                'fetchClients',
                'addClientNames',
            ]),

            ...mapMutations({
                updateFilteredFleets: 'UPDATE_FILTERED_FLEETS'
            }),
            updateFleets(value){
                if(value!==''){
                    this.updateFilteredFleets({fleets: this.getFleetsByAll(value)})
                }
                else {
                    this.updateFilteredFleets({fleets: this.fleets})
                }
            },
            updateFleetsAdvanced(filterFleet){
                this.updateFilteredFleets({fleets: this.getFleetsByAllAdvanced(filterFleet)})
            }
        }
    }
</script>
