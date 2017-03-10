<template>
    <div id="sidebar-wrapper">
        <ul class="sidebar-nav">
            <li class="sidebar-brand">Solvas fleet</li>
            <li><a href="#">Vloten</a></li>
            <li><a href="#">Subvloten</a></li>
            <li><a href="#">Voertuigen</a></li>
            <li v-on:click="goToUser"><a href="#"> Gebruikers </a></li>
            <li v-on:click="goToClient"><a href="#"> Klanten </a></li>
            <li><a href="#">About</a></li>
            <li><a href="#">Services</a></li>
            <li><a href="#">Contact</a></li>
        </ul>
    </div>


</template>
<script>
    export default {
        methods:{
                // Methods for routing purposes 
                goToUser: function () {
                            this.$router.push({ path: 'identity', query: { type: 'Gebruiker' }})
                },
                goToClient: function () {
                            this.$router.push({ path: 'identity', query: { type: 'Klant' }})
                },
        }

    }
</script>


