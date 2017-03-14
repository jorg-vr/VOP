<template>
    <div>
        <div class="page-header">
            <h1>Vloten </h1>
        </div>
        <info-pane v-for="fleet in fleets"
                   :textValues="new Array(fleet.name, fleet.company)"
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
            'info-pane': infoPane
        },
        data: function () {
            return {
                fleets : [ //Some test fleets, this will be filled in with the actual fleets
                    {id: 1, name: 'Vloot 1', company : '1'},
                    {id: 2, name: 'Vloot 2', company : '2'},
                    {id: 3, name: 'Vloot 3', company : '3'},
                    {id: 4, name: 'Vloot 4', company : '4'}
                ]
            }
        },
        created() {
            this.fetchFleetList()
        },
        methods: {
            fetchFleetList (){
                this.$http.get('https://vopro5.ugent.be/app/api/fleets').then(response => {
                    this.fleets = response.body;
                })
            },
            deleteFleet(){
                //TODO
            },
            createFleet(){
                //TODO
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
