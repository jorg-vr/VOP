<!--
    This page is used to create a new insurance contract
-->
<template>
    <abstract-form :actions="actions" :object="contract" :back="back" :resource="resource">
        <contract-form-input :object="contract" :actions="actions"></contract-form-input>
    </abstract-form>
</template>
<script>
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import contractFormInput from './contractFormInput.vue'

    export default {
        data(){
            return {
                actions: actions.CREATE,
                resource: resources.CONTRACT,
                contract: {},
                back: {name:resources.CONTRACT.name.plural()}

            }
        },
        components: {
            contractFormInput,abstractForm
        }
    }
</script>