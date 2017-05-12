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
            <list-component :listObject="listObject" :resource="resource">
            </list-component>
            <h2>{{$t("contract.contracts") | capitalize}}</h2>
            <list-component :resource="resource2" :listObject="listObject2"></list-component>
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
                resource2: resources.CONTRACT,
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
                if(client.type){
                    this.type = clientTypes[client.type].translation()
                }
            });
            let p1=this.fetchFleetsBy({filters: {company: clientId}});
            let p2=this.fetchContractsBy({filters: {company: clientId}});

            Promise.all([p1, p2]).then(() => {
                this.setLoading({loading: false })
            });
        },
        computed: {
            ...mapGetters([
                'client',
                'contracts',
                'fleets'
            ]),
            listObject () {
                var listObj = {};
                listObj.headers = ["name"];
                listObj.values = this.fleets;
                return listObj;
            },
            listObject2() {
                var listObj = {};
                listObj.headers = ['insuranceCompanyName','showableStartDate','totalCost','totalTax'];
                listObj.values = this.contracts;
                return listObj;
            }
        },
        methods: {
            ...mapActions([
                'fetchClient',
                'fetchFleetsBy',
                'fetchContractsBy'
            ]),
            ...mapMutations([
                'setLoading'
            ])
        }
    }
</script>
