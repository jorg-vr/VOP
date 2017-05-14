<template>
    <vehicle-type-form :actions="actions" :id="id"></vehicle-type-form>
</template>
<script>
    import actions from '../../constants/actions'
    import VehicleTypeForm from './VehicleTypeForm.vue'

    export default {
        data(){
            return {
                actions: actions.UPDATE
            }
        },
        components: {
            VehicleTypeForm
        },
        props: {
            id: String
        }
    }
</script>