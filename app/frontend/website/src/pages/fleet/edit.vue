<!--
    This page is used to edit a fleet.
-->
<template>
    <div>
        <div class="page-header">
            <h1>{{$t("common.edit") | capitalize}} {{$t("fleet.fleet")}}</h1>
        </div>
        <fleet-form :fleet="fleet" :submit="updateFleet"></fleet-form>
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
            this.getFleet({id: this.$route.params.id})
        },
        computed: {
            ...mapGetters([
                'fleet'
            ])
        },
        methods: {
            ...mapActions([
                'updateFleet',
                'getFleet'
            ])
        }
    }
</script>
