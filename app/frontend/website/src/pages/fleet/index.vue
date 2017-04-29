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
        <fleet-search-bar @search="updateFleets" @advancedSearch="updateFleetsAdvanced"></fleet-search-bar>
        <!-- Render an info-pane for every fleet. Once all the data is loaded, the table will be shown.-->
        <list-component :listObject="listObject" :resource="resource"></list-component>
    </div>
</template>
<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import actions from '../../constants/actions'
    import listComponent from "../../assets/list/listComponent.vue"
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
            if(this.authorizedForAll) { //Then the user is authorized for all, else the user can't get on this page.
                let p1 = this.fetchFleets()
                let p2 = this.fetchClients()
                Promise.all([p1, p2]).then(values => {
                    this.addClientNames({clients: values[1]})
                })
            }
            else {
                this.fetchFleetsBy({company: this.activeFunction.company})
            }
        },

        computed: {
            ...mapGetters([
                'clients',
                'fleets',
                'filteredFleets',
                'getFleetsByAll',
                'getFleetsByAllAdvanced',
                'activeFunction',
                'isAuthorizedForAllResources'
            ]),
            authorizedForAll(){
                return this.isAuthorizedForAllResources(this.resource, actions.READ_ALL)
            },
            listObject() {
                var listObj = {};
                listObj.values = this.filteredFleets;
                if (this.authorizedForAll) {
                   listObj.headers = ['name', 'companyName']; 
               } else {
                   listObj.headers = ['name'];
               }
                return listObj;
            }
        },
        methods: {
            ...mapActions([
                'fetchFleets',
                'deleteFleet',
                'fetchClients',
                'addClientNames',
                'fetchFleetsBy'
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
