<!--
    This page is used to edit a fleet.
-->
<template>
    <div>
        <div class="page-header">
            <h1>{{ $t("fleet.fleet") | capitalize }} {{$t("actions_plural.edit") }}</h1>
        </div>
        <fleet-form :submit="updateFleet" :oldFleet="fleet"></fleet-form>
    </div>
</template>
<script>
    import FleetForm from '../../assets/form/types/fleetForm.vue'
    import {mapGetters, mapActions} from 'vuex'
    export default {
        components: {
            FleetForm
        },
        props: {
            id: String
        },
        created() {
            this.fetchFleet({id: this.id})
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
