<!--
    This page shows all of the fleets in the database.
-->
<template>
    <div>
        <div class="page-header">
            <h1>{{$t("fleet.fleets") | capitalize}}</h1>
        </div>
        <search-bar @search="updateFleets"></search-bar>
        <!-- Render an info-pane for every fleet. Once all the data is loaded, the table will be shown.-->
        <list-component v-for="fleet in visibleFleets"
                        v-if="fleet"
                        :object="fleet"
                        :visibleKeys="new Array('name','companyName')"
                        :remove="deleteFleet"
                        edit="edit_fleet"
                        show="fleet"
                        :key="fleet.id"
                        rowClass="fleetrow">
        </list-component>
        <button-add :route="{name: 'new_fleet'}"></button-add>
    </div>
</template>
<script>
    import { mapGetters, mapActions } from 'vuex'
    import listComponent from "../../assets/general/listComponent.vue"
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import searchBar from '../../assets/general/searchBar.vue'
    import Vue from 'vue'

    export default {
        data(){
            return {
                visibleFleets: []
            }
        },
        components: {
            listComponent, buttonAdd, searchBar
        },
        created() {
            let p1 = this.fetchFleets().then(fleets => {
                this.visibleFleets = fleets
            })
            let p2 = this.fetchClients()
            Promise.all([p1, p2]).then(values => {
                this.addClientNames({clients: values[1]})
            })
        },
        computed: {
            ...mapGetters([
                'fleets',
                'getFleetsByName',
                'getFleetsByClient',
                'getFleetsByAll'
            ])
        },
        methods: {
            ...mapActions([
                'fetchFleets',
                'deleteFleet',
                'fetchClients',
                'addClientNames',
            ]),
            updateFleets(value){
                if(value!==''){
                    this.visibleFleets = this.getFleetsByAll(value)
                }
                else {
                    this.visibleFleets = this.fleets
                }
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
