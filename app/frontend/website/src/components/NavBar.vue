<template>
    <div id="sidebar-wrapper">
        <ul class="sidebar-nav">
            <li class="sidebar-brand">Solvas fleet</li>
            <li><router-link :to="{name: 'fleets'}">Vloten</router-link></li>
            <li><a href="#">Subvloten</a></li>
            <li><router-link :to="{name: 'vehicles'}">Voertuigen</router-link></li>
            <li><router-link :to="{name: 'identities', query: {type:'Gebruiker'}}">Gebruikers</router-link></li>
            <li><router-link :to="{name: 'identities', query: {type:'Klant'}}">Klanten</router-link></li>
            <li><a href="#">About</a></li>
            <li><a href="#">Services</a></li>
            <li><a href="#">Contact</a></li>
        </ul>
    </div>
</template>
<script>
    //TODO: Change CSS of currently selected list item
    export default {}
</script>

