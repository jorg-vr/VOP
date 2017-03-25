<!--
    This page is used to edit a fleet.
-->
<template>
    <div>
        <div class="page-header">
            <h1>Wijzig vloot</h1>
        </div>
        <fleet-form :fleet="fleet" :submit="updateFleet"></fleet-form>
    </div>
</template>
<script>
    import FleetForm from './form.vue'

    export default {
        data(){
            return {
                fleet : {}
            }
        },
        components: {
            FleetForm
        },
        created() {
            //this.fetchFleet()
        }
    }
</script>
