<!--
    This page shows all clients in the database.
    From this page a new client can be added or an existing client can be edited or removed.
-->
<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{$t("client.client") | capitalize}}
               <button-add resourceName="client"></button-add>
            </h1>
        </div>
        <client-search-bar @search="updateClients" @advancedSearch="updateClientsAdvanced"></client-search-bar>
        <!-- Render an info-pane for every client. Once all the data is loaded, the table will be shown.-->
        <list-component v-for="client in filteredClients"
                        v-if="client"
                        :object="client"
                        :visibleKeys="new Array('name')"
                        :remove="deleteClient"
                        edit="edit_client"
                        show="client"
                        :key="client.id">
        </list-component>
    </div>
</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import listComponent from "../../assets/general/listComponent.vue"
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import clientSearchBar from '../../assets/search/types/clientSearchBar.vue'

    export default {
        components: {
            listComponent, buttonAdd, clientSearchBar
        },
        created() {
            this.fetchClients().then(clients => {
                this.setFilteredClients(clients)
            })
        },
        computed: {
            ...mapGetters([
                'clients',
                'filteredClients',
                'getClientsByAll',
                'getClientsByAllAdvanced'
            ])
        },
        methods: {
            ...mapActions([
                'fetchClients',
                'deleteClient',
            ]),
            ...mapMutations([
                'setFilteredClients'
            ]),
            updateClients(value){
                if(value!==''){
                    this.setFilteredClients(this.getClientsByAll(value))
                }
                else {
                    this.setFilteredClients(this.clients)
                }
            },
            updateClientsAdvanced(filterClient){
                this.setFilteredClients(this.getClientsByAllAdvanced(filterClient))

            }
        }
    }
</script>
