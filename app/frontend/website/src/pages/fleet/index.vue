<!--
    This page shows all of the fleets in the database.
-->
<template>
    <div>
        <div class="page-header">
            <h1>Vloten </h1>
        </div>
        <!-- Render an info-pane for every fleet. Once all the data is loaded, the table will be shown.-->
        <list-component v-if="fleets.length>0" v-for="fleet in fleets"
                        :object="fleet"
                        :visibleKeys="new Array('name','companyName')"
                        :remove="deleteFleet"
                        edit="edit_fleet"
                        show="fleet"
                        :key="fleet.id">
        </list-component>
        <router-link :to="{name: 'new_fleet'}">
            <button type="button" class="btn btn-primary btn-circle btn-lg">+</button>
        </router-link>
    </div>
</template>

<script>
    import { mapGetters, mapActions } from 'vuex'
    import listComponent from "../../assets/listComponent.vue"

    export default {
        components: {
            'list-component': listComponent,
        },
        created() {
            let p1 = this.$store.dispatch('getFleets')
            let p2 = this.$store.dispatch('getClients')
            Promise.all([p1, p2]).then(values => {
                this.$store.dispatch('addClientNames', {clients: values[1]})
            })
        },
        computed: {
            ...mapGetters([
                'fleets'
            ])
        },
        methods: {
            deleteFleet(fleetId){
                this.$store.dispatch('deleteFleet', {fleetId: fleetId});
            }
        }
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
