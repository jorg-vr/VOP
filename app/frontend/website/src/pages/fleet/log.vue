<!--
    This page shows a certain client in detail.
-->
<template>
    <div>
        <div class="page-header">
            <h1>
                {{$t("log.logEntry") | capitalize}} {{$t("resource.fleet")}} {{fleet.name}}
            </h1>
        </div>
        <log :resourceId="resourceId" :id="id" :entry="entry" resource="fleet"></log>
    </div>
</template>
<script>
    import {mapActions} from 'vuex'
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import log from '../log/show.vue'

    export default {
        data() {
            return {
                fleet: {}
            }
        },
        components: {
            log
        },
        props: {
            id: String,
            resourceId: String,
            entry: Object
        },
        created(){
            this.fetchFleet({id: this.resourceId}).then(fleet => {
                this.fleet = fleet
            })
        },
        methods: {
            ...mapActions([
                'fetchFleet',
            ])
        },
    }
</script>
