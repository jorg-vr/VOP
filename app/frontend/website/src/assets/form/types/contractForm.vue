<!--
This page is used to show a form for an contract.

@param actions: the action this form is intended for (create/update)
@param oldInsurance (optional): The old contract if the form is intended to a update contract
-->
<template>
    <form-component v-if="contract" :actions="actions" :resource="resource" :object="contract">
        <contract-form-input :contract="contract" :actions="actions" ></contract-form-input>
    </form-component>
</template>
<script>
    import {mapActions} from 'vuex'
    import resources from '../../../constants/resources'
    import formComponent from '../formComponent.vue'
    import contractFormInput from './contractFormInput.vue'

    export default {
        data(){
            return {
                resource: resources.CONTRACT,
            }
        },
        components: {
            formComponent, contractFormInput
        },
        props: {
            actions: Object,
            oldContract: Object
        },
        computed: {
            contract(){
                if(this.oldContract === null) {
                    return {}
                }
                else {
                    return this.oldContract
                }
            }
        },
    }
</script>
