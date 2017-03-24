<!--
    This page shows all of the fleets in the database.
-->
<template>
    <div>
        <div class="page-header">
            <h1>Vloten </h1>
        </div>
        <!-- Render an info-pane for every fleet. Once all the data is loaded, the table will be shown.-->
        <info-pane v-if="fleets.length>0 && finishedFetchingFleets" v-for="fleet in fleets"
                   :textValues="new Array(fleet.name, fleet.companyName)"
                   :remove="deleteFleet"
                   :objectId="fleet.id"
                   edit="edit_fleet"
                   show="fleet"
                   :key="fleet.id">
        </info-pane>
        <router-link :to="{name: 'new_fleet'}">
            <button type="button" class="btn btn-primary btn-circle btn-lg">+</button>
        </router-link>
    </div>
</template>

<script>
    import infoPane from "../../assets/listComponent.vue"

    export default {
        components: {
            'info-pane': infoPane,
        },
        created() {
            var FleetRequestHandler = require('../../api/FleetRequestHandler.js')
            //Get all the fleets in the database when the page is loaded.
            FleetRequestHandler.fetchFleets(this.addCompanyNameToFleets);
        },
    }
</script>
<style>
    .btn-circle.btn-lg {
        position: fixed;
        left: 230px;
        bottom: 40px;
        width: 50px;
        height: 50px;
        padding: 10px 16px;
        font-size: 18px;
        line-height: 1.33;
        border-radius: 25px;
    }
</style>
