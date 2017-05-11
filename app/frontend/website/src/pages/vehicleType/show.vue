<!--
    This page shows a certain vehicle in detail.

    @param id: The id of the vehicle to be shown.
-->
<template>
    <div v-if="vehicleType">
        <div class="page-header">
            <h1> {{$t('vehicleType.vehicleType') | capitalize }} {{vehicleType.name}}</h1>
        </div>
        <div class="col-md-8">
            <commissions :id="id"></commissions>
            <button-back :route="{name: 'vehicleTypes'}"></button-back>
        </div>

    </div>
</template>
<script>
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import commissions from '../commission/show.vue'
    import {mapGetters, mapActions} from 'vuex'

    export default {
        components: {
            buttonBack,commissions
        },
        props: {
            id: String
        },
        created() {
            this.fetchVehicleType({id: this.id});
        },
        computed: {
            ...mapGetters([
                'vehicleType',
            ])
        },
        methods: {
            ...mapActions([
                'fetchVehicleType',
            ])
        }
    }
</script>