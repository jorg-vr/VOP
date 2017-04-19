<!--
    This page shows a certain client in detail.

    @param id: The id of the client to be shown.

-->
<template>
    <div v-if="client && client.address">
        <div class="page-header">
            <h1>{{$t("client.client") | capitalize }}</h1>
        </div>
        <div class="col-md-8">
            <table class="table show-table" v-if="client.address">
                <tr>
                    <td>{{$t('client.name') | capitalize }}</td>
                    <td>{{client.name}}</td>
                </tr>
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
            <list-component v-for="fleet in fleets"
                            v-if="fleet"
                            :object="fleet"
                            :visibleKeys="new Array('name','companyName')"
                            :resource="resource"
                            :key="fleet.id">
            </list-component>
            <button-back :route="{name: 'clients'}"></button-back>
            <button-add :resource="resource" :params="{clientId: client.id}"></button-add>
        </div>
    </div>
</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
    import resources from '../../constants/resources'
    import clientTypes from '../../constants/clientTypes'
    import listComponent from "../../assets/general/listComponent.vue"
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'

    export default {
        data(){
            return {
                resource: resources.FLEET,
                type: ''
            }
        },
        components: {
            buttonBack, listComponent, buttonAdd
        },
        props: {
            id: String
        },
        created(){
            let clientId = this.id
            this.fetchClient({id: clientId}).then(client => {
                this.type = clientTypes[client.type]['name']
            })
            this.fetchFleetsByClient({clientId: clientId})
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
                'fetchFleetsByClient'
            ])
        },
    }
</script>
