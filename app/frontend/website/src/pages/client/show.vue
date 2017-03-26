<!--
    This page shows a certain client in detail. 
-->
<template>
    <div>
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
                            show="fleet"
                            :key="fleet.id">
            </list-component>
            <button-back :route="{name: 'clients'}"></button-back>
        </div>
    </div>
</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
    import listComponent from "../../assets/general/listComponent.vue"
    import buttonBack from '../../assets/buttons/buttonBack.vue'

    export default {
        components: {
            buttonBack, listComponent
        },
        created(){
            let clientId = this.$route.params.id
            console.log(this.$route.params.id)
            this.fetchClient({id: clientId})
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
