<!--
    This page shows a certain insurance in detail.

    @param id: The id of the insurance to be shown.

-->
<template>
    <div>
         <div class="page-header">
            <h1>{{$t("vehicle_insurance.vehicle_insurance") | capitalize }}  </h1>
        </div>
        <div class="col-md-8">
        	 <table class="table show-table" v-if="insurance">
                <tr>
                    <td>{{$t('vehicle_insurance.showableStartDate') | capitalize }}</td>
                    <td>{{showDate(insurance.startDate)}} </td>
                </tr>
               	<tr>
                    <td>{{$t('vehicle_insurance.showableEndDate') | capitalize }}</td>
                    <td> {{showDate(insurance.endDate)}} </td>
                </tr>
                 <tr>
                    <td>{{$t('vehicle_insurance.insuredValue') | capitalize }}</td>
                    <td> {{insurance.insuredValue}}  </td>
                </tr>
				<tr>
                    <td>{{$t('vehicle_insurance.cost') | capitalize }}</td>
                    <td> {{insurance.cost}} </td>
                </tr>
                <tr>
                    <td>{{$t('vehicle_insurance.tax') | capitalize }}</td>
                    <td> {{insurance.tax}} </td>
                </tr>
            </table>

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


                <tr>
                    <td>{{$t('vehicle.value') | capitalize }}</td>
                    <td>€ {{vehicle.value}}</td>
                </tr>
                <tr v-if='vehicle.leasingCompany != null'>
                    <td>{{$t('vehicle.leasingCompany') | capitalize }}</td>
                    <td>{{vehicle.leasingCompany}}</td>
                </tr>


            </table> 
         
             <!-- surety -->
              <h2>{{$t("surety.surety") | capitalize }}</h2>

            <table class="table show-table" v-if="surety">
                <tr>
                    <td>{{$t('surety.suretyType') | capitalize }}</td>
                    <td> {{ surety.suretyType }}</td>
                </tr>

                <tr>
                    <td>{{$t('surety.premium') | capitalize }}</td>
                    <td> {{ surety.premium }}</td>
                </tr>

                <tr>
                    <td>{{$t('surety.premiumPercentage') | capitalize }}</td>
                    <td> {{ surety.premiumPercentage }}</td>
                </tr>
            </table> 

            <h2>{{$t("surety.coverage") | capitalize }}</h2>

            <table class="table show-table" v-if="surety">
                <tr>
                    <td> {{$t('condition.title') | capitalize }}</td>
                    <td> {{$t('condition.referenceCode') | capitalize }}</td>
                    <td> {{$t('condition.text') | capitalize }}</td>
                </tr>
                <tr v-for="sp in surety.specialConditions">
                    <td> {{ sp.title}}</td>
                    <td> {{ sp.referenceCode }}</td>
                    <td> {{ sp.text }}</td>
                </tr>

            </table> 

            <button-back :route="{name: 'contract', params: {id: contractId}}"></button-back>
        </div>
    </div>
</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
    import buttonBack from '../../assets/buttons/buttonBack.vue'

    export default {
        components: {
            buttonBack
        },
        props: {
            id: String,
            contractId: String
        },
        created(){
            // fetch vehicle insurance to display
             this.fetchInsurance({ids:{ contract:this.contractId}, id:this.id}).then(insurance => {
                // fetch insured vehicle
                this.fetchVehicle({id: insurance.vehicle})
                // fetch surety
                this.fetchSurety({id: insurance.surety})
            })
        },
        computed: {
            ...mapGetters([
                'surety',
                'suretyData',
                'suretyDetail',
                'vehicle',
                'insurance'
            ])
        },
        methods: {
            ...mapActions([
                'fetchSurety',
                'fetchInsurance',
                'fetchVehicle',
                'fetchSureties'
            ]),
            showDate: function (date) {
                var d=new Date(date)
                return d.getDate()+'/'+(d.getMonth()+1)+'/'+d.getFullYear()
            }
        },
    }
</script>