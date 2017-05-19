<!--
    This page shows a certain insurance in detail.

    @param id: The id of the insurance to be shown.

-->
<template>
    <div>
         <div class="page-header">
             <h1>{{$t("vehicle_insurance.vehicle_insurance") | capitalize }}  </h1>
             <h4 v-if="insurance" >{{showDate(insurance.startDate)}} - {{showDate(insurance.endDate)}}</h4>
        </div>
        <div class="col-md-8">
            <h4 >
                <table class="table show-table" v-if="insurance">
                    <tr>
                        <td>{{$t('vehicle_insurance.insuredValue') | capitalize }}</td>
                        <td>{{insurance.insuredValueEuro}}</td>
                    </tr>
                    <tr>
                        <td>{{$t('insurance.costEuro') | capitalize }}</td>
                        <td>{{insurance.costEuro}}</td>
                    </tr>
                    <tr >
                        <td>{{$t('insurance.taxEuro') | capitalize }}</td>
                        <td>{{insurance.taxEuro}}</td>
                    </tr>
                </table>
            </h4>
            <div class="row">
                <button-action @click="downloadGreenCard({contractId, insuranceId: id})" buttonClass="pull-left btn btn-primary">
                    {{$t('vehicle.generate_green_card')}}
                </button-action>
            </div>
            <!-- insured vehicle -->
             <h2>{{$t("vehicle.vehicle") | capitalize }}</h2>

            
            <table class="table show-table" v-if="vehicle">
                <tr>
                    <td>{{$t('vehicle.brand') | capitalize }}</td>
                    <td> {{ vehicle.brand }}</td>
                </tr>
                <tr>
                    <td>{{$t('vehicle.model') | capitalize }}</td>
                    <td>{{vehicle.model}}</td>
                </tr>
                <tr v-if='vehicle.licensePlate != null'>
                    <td>{{$t('vehicle.licensePlate') | capitalize }}</td>
                    <td>{{vehicle.licensePlate}}</td>
                </tr>
                <tr>
                    <td>{{$t('vehicle.vin') | capitalize }}</td>
                    <td>{{vehicle.vin}}</td>
                </tr>
                <tr>
                    <td>{{$t('vehicle.year') | capitalize }}</td>
                    <td>{{vehicle.year}}</td>
                </tr>
                <tr>
                    <td>{{$t('vehicle.mileage') | capitalize }}</td>
                    <td>{{vehicle.mileage}} </td>
                </tr>
            </table>


             <!-- surety -->
              <h2>{{$t("surety.surety") | capitalize }}</h2>

            <table class="table show-table" v-if="surety">
                <tr>
                    <td>{{$t('surety.suretyType' ) | capitalize }}</td>
                    <td> {{$t('suretyTypes.'+surety.suretyType ) | capitalize }}</td>
                </tr>

                <tr>
                    <td>{{$t('surety.premium') | capitalize }}</td>
                    <td> {{ surety.premiumEuro }}</td>
                </tr>

                <tr>
                    <td>{{$t('surety.premiumPercentage') | capitalize }}</td>
                    <td> {{ (surety.premiumPercentage*100).toFixed(2) }}%</td>
                </tr>
            </table> 

            <h2>{{$t("surety.coverage") | capitalize }}</h2>

            <list-component :resource="resource" :listObject="listObject" :remove="false" :edit="false">
            </list-component>

            <!-- TODO route niet correct -->
            <button-back :route="{name: 'contract', params: {id: contractId}}"></button-back>
        </div>
    </div>
</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import {centsToEuroObject} from '../../utils/utils'
    import listComponent from "../../assets/general/listComponent.vue"
    import resources from '../../constants/resources'

    export default {
        data(){
            return{
                resource: resources.CONDITION,
                values:[]
            }
        },
        components: {
            buttonBack,listComponent
        },
        props: {
            id: String,
            contractId: String
        },
        created(){
            // fetch vehicle insurance to display
             this.fetchInsurance({ids:{ contract:this.contractId}, id:this.id}).then(insurance => {
                 centsToEuroObject(insurance,"tax");
                 centsToEuroObject(insurance,"cost");
                 centsToEuroObject(insurance,"insuredValue");
                // fetch insured vehicle
                this.fetchVehicle({id: insurance.vehicle})
                // fetch surety
                this.fetchSurety({id: insurance.surety}).then(()=> {
                    centsToEuroObject(this.surety,"premium")
                    this.values = this.surety.specialConditions
                })
            })
        },
        computed: {
            ...mapGetters([
                'surety',
                'vehicle',
                'insurance'
            ]),
            listObject() {
                var listObj = {};
                listObj.headers = ['referenceCode','title'];
                listObj.values = this.values;
                return listObj;
            }
        },
        methods: {
            ...mapActions([
                'fetchSurety',
                'fetchInsurance',
                'fetchVehicle',
                'fetchSureties',
                'fetchGreenCard'
            ]),
            downloadGreenCard({contractId, insuranceId}){
                this.fetchGreenCard({contractId, insuranceId}).then(blob => {
                    console.log(blob)
                    //Download the response.
                    //Based on: https://github.com/pagekit/vue-resource/issues/285
                    //TODO: no content disposition is part of the headers, however one is returned.
                    //var filename = contentDisposition.split('filename=')[1];
                    let link = document.createElement('a');
                    link.href = window.URL.createObjectURL(blob);
                    link.download = "green_card.pdf";
                    link.click();
                })
            },
            showDate: function (date) {
                var d=new Date(date)
                return d.getDate()+'/'+(d.getMonth()+1)+'/'+d.getFullYear()
            }
        },
    }
</script>