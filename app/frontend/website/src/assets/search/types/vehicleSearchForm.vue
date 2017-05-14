<!--
Special searchbar for searching vehicles, which only contains the advanced searching part of the searchbar (the form part).

@param vehicleTypes: a list of possible vehicle types.
-->
<template>
    <div class="row search-bar">
        <button id="search-vehicle" type="button" class="btn btn-default btn-block" data-toggle="collapse" data-target=".panel-collapse" aria-expanded="false">
            {{$t('search_bar.search_for')}} {{$t('vehicle.vehicles')}}
            <span class="caret"></span>
        </button>
        <search-form @submit="onSubmit(vehicle)" @reset="reset">
            <vehicle-form-input :vehicle="vehicle"></vehicle-form-input>
        </search-form>
    </div>
</template>

<script>
    import searchForm from '../AbstractSearchForm.vue'
    import vehicleFormInput from '../../../pages/vehicle/vehicleFormInput.vue'
    export default {
        data() {
            return {
                vehicle: {}
            }
        },
        props: {
            clients: Array,
            vehicleTypes: Array
        },
        components: {
            searchForm, vehicleFormInput
        },
        methods: {
            onSubmit(value){
                this.$emit('advancedSearch', value)
            },
            reset(){
                this.$emit('advancedSearch', {})
            }
        }
    }
</script>
<style>
    #search-vehicle {
        margin-bottom: 10px;
    }
</style>