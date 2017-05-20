<!--
    This page shows a certain insurance surety in detail.

    @param id: The id of the insurance surety to be shown.

-->
<template>
    <div>
       <div v-if="surety" class="page-header">
           <h1 v-if="surety.flat">{{$t('surety.flatAdjective') | capitalize }} {{$t("surety.surety")}}
               <delete-component  :resource="resourceSurety" :id="surety.id" :back="back"></delete-component> </h1>
           <h1 v-else>{{$t("surety.surety") | capitalize }}
               <delete-component :resource="resourceSurety" :id="surety.id" :back="back"></delete-component> </h1>

        </div>
    <div class="col-md-8">
        <h4>{{$t('suretyTypes.'+surety.suretyType) | capitalize }}</h4>
        <h4>{{surety==true ? $t('surety.premium'): $t('surety.minPremium') | capitalize }}:  {{surety.premiumEuro}}</h4>
        <h4 v-if="surety.flat==false">{{$t('surety.premiumPercentage') | capitalize }}: {{(surety.premiumPercentage*100).toFixed(2)}} %</h4>
    </table> 

    <!-- special conditions for the insurance surety -->
        <div class="page-header">   
            <!-- <button-add :resource="resource"></button-add>   -->
            <h2>{{$t("surety.coverage") | capitalize }}</h2>
        </div>   

    <list-component :resource="resource" :listObject="listObject" :remove="false" :edit="false">
    </list-component>

    <!-- Go back to overview contract page -->
    <button-back :route="back"></button-back>


    </div>
</div>
</template>
<script>
    import {mapGetters, mapActions,mapMutations} from 'vuex'
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import listComponent from "../../assets/general/listComponent.vue"
    import resources from '../../constants/resources'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import {centsToEuroObject} from '../../utils/utils'
    import deleteComponent from '../../assets/general/deleteComponent.vue'

    export default {
        data(){
            return{
                resource: resources.CONDITION,
                resourceSurety: resources.SURETY,
                values: []
            }
        },
        components: {
            buttonBack,listComponent,buttonAdd,deleteComponent
        },
        props: {
            id: String,
            //contractId: String
        },
        created(){
           let suretyId = this.id
           this.fetchSurety({id:suretyId}).then(surety => {
               centsToEuroObject(this.surety,"premium");
                this.values = this.surety.specialConditions
           })
       },
       computed: {
        ...mapGetters([
            'contractId',
            'surety',
            'conditions'
            ]),
        listObject() {
                var listObj = {};
                listObj.headers = ['referenceCode','title'];
                listObj.values = this.values
                return listObj;
        },
           back(){
               return {name:resources.company,params:{id:this.surety.insuranceCompany}};
           }
    },
    methods: {
        ...mapActions([
            'fetchSurety',
            ]),
        ...mapMutations([
            'setConditions'
            ]),
        showDate: function (date) {
            var d=new Date(date)
            return d.getDate()+'/'+(d.getMonth()+1)+'/'+d.getFullYear()
        }
    },
}
</script>