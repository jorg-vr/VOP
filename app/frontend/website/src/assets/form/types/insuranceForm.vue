<!--
    This page is used to generate a form for a user.
-->
<template>
    <form-component v-if="insurance" :actions="actions" :resource="resource" :object="insurance">
        <insurance-form-input :insurance="insurance"></insurance-form-input>
    </form-component>
</template>
<script>
    import {mapActions} from 'vuex'
    import resources from '../../../constants/resources'
    import formComponent from '../formComponent.vue'
    import insuranceFormInput from './insuranceFormInput.vue'

    export default {
        data(){
            return {
                resource: resources.INSURANCE,
            }
        },
        components: {
            formComponent, insuranceFormInput
        },
        props: {
            actions: Object,
            oldInsurance: Object
        },
        computed: {
            insurance(){
                if(this.oldInsurance === null) {
                    return {address: {}}
                }
                else {
                    return this.oldInsurance
                }
            }
        }
    }
</script>
