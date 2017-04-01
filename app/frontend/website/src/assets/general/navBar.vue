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
            <li><router-link :to="{name: 'vehicles'}">{{$t("vehicle.vehicles") | capitalize}}</router-link></li>
        </ul>
        <language-switcher></language-switcher>
    </div>
</template>
<script>
    import languageSwitcher from './languagePicker.vue'
    export default {
        components: {
            languageSwitcher
        }
    }
</script>

