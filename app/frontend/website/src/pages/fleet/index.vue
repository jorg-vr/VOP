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
        <abstract-search-form :filters="filters" :resource="resource" :searchFunction="searchFleets">
            <fleet-search-input :fleet="filters"></fleet-search-input>
        </abstract-search-form>
        <!-- Render an info-pane for every fleet. Once all the data is loaded, the table will be shown.-->
        <list-component :listObject="listObject" :resource="resource"></list-component>
    </div>
</template>
<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import actions from '../../constants/actions'
    import listComponent from "../../assets/general/listComponent.vue"
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import AbstractSearchForm from '../../assets/general/AbstractSearchForm.vue'
    import FleetSearchInput from './FleetSearchInput.vue'

    export default {

        data(){
            return {
                filters: {},
                resource: resources.FLEET,
            }
        },

        components: {
            listComponent, buttonAdd, AbstractSearchForm, FleetSearchInput
        },
        created() {
            this.setLoading({loading: true })
            if(this.authorizedForAll) { //Then the user is authorized for all, else the user can't get on this page.
                let p1 = this.fetchFleets()
                let p2 = this.fetchClients()
                Promise.all([p1, p2]).then(values => {
                    this.addClientNames({clients: values[1]}).then(() => {
                        this.setLoading({loading: false })
                    })
                })
            }
            else {
                this.fetchFleetsBy({filters: {company: this.activeFunction.company}}).then(() => {
                    this.setLoading({loading: false })
                })
            }
        },
        computed: {
            ...mapGetters([
                'clients',
                'fleets',
                'activeFunction',
                'isAuthorizedForAllResources'
            ]),
            authorizedForAll(){
                return this.isAuthorizedForAllResources(this.resource, actions.READ_ALL)
            },
            listObject() {
                var listObj = {};
                listObj.values = this.fleets;
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
                'fetchFleetsBy',
                'fetchClients',
                'addClientNames'
            ]),
            ...mapMutations([
                'setLoading'
            ]),
            searchFleets({filters}){
                if(!this.authorizedForAll) {
                    filters.company = this.activeFunction.company

                }
                this.fetchFleetsBy({filters}).then(() => {
                    this.addClientNames({clients: this.clients})
                })
            }
        }
    }
</script>
