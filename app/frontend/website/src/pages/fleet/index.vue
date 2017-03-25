<!--
    This page shows all of the fleets in the database.
-->
<template>
    <div>
        <div class="page-header">
            <h1>{{$t("fleet.fleets") | capitalize}}</h1>
        </div>
        <!-- Render an info-pane for every fleet. Once all the data is loaded, the table will be shown.-->
        <list-component v-for="fleet in fleets"
                        v-if="fleet"
                        :object="fleet"
                        :visibleKeys="new Array('name','companyName')"
                        :remove="deleteFleet"
                        edit="edit_fleet"
                        show="fleet"
                        :key="fleet.id">
        </list-component>
        <button-link :route="{name: 'new_fleet'}" buttonClass="btn btn-primary btn-circle btn-lg">+</button-link>
    </div>
</template>

<script>
    import { mapGetters, mapActions } from 'vuex'
    import listComponent from "../../assets/listComponent.vue"
    import buttonLink from '../../assets/buttons/buttonLink.vue'

    export default {
        components: {
            listComponent, buttonLink
        },
        created() {
            let p1 = this.fetchFleets()
            let p2 = this.fetchClients()
            Promise.all([p1, p2]).then(values => {
                this.addClientNames({clients: values[1]})
            })
        },
        computed: {
            ...mapGetters([
                'fleets'
            ])
        },
        methods: {
            ...mapActions([
                'fetchFleets',
                'deleteFleet',
                'fetchClients',
                'addClientNames'
            ])
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
