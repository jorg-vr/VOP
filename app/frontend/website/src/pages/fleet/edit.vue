<!--
    This page is used to edit a fleet.
-->
<template>
    <div>
        <div class="page-header">
            <h1>{{$t("common.edit") | capitalize}} {{$t("fleet.fleet")}}</h1>
        </div>
        <fleet-form :submit="updateFleet" :fleet="fleet"></fleet-form>
    </div>
</template>
<script>
    import FleetForm from '../../assets/form/types/fleetForm.vue'
    import {mapGetters, mapActions} from 'vuex'
    export default {
        components: {
            FleetForm
        },
        created() {
            this.fetchFleet({id: this.$route.params.id})
        },
        computed: {
            ...mapGetters([
                'fleet'
            ])
        },
        methods: {
            ...mapActions([
                'updateFleet',
                'fetchFleet'
            ])
        }
    }
</script>
