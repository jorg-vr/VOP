<!--
This page is used to edit or create a certain insurance.

@param id (optional): ID of the old object
@param actions: the action this form is intended for (create/update)
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1>{{ title }}</h1>
        </div>
        <insurance-form :actions="actions" :oldInsurance="oldInsurance"></insurance-form>
    </div>
</template>
<script>
    import InsuranceForm from '../../../assets/form/types/insuranceForm.vue'
    import actions from '../../../constants/actions'
    import {mapGetters, mapActions,mapMutations} from 'vuex'
    import {getResourceActionText} from '../../../utils/utils'

    export default {
        data(){
            return {
                title: getResourceActionText('insurance', this.actions.name),
                oldInsurance: null
            }
        },
        components: {
            InsuranceForm
        },
         created(){
            // this.id is id of insurance, contractId is id of contract
            this.clearSurety()
            if(this.id){
                console.log(this.actions.name)
                this.fetchSurety({id: this.id, contractId:this.contractId})
                let suretyInfo = this.suretyData.surety
                console.log(suretyInfo)
                this.fetchSuretyDetail(suretyInfo)
                console.log(this.suretyDetail)
               // this.suretyData.surety = this.suretyDetail
                console.log(this.suretyData)
            }
           
        },
        props: {
            id: String,
            actions: Object //The action for this form.
        },
        computed: {
            ...mapGetters([
                'suretyData',
                'suretyDetail',
                'insurance',
                'contractId'
            ])
        },
        methods: {
            ...mapActions([
                'fetchSurety',
                'fetchSuretyDetail'
            ]),
            ...mapMutations([
                'clearSurety'
                
            ]),
        }
    }
</script>