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
            this.clearSurety()
            // edit
            if(this.id){
                this.fetchInsurance({ids: this.contractId, id:this.id}).then(insurance => {
                    this.oldInsurance = insurance
                    this.oldInsurance.startDate = this.oldInsurance.startDate.substring(0,10)
                    this.oldInsurance.endDate = this.oldInsurance.endDate.substring(0,10)
                    console.log(this.oldInsurance)
                })
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
                'fetchSuretyDetail',
                'fetchInsurance'
            ]),
            ...mapMutations([
                'clearSurety'
                
            ]),
        }
    }
</script>