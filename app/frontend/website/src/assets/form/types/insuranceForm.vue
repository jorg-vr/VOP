<!--
    This is a form to create/update an insurance.
-->
<template>
    <div>
        <form-component v-if="insurance" @submit="proceed" :failroute="{name: 'insurances'}" :successButtonText="successButtonText" :failButtonText="failButtonText">
            <insurance-form-input :insurance="insurance"></insurance-form-input>
        </form-component>
    </div>
</template>
<script>
    import formComponent from '../formComponent.vue'
    import insuranceFormInput from './insuranceFormInput.vue'

    export default {
        components: {
            formComponent, insuranceFormInput
        },
        props: {
            failButtonText: String,
            successButtonText: String,
            submit: Function, //Function to create the fleet.
            oldInsurance: Object
        },
        computed: {
            insurance(){
                if(this.oldInsurance === undefined) {
                    return {address: {}}

                }
                else {
                    return this.oldInsurance
                }
            }
        },
        methods: {
            proceed(){
                this.submit({insurance: this.insurance}).then(() => {
                    this.$router.push({name: 'insurances'})
                })
            }
        }
    }
</script>
