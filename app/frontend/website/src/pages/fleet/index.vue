<!--
    This page shows all of the fleets in the database.
-->
<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{$t("fleet.fleets") | capitalize}}
               <button-add :resource="resource"></button-add>
            </h1>
        </div>
        <fleet-search-bar @search="updateFleets" :clients="clients" @advancedSearch="updateFleetsAdvanced"></fleet-search-bar>
        <!-- Render an info-pane for every fleet. Once all the data is loaded, the table will be shown.-->
        <list-component v-for="fleet in filteredFleets"
                        v-if="fleet"
                        :resource="resource",
                        :object="fleet"
                        :visibleKeys="['name','companyName']"
                        :key="fleet.id"
                        rowClass="fleetrow">
        </list-component>
    </div>
</template>
<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import listComponent from "../../assets/general/listComponent.vue"
    import fleetSearchBar from '../../assets/search/types/fleetSearchBar.vue'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'

    export default {
        data(){
            return {
                resource: resources.FLEET
            }
        },
        components: {
            listComponent, buttonAdd, fleetSearchBar
        },
        created() {
            let p1 = this.fetchFleets()

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
            ...mapMutations([
                'setFilteredFleets'
            ]),
            updateFleets(value){
                if(value!==''){
                    this.setFilteredFleets(this.getFleetsByAll(value))
                }
                else {
                    this.setFilteredFleets(this.fleets)
                }
            },
            updateFleetsAdvanced(filterFleet){
                this.setFilteredFleets(this.getFleetsByAllAdvanced(filterFleet))
            }
        }
    }
</script>
