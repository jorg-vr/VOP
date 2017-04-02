<!--
    This page shows all clients in the database.
    From this page a new client can be added or an existing client can be edited or removed.
-->
<template>
    <div>
        <div class="page-header">
            <h1>{{$t("client.clients") | capitalize }}</h1>
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
        <button-add :route="{name: 'new_client'}"></button-add>
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
                this.updateFilteredClients({clients: clients})
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

            ...mapMutations({
                updateFilteredClients: 'UPDATE_FILTERED_CLIENTS'
            }),
            updateClients(value){
                if(value!==''){
                    this.updateFilteredClients({clients: this.getClientsByAll(value)})
                }
                else {
                    this.updateFilteredClients({clients: this.clients})
                }
            },
            updateClientsAdvanced(filterClient){
                this.updateFilteredClients({clients: this.getClientsByAllAdvanced(filterClient)})

            }
        }
    }
</script>
