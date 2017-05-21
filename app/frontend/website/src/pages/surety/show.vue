<!--
    This page shows a certain insurance surety in detail.
    @param id: The id of the insurance surety to be shown.
-->
<template>
    <div v-if="surety" >
        <div class="page-header">
            <h1 v-if="surety.flat">{{$t('surety.flatAdjective') | capitalize }} {{$t("surety.surety")}}
               <delete-component  :resource="resourceSurety" :id="surety.id" :back="back"></delete-component> </h1>
            <h1 v-else>{{$t("surety.surety") | capitalize }}
               <delete-component :resource="resourceSurety" :id="surety.id" :back="back"></delete-component> </h1>

        </div>
        <div class="col-md-8">
            <h4>{{$t('suretyTypes.' + surety.suretyType) | capitalize }}</h4>
            <h4 v-if="show">{{surety.flat ? $t('surety.premium'): $t('surety.minPremium') | capitalize }}:  {{surety.premiumEuro}}</h4>
            <h4 v-if="!surety.flat">{{$t('surety.premiumPercentage') | capitalize }}: {{(surety.premiumPercentage*100).toFixed(2)}} %</h4>

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
                show:false
            }
        },
        components: {
            buttonBack,listComponent,buttonAdd,deleteComponent
        },
        props: {
            id: String,
            clientId: String
        },
        created(){
            this.fetchSurety({id:this.id}).then(surety => {
                centsToEuroObject(surety,"premium");
                this.show=true;
            })
        },
        computed: {
            ...mapGetters([
                'surety',
                'conditions'
            ]),
            listObject() {
                var listObj = {};
                listObj.headers = ['referenceCode','title'];
                listObj.values = this.surety.specialConditions
                return listObj;
            },
            back(){
                return {name:resources.CLIENT.name ,params: {id:this.clientId}};
            }
        },
        methods: {
            ...mapActions([
                'fetchSurety',
            ]),
            ...mapMutations([
                'setConditions'
            ])
        },
    }
</script>