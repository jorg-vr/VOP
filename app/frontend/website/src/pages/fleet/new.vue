<!--
    This page is used to create a new fleet.
-->
<template>
    <abstract-form :actions="actions" :object="fleet" :back="back" :resource="resource">
        <fleet-form-input :fleet="fleet"></fleet-form-input>
    </abstract-form>
</template>
<script>
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import fleetFormInput from '../../assets/form/types/fleetFormInput.vue'

    export default {
        data(){
            return {
                actions: actions.CREATE,
                resource: resources.FLEET,
                fleet: {},
                back:{name:resources.FLEET.name.plural()}
            }
        },
        components: {
            abstractForm,fleetFormInput
        }
    }
</script>