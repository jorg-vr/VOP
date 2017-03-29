<template>
    <search-bar @input="$emit('search', $event)">
        <fleet-form-input :fleet="{}" :clients="clients"></fleet-form-input>
        <button type="submit" class="btn btn-primary"><span class="fa fa-search" aria-hidden="true"></span></button>
    </search-bar>
</template>
<script>
    import searchBar from '../searchBar.vue'
    import fleetFormInput from '../../form/types/fleetFormInput.vue'
    export default {
        components: {
            searchBar, fleetFormInput
        },
        props: {
            clients: Array
        }
    }
</script>