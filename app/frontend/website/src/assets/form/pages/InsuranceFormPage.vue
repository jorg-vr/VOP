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
    import {mapActions} from 'vuex'
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
            if(this.id){
                this.fetchInsurance({id: this.id}).then(insurance => {
                    this.oldInsurance = insurance
                })
            }
        },
        props: {
            id: String,
            actions: Object //The action for this form.
        },
        methods: {
            ...mapActions([
                'fetchInsurance'
            ])
        }
    }
</script>

