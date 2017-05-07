<!--
Searchbar to search for fleets.
-->
<template>
    <search-bar @input="$emit('search', $event)" @submit="onSubmit(fleet)" @reset="$emit('search', '')"  :title="$t('fleet.fleets')">
        <fleet-form-input :fleet="fleet" ></fleet-form-input>
    </search-bar>
</template>
<script>
    import searchBar from '../searchBar.vue'
    import fleetFormInput from '../../../pages/fleet/fleetFormInput.vue'
    export default {
        data() {
            return {
                fleet: {}
            }
        },
        components: {
            searchBar, fleetFormInput
        },
        methods: {
            onSubmit(value){
                this.$emit('advancedSearch', value)
            }
        }
    }
</script>
