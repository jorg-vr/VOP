<!--
    This page is used to create a new insurance.
-->
<template>
    <div id="content-wrapper">
       <div class="page-header">
         <h1>{{ $t("insurance.insurance") | capitalize }} {{$t("actions_plural.create") }}</h1>
     </div>  

     <insurance-form :successButtonText="$t('insurance.insurance').capitalize() + ' ' +  $t('actions_plural.create')" :failButtonText="$t('common.cancel').capitalize()"  :submit="createInsurance"></insurance-form>
 </div>
</template>
<script>
    import InsuranceForm from '../../assets/form/types/insuranceForm.vue'
    import {mapActions} from 'vuex'

    export default {
        components: {
            InsuranceForm
        },
        methods: {
            ...mapActions([
                'createInsurance'
            ])
        }
    }
</script>

