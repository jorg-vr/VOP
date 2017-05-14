<!--
    This page shows a certain insurance surety in detail.

    @param id: The id of the insurance surety to be shown.

-->
<template>
    <div>
       <div class="page-header">
        <h1>{{$t("surety.surety") | capitalize }} </h1>
        </div>
    <div class="col-md-8">
      <table class="table show-table" v-if="surety">
        <tr>
            <td>{{$t('surety.surety') | capitalize }}</td>
            <td>{{surety.suretyType}} </td>
        </tr>
        <tr>
            <td>{{$t('surety.premium') | capitalize }}</td>
            <td> {{surety.premium}}  </td>
        </tr>
        <tr>
            <td>{{$t('surety.premiumPercentage') | capitalize }}</td>
            <td> {{surety.premiumPercentage}} % </td>
        </tr>
    </table> 

    <!-- special conditions for the insurance surety -->
        <div class="page-header">   
            <!-- <button-add :resource="resource"></button-add>   -->
            <h2>{{$t("surety.coverage") | capitalize }}</h2>
        </div>   

    <list-component :resource="resource" :listObject="listObject">
    </list-component>

    <!-- Go back to overview contract page -->
    <button-back :route="{name: 'contracts'}"></button-back>


    </div>
</div>
</template>
<script>
    import {mapGetters, mapActions,mapMutations} from 'vuex'
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import listComponent from "../../assets/general/listComponent.vue"
    import resources from '../../constants/resources'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'

    export default {
        data(){
            return{
                resource: resources.CONDITION,
                values: []
            }
        },
        components: {
            buttonBack,listComponent,buttonAdd
        },
        props: {
            id: String,
            //contractId: String
        },
        created(){
           let suretyId = this.id
           this.fetchSurety({id:suretyId}).then(surety => {
                this.values = this.surety.specialConditions
           })
           // Needs to be removed after back end support
           // if back end support
           // this.setSpecialConditions(this.surety.specialConditions)
           // bind specialConditions in store to surety resource
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