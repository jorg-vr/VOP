<template>
    <div>
        <h1>Remove Vehicle x</h1>
        <p>In principe moeten we hiervoor geen aparte pagina maken, dit kan in index</p>
        <button v-on:click="removeVehicle">Verwijder</button>
    </div>
</template>
<script>
    export default {
        data : function() {
            return {}
        },
        methods:{
            removeVehicle: function() {
                this.$http.delete('/vehicles/' + $route.params.id)
            }
        }
    }
</script>