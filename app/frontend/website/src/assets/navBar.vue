<template>
    <div id="sidebar-wrapper">
        <ul class="sidebar-nav">
            <li class="sidebar-brand">Solvas fleet</li>
            <li><router-link :to="{name: 'fleets'}">Vloten</router-link></li>
            <li><router-link :to="{name: 'users'}">Gebruikers</router-link></li>
            <li><router-link :to="{name: 'clients'}">Klanten</router-link></li>
            <li><a href="#">About</a></li>
            <li><a href="#">Services</a></li>
            <li><a href="#">Contact</a></li>
        </ul>
    </div>
</template>
<script>
    export default {}
</script>

