<!--
    This page shows all clients in the database.
    From this page a new client can be added or an existing client can be edited or removed.
-->
<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{$t("client.clients") | capitalize}}
               <button-add :resource="resource"></button-add>
            </h1>
        </div>
        <abstract-search-form :resource="resource" :filters="filters">
            <client-search-input :client="filters"></client-search-input>
        </abstract-search-form>

        <!-- Render an info-pane for every client. Once all the data is loaded, the table will be shown.-->
        <list-component :resource="resource" :listObject="listObject" ></list-component>
    </div>
</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import listComponent from "../../assets/general/listComponent.vue"
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import clientTypes from '../../constants/clientTypes'
    import AbstractSearchForm from '../../assets/general/AbstractSearchForm.vue'
    import ClientSearchInput from './ClientSearchInput.vue'

    export default {
        data(){
            return {
                filters: {address: {}},
                resource: resources.CLIENT
            }
        },
        components: {
            listComponent, buttonAdd, AbstractSearchForm, ClientSearchInput
        },
        created() {
            this.setLoading({loading: true})
            this.fetchClients().then(() => {
                this.setLoading({loading: false })
            })
        },
        computed: {
            ...mapGetters([
                'clients'
            ]),
            listObject() {
                var listObj = {};
                listObj.headers = ["name"];
                listObj.values = this.clients;
                return listObj;
            }
        },
        methods: {
            ...mapActions([
                'fetchClients',
            ]),
            ...mapMutations([
                'setLoading'
            ])
        }
    }
</script>
