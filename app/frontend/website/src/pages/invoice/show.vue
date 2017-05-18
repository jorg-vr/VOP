<!--
    This page shows a certain client in detail. 
-->
<template>
    <div>
        <div v-if="invoice" class="page-header">
            <h1>{{$t("invoiceTypes."+invoice.type) | capitalize }} {{client.name}}</h1>
            <h4 >{{showDate(invoice.startDate)}} - {{showDate(invoice.endDate)}}</h4>
            <h4> {{$t('invoice.totalAmount') | capitalize }}: {{invoice.totalAmount}}€</h4>
            <h4>{{$t('invoice.totalTax') | capitalize }}: {{invoice.totalTax}}€</h4>
        </div>

            <h2>{{$t("vehicleInvoice.vehicleInvoices") | capitalize }}</h2>
            <list-component :resource="resource" :listObject="listObject" :edit="false" :remove="false" :show="false"></list-component>
            <button-back :route="{name: 'invoices'}"></button-back>
        </div>
    </div>
</template>
<script>
    import {mapGetters, mapActions, mapMutations} from 'vuex'
    import resources from '../../constants/resources'
    import listComponent from '../../assets/general/listComponent.vue'
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'

    export default {
        data() {
            return {
                resource: resources.VEHICLE_INVOICE
            }
        },
        components: {
            buttonBack, listComponent, buttonAdd
        },
        props: {
            id: String,
            companyId: String
        },
        created(){
            this.fetchInvoice({id:this.id,ids:{company: this.companyId}});
            this.fetchClient({id:this.companyId});
            this.fetchVehicleInvoices({ids:{company: this.companyId,invoice:this.id}})
        },
        computed: {
            ...mapGetters([
                'client',
                'invoice',
                'vehicleInvoices'
            ]),
            listObject() {
                var listObj = {};
                listObj.headers = ["licensePlate","insuredValue","franchise","totalCost","totalTax"];
                listObj.values = this.vehicleInvoices;
                return listObj;
            }
        },
        methods: {
            ...mapActions([
                'fetchInvoice',
                'fetchClient',
                'fetchVehicleInvoices'
            ]),
            ...mapMutations([
                'setLoading'
            ]),
            showDate: function (date) {
                var d=new Date(date)
                return d.getDate()+'/'+(d.getMonth()+1)+'/'+d.getFullYear()
            }
        },
    }
</script>
