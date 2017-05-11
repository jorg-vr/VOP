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
        <condition-form :actions="actions" :oldCondition="oldCondition"></condition-form>
    </div>
</template>
<script>
    import ConditionForm from '../../../assets/form/types/ConditionForm.vue'
    import actions from '../../../constants/actions'
    import {mapGetters, mapActions,mapMutations} from 'vuex'
    import {getResourceActionText} from '../../../utils/utils'

    export default {
        data(){
            return {
                title: getResourceActionText('condition', this.actions.name),
                oldCondition: null
            }
        },
        components: {
            ConditionForm
        },
         created(){
           if(this.id){
                this.fetchCondition({id:this.id}).then(condition => {
                    this.oldCondition = condition
                })
            }
           
        },
        props: {
            id: String,
            actions: Object //The action for this form.
        },
        computed: {
            ...mapGetters([
                'surety',
                'suretyData',
                'suretyDetail',
                'insurance',
                'contractId'
            ])
        },
        methods: {
            ...mapActions([
                'fetchSurety',
                'fetchCondition'
            ]),
            ...mapMutations([
                'clearSurety'
                
            ]),
        }
    }
</script>