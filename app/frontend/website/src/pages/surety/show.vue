<!--
    This page shows a certain insurance in detail.

    @param id: The id of the insurance to be shown.

-->
<template>
    <div>
         <div class="page-header">
            <h1>{{$t("insurance.insurance") | capitalize }} gedetailleerd </h1>
        </div>
        <div class="col-md-8">
        	 <h2>{{$t("insurance.type") | capitalize }}</h2>
        	 <table class="table show-table">
                <tr>
                    <td>{{$t('insurance.type') | capitalize }}</td>
                    <td>{{suretyDetail.suretyType}} </td>
                </tr>
               	<tr>
                    <td>{{$t('insurance.premium') | capitalize }}</td>
                    <td> {{suretyDetail.premium}} % </td>
                </tr>
                 <tr>
                    <td>{{$t('insurance.percentage') | capitalize }}</td>
                    <td> {{suretyDetail.premiumPercentage}} % </td>
                </tr>
				<tr>
                    <td>{{$t('insurance.insurancedAmount') | capitalize }}</td>
                    <td> {{suretyData.insuredValue}} </td>
                </tr>
                <tr>
                    <td>{{$t('insurance.totalCost') | capitalize }}</td>
                    <td> € {{suretyData.cost}} </td>
                </tr>
                <tr>
                    <td>{{$t('insurance.tax') | capitalize }}</td>
                    <td> {{suretyData.tax}} % </td>
                </tr>
                 <tr>
                    <td>{{$t('insurance.startdate') | capitalize }}</td>
                    <td> {{suretyData.startDate}}</td>
                </tr>
                <!-- Insuranced vehicle -->
            </table>
             <h2>{{$t("insurance.vehicle") | capitalize }}</h2>

            
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

   				<!--<tr>
                    <td>{{$t('vehicle.vehicleType') | capitalize }}</td>
                    <td>{{vehicle.type}}</td>
                </tr> -->

                <tr>
                    <td>{{$t('vehicle.value') | capitalize }}</td>
                    <td>€ {{vehicle.value}}</td>
                </tr>
                <tr v-if='vehicle.leasingCompany != null'>
                    <td>{{$t('vehicle.leasingCompany') | capitalize }}</td>
                    <td>{{vehicle.leasingCompany}}</td>
                </tr>


            </table> 
            <!-- Go back to overview contract page -->
            <button-back :route="{name: 'insurance', params: {id: contractId}}"></button-back>


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
             let suretyId = this.id
             this.fetchSurety({id:suretyId,contractId: this.contractId })
             console.log(this.suretyData)
             // get all resources to display information
             // get SuretyInfo
             let suretyInfo = this.suretyData.surety
             console.log(suretyInfo)
             this.fetchSuretyDetail(suretyInfo)
            // console.log('GETTING DETAILED INFO FOR: '+suretyInfo)
            // console.log(this.suretyDetail)
            //  // get vehicle info
              let vehicleId= this.suretyData.vehicle
            //  console.log('GETTING DETAILED INFO FOR: '+vehicleId)
              this.fetchVehicle({id: vehicleId})

        },
        computed: {
            ...mapGetters([
                'suretyData',
                'suretyDetail',
                'vehicle'
            ])
        },
        methods: {
            ...mapActions([
                'fetchSurety',
                'fetchSuretyDetail',
                'fetchVehicle'
            ])
        },
    }
</script>
