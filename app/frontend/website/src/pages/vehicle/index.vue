<!--
    This page shows all vehicles in the database.
    From this page a new vehicle can be added or an existing vehicle can be edited or removed.
-->
<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>{{$t("vehicle.vehicles") | capitalize }}</h1>
        </div>
        <abstract-search-form :resource="resource" :filters="filters" :searchFunction="searchFunction">
            <vehicle-search-input :vehicle="filters"></vehicle-search-input>
        </abstract-search-form>
        <!-- Render an info-pane for every vehicle. Once all the data is loaded, the table will be shown.-->
        <list-component v-if="vehicles.length > 0" :resource="resource" :listObject="listObject"
                        :pager="true" @previous="previous" @next="next" @last="last" @first="first" >

        </list-component>
    </div>
</template>
<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import listComponent from "../../assets/general/listComponent.vue"
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import AbstractSearchForm from '../../assets/general/AbstractSearchForm.vue'
    import VehicleSearchInput from './VehicleSearchInput.vue'
    import actions from '../../constants/actions'

    export default {
        data(){
            return {
                filters: {},
                resource: resources.VEHICLE,
                previousResponse: {} //Indicates the last response of a search query. This is used for paging.
            }
        },
        components: {
            listComponent, buttonAdd, AbstractSearchForm, VehicleSearchInput
        },
        created() {
            this.clearVehicles()
        },
        mounted(){
            $('.search-panel.collapse').collapse("show")
            if(this.isAuthorizedForOwnResourcesButNotAll(this.resource, actions.READ_ALL)){
                this.filters.company = this.activeFunction.company
            }
        },
        computed: {
            ...mapGetters([
                'isAuthorizedForOwnResourcesButNotAll',
                'activeFunction',
                'vehicles'
            ]),
            listObject() {
                var listObj = {};
                listObj.headers = ['brand','model', 'licensePlate'];
                listObj.values = this.vehicles;
                return listObj;
            }
        },
        methods: {
            ...mapActions([
                'fetchVehiclesPage',
                'fetchVehiclesByLocation'
            ]),
            ...mapMutations([
                'clearVehicles'
            ]),
            previous(){
                let previousLoc = this.previousResponse.body.previous
                if(previousLoc!==null){
                    this.fetchVehiclesByLocation({location: previousLoc}).then(response => {
                        this.previousResponse = response
                    })
                }
                
            },
            next(){
                let nextLoc = this.previousResponse.body.next
                if(nextLoc!==null){
                    this.fetchVehiclesByLocation({location: nextLoc}).then(response => {
                        this.previousResponse = response
                    })
                }
            },
            first(){
                let firstLoc = this.previousResponse.body.first
                if(firstLoc!==null){
                    this.fetchVehiclesByLocation({location: firstLoc}).then(response => {
                        this.previousResponse = response
                    })
                }
            },
            last(){
                let lastLoc = this.previousResponse.body.last
                if(lastLoc!==null){
                    this.fetchVehiclesByLocation({location: lastLoc}).then(response => {
                        this.previousResponse = response
                    })
                }
            },
            searchFunction(){
                this.filters.page = 0
                this.filters.limit = 10
                this.fetchVehiclesPage({filters: this.filters}).then(response => {
                    console.log(response)
                    this.previousResponse = response
                })
            }
        }
    }
</script>
