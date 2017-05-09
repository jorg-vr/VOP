<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{title | capitalize}}
                <button-add :resource="resource"></button-add>
            </h1>
        </div>
        <list-component :resource="resource" :objects="filteredClients" :visibleKeys="['name']"></list-component>
    </div>
</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import resources from '../../constants/resources'
    import listComponent from "../../assets/list/listComponent.vue"
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import clientSearchBar from '../../assets/search/types/clientSearchBar.vue'
    import clientTypes from '../../constants/clientTypes'

    export default {
        data(){
            return {
                resource: resources.CLIENT
            }
        },
        components: {
            listComponent, buttonAdd, clientSearchBar
        },
        created() {
            this.setLoading({loading: true})
            this.fetchClientsBy({filters: {type: clientTypes.CUSTOMER.value}}).then(() => {
                this.setLoading({loading: false })
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
                'fetchClientsBy',
                'deleteClient',
            ]),
            ...mapMutations([
                'setLoading',
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