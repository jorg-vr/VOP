<!--
    This page is used to edit a certain insurance.
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1>{{ $t("insurance.insurance") | capitalize }} {{$t("actions_plural.edit") }}</h1>
        </div>
        <insurance-form :oldInsurance=insurance :submit="updateInsurance"></insurance-form>
    </div>
</template>
<script>
    import InsuranceForm from '../../assets/form/types/insuranceForm.vue'
    import {mapGetters, mapActions} from 'vuex'


    export default {
        components: {
            InsuranceForm
        },
        props: {
            id: String
        },
        created(){
            this.fetchInsurance({id: this.id})
        },
        computed: {
            ...mapGetters([
                'insurance'
            ])
        },
        methods: {
            ...mapActions([
                'fetchInsurance',
                'updateInsurance'
            ])
        }
    }
</script>

