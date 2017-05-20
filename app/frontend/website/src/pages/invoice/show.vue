<!--
    This page shows a certain client in detail. 
-->
<template>
    <div v-if="show">
        <div v-if="invoice" class="page-header">
            <h1>{{$t("invoiceTypes."+invoice.type) | capitalize }} {{client.name}}
                <button-action @click="downloadGreenCard({contractId, insuranceId: id})" buttonClass="pull-right btn btn-primary">
                    {{$t('vehicle.generate_green_card')}}
                </button-action></h1>
            <h4 >{{showDate(invoice.startDate)}} - {{showDate(invoice.endDate)}}</h4>
            <h4> {{$t('invoice.totalAmountEuro') | capitalize }}: {{invoice.totalAmountEuro}}</h4>
            <h4>{{$t('invoice.totalTaxEuro') | capitalize }}: {{invoice.totalTaxEuro}}</h4>
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
    import {translateSuretyTypes,centsToEuroArray,centsToEuroObject} from '../../utils/utils'

    export default {
        data() {
            return {
                resource: resources.VEHICLE_INVOICE,
                show:false
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

            this.setLoading({loading: true });
            let p1 = this.fetchInvoice({id:this.id,ids:{company: this.companyId}});
            this.fetchClient({id:this.companyId});
            let p2= this.fetchVehicleInvoices({ids:{company: this.companyId,invoice:this.id}});

            Promise.all([p1,p2]).then(()=>{
                centsToEuroArray(this.vehicleInvoices,"totalCost");
                centsToEuroArray(this.vehicleInvoices,"totalTax");
                centsToEuroArray(this.vehicleInvoices,"insuredValue");
                centsToEuroArray(this.vehicleInvoices,"franchise");
                translateSuretyTypes(this.vehicleInvoices);
                centsToEuroObject(this.invoice,"totalAmount");
                centsToEuroObject(this.invoice,"totalTax");
                this.setLoading({loading: false });
                this.show=true;
            });
        },
        computed: {
            ...mapGetters([
                'client',
                'invoice',
                'vehicleInvoices'
            ]),
            listObject() {
                var listObj = {};
                listObj.headers = ["licensePlate","suretyTypeTranslation","insuredValueEuro","franchiseEuro","totalCostEuro","totalTaxEuro"];
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
