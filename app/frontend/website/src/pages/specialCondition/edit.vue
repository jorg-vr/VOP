<!--
    This page is used to edit a certain insurance.

    @param id: the id of the insurance which is edited.
-->
<template>
    <abstract-form :actions="actions" :object="condition" :back="back" :resource="resource">
        <condition-form-input :object="condition"></condition-form-input>
    </abstract-form>
</template>

<script>
    import {mapActions} from 'vuex'
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import conditionFormInput from './conditionFormInput.vue'

    export default {
        data(){
            return {
                actions: actions.UPDATE,
                resource: resources.CONDITION,
                condition:{},
                back:{name:resources.CONDITION.name.plural()}
            }
        },
        created(){
            if(this.id){
                this.fetchCondition({id: this.id}).then(condition => {
                    this.condition = condition;
                })
            }
        },
        components: {
            abstractForm,conditionFormInput
        },
        props: {
            id: String
        },
        methods: {
            ...mapActions([
                'fetchCondition'
            ])

        }
    }
</script>
