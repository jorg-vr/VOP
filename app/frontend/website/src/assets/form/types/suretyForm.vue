<!--
This page is used to show a form for an insurance.

@param actions: the action this form is intended for (create/update)
@param oldInsurance (optional): The old insurnace if the form is intended to a update insurance
-->
<template>
    <form-component v-if="surety" :actions="actions" :resource="resource" :object="surety">
        <surety-form-input :surety="surety"></surety-form-input>
    </form-component>
</template>
<script>
    import {mapActions} from 'vuex'
    import resources from '../../../constants/resources'
    import formComponent from '../formComponent.vue'
    import suretyFormInput from './suretyFormInput.vue'

    export default {
        data(){
            return {
                resource: resources.SURETY,
            }
        },
        components: {
            formComponent, suretyFormInput
        },
        props: {
            actions: Object,
            oldSurety: Object
        },
        computed: {
            surety(){
                if(this.oldSurety === null) {
                    return {}
                }
                else {
                    return this.oldSurety
                }
            }
        }
    }
</script>
