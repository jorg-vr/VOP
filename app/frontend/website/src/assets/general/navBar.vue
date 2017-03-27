<!--
    Navigation bar of the website
-->
<template>
    <div id="sidebar-wrapper">
        <ul class="sidebar-nav">
            <li class="sidebar-brand">Solvas fleet</li>
            <li><router-link :to="{name: 'fleets'}">{{$t("fleet.fleets") | capitalize}}</router-link></li>
            <li><router-link :to="{name: 'users'}">{{$t("user.users") | capitalize}}</router-link></li>
            <li><router-link :to="{name: 'clients'}">{{$t("client.clients") | capitalize}}</router-link></li>
            <li><a href="#">About</a></li>
            <li><a href="#">Services</a></li>
            <li><a href="#">Contact</a></li>
        </ul>
        <language-switcher></language-switcher>
    </div>
</template>
<script>
    import languageSwitcher from './languageSwitcher.vue'
    export default {
        components: {
            languageSwitcher
        }
    }
</script>

