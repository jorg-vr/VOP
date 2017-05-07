<!--
    This page shows a certain client in detail.

    @param id: The id of the client to be shown.

-->
<template>
    <div v-if="client && client.address" class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{client.name| capitalize }}
                <button-invoice  :id="client.id" ></button-invoice>
            </h1>

        </div>
        <div >
            <table class="table show-table" v-if="client.address">
                <tr>
                    <td>Type</td>
                    <td>{{type | capitalize}}</td>
                </tr>
                <tr>
                    <td>{{$t('address.country') | capitalize }}</td>
                    <td>{{client.address.country}}</td>
                </tr>
                <tr>
                    <td>{{$t('address.city') | capitalize }}</td>
                    <td>{{client.address.city}}</td>
                </tr>
                <tr>
                    <td>{{$t('address.postalCode') | capitalize }}</td>
                    <td>{{client.address.postalCode}}</td>
                </tr>
                <tr>
                    <td>{{$t('address.street') | capitalize }}</td>
                    <td>{{client.address.street}}</td>
                </tr>
                <tr>
                    <td>{{$t('address.houseNumber') | capitalize }}</td>
                    <td>{{client.address.houseNumber}}</td>
                </tr>
                <tr>
                    <td>{{$t('client.vatNumber') | capitalize }}</td>
                    <td>{{client.vatNumber}}</td>
                </tr>
                <tr>
                    <td>{{$t('client.phoneNumber') | capitalize }}</td>
                    <td>{{client.phoneNumber}}</td>
                </tr>
            </table>
            <h2>{{$t("fleet.fleets") | capitalize }}</h2>
            <list-component :objects="fleets"
                            :visibleKeys="['name','companyName']"
                            :resource="resource">
            </list-component>
            <button-back :route="{name: 'clients'}"></button-back>
            <button-add :resource="resource" :params="{clientId: client.id}"></button-add>
        </div>
    </div>
</template>
<script>
    import {mapMutations, mapGetters, mapActions} from 'vuex'
    import resources from '../../constants/resources'
    import clientTypes from '../../constants/clientTypes'
    import listComponent from "../../assets/list/listComponent.vue"
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import buttonInvoice from '../../assets/buttons/buttonInvoice.vue'

    export default {
        data(){
            return {
                resource: resources.FLEET,
                type: ''
            }
        },
        components: {
            buttonBack, listComponent, buttonAdd, buttonInvoice
        },
        props: {
            id: String
        },
        created(){
            this.setLoading({loading: true})
            let clientId = this.id
            this.fetchClient({id: clientId}).then(client => {
                this.type = clientTypes[client.type]['name']
            })
            this.fetchFleetsBy({filters: {company: clientId}}).then(() => {
                this.setLoading({loading: false })
            })
        },
        computed: {
            ...mapGetters([
                'client',
                'fleets'
            ])
        },
        methods: {
            ...mapActions([
                'fetchClient',
                'fetchFleetsBy'
            ]),
            ...mapMutations([
                'setLoading'
            ])
        },
    }
</script>
