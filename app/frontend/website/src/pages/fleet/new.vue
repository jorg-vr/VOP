<!--
    This page is used to create a new fleet.
-->
<template>
    <div>
        <div class="page-header">
            <h1>Nieuwe vloot</h1>
        </div>
        <fleet-form :fleet={} :submit="createFleet"></fleet-form>
    </div>
</template>
<script>
    import FleetForm from './form.vue'
    import FleetRequestHandler from '../../api/FleetRequestHandler.vue'

    export default {
        components: {
            FleetForm
        },
        mixins: [FleetRequestHandler]
    }
</script>
