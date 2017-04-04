<template>
    <search-bar @input="$emit('search', $event)" @submit="onSubmit(vehicle)" @reset="$emit('search', '')"  :title="$t('vehicle.vehicles')">
        <vehicle-form-input :vehicle="vehicle"></vehicle-form-input>
    </search-bar>
</template>
<script>
    import searchBar from '../searchBar.vue'
    import vehicleFormInput from '../../form/types/vehicleFormInput.vue'
    import {mapGetters, mapActions} from 'vuex'
    export default {
        data() {
            return {
                vehicle: {}
            }
        },
        props: {
            vehicleTypes: Array
        },
        components: {
            searchBar, vehicleFormInput
        }
    }
</script>
