<template>
    <search-bar @input="$emit('search', $event)" @submit="onSubmit(fleet)" @reset="$emit('search', '')"  :title="$t('fleet.fleets')">
        <fleet-form-input :fleet="fleet" :clients="clients"></fleet-form-input>
    </search-bar>
</template>
<script>
    import searchBar from '../searchBar.vue'
    import fleetFormInput from '../../form/types/fleetFormInput.vue'
    export default {
        data() {
            return {
                fleet: {}
            }
        },
        components: {
            searchBar, fleetFormInput
        },
        props: {
            clients: Array
        },
        methods: {
            onSubmit(value){
                console.log(value)
                this.$emit('advancedSearch', value)
            }
        }
    }
</script>
