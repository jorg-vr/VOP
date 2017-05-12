<!--
    This page is used to create a new special condition.
-->
<template>
    <abstract-form :actions="actions" :object="condition" :back="back" :resource="resource">
        <condition-form-input :object="condition"></condition-form-input>
    </abstract-form>
</template>
<script>
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import conditionFormInput from './conditionFormInput.vue'

    export default {
        data(){
            return {
                actions: actions.CREATE,
                resource: resources.CONDITION,
                condition: {},
                back: {name:resources.CONDITION.name.plural()}

            }
        },
        components: {
            conditionFormInput,abstractForm
        }
    }
</script>