<!--
This page is used to show a form for an insurance.

@param actions: the action this form is intended for (create/update)
@param oldInsurance (optional): The old insurnace if the form is intended to a update insurance
-->
<template>
    <form-component v-if="condition" :actions="actions" :resource="resource" :object="condition">
        <condition-form-input :condition="condition"></condition-form-input>
    </form-component>
</template>
<script>
    import {mapActions} from 'vuex'
    import resources from '../../../constants/resources'
    import formComponent from '../formComponent.vue'
    import conditionFormInput from './conditionFormInput.vue'

    export default {
        data(){
            return {
                resource: resources.CONDITION,
            }
        },
        components: {
            formComponent, conditionFormInput
        },
        props: {
            actions: Object,
            oldCondition: Object
        },
        computed: {
            condition(){
                if(this.oldCondition === null) {
                    return {}
                }
                else {
                    return this.oldCondition
                }
            }
        }
    }
</script>
