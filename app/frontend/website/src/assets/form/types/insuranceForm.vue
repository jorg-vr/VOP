<!--
This page is used to show a form for an insurance.

@param actions: the action this form is intended for (create/update)
@param oldInsurance (optional): The old insurnace if the form is intended to a update insurance
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
                    return {}
                }
                else {
                    return this.oldInsurance
                }
            }
        }
    }
</script>
