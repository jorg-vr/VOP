<template>
    <div class="col-lg-8 col-md-9 col-sm-11">
        <div class="page-header">
            <h1>
                {{$t("log.log") | capitalize}} {{$t("resource.fleet")}} {{fleet.name}}
            </h1>
            <logs :id="id" resource="fleets"></logs>
        </div>
    </div>
</template>
<script>
    import {mapActions} from 'vuex'
    import logs from '../log/index.vue'

    export default {
        data(){
            return {
                fleet: {},
            }
        },
        props: {
            id: String
        },
        components: {
            logs
        },
        created(){
            this.fetchFleet({id: this.id}).then(fleet => {
                this.fleet = fleet
            })
        },
        methods: {
            ...mapActions([
                'fetchFleet',
            ])
        }
    }
</script>