<!--
Searchbar to search for insurances.
-->
<template>
    <search-bar @input="$emit('search', $event)" @submit="onSubmit(insurance)" @reset="$emit('search', '')"  :title="$t('insurance.insurance')">
        <insurance-form-input :insurance="insurance"></insurance-form-input>
    </search-bar>
</template>
<script>
    import searchBar from '../searchBar.vue'
    import insuranceFormInput from '../../form/types/insuranceFormInput.vue'
    export default {
        data() {
            return {
                insurance: {}
            }
        },
        components: {
            searchBar, insuranceFormInput
        },
        methods: {
            onSubmit(value){
                this.$emit('advancedSearch', value)
            }
        }
    }
</script>
