<!--
    This page is used to create a new surety.
-->
<template>
    <abstract-form :actions="actions" :object="surety" :back="back" :resource="resource">
        <surety-form-input :object="surety" :actions="actions"></surety-form-input>
    </abstract-form>
</template>
<script>
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import suretyFormInput from './suretyFormInput.vue'

    export default {
        data(){
            return {
                actions: actions.CREATE,
                resource: resources.SURETY,
                surety: {},
                back: {name:resources.SURETY.name.plural()}

            }
        },
        components: {
            suretyFormInput,abstractForm
        }
    }
</script>