<!--
    Navigation bar of the website
-->
<template>

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-static-side" role="navigation">
        <div id="sidebar-collapse">
            <ul class="nav">
                <li class="sidebar-brand">Solvas fleet</li>
                <li><router-link :to="{name: 'fleets'}">{{$t("fleet.fleets") | capitalize}}</router-link></li>
                <li><router-link :to="{name: 'users'}">{{$t("user.users") | capitalize}}</router-link></li>
                <li><router-link :to="{name: 'clients'}">{{$t("client.clients") | capitalize}}</router-link></li>
                <li><router-link :to="{name: 'vehicles'}">{{$t("vehicle.vehicles") | capitalize}}</router-link></li>
            </ul>
            <language-switcher></language-switcher>
        </div>
    </nav>
</template>
<script>
import languageSwitcher from './languagePicker.vue'
    export default {
        components: {
            languageSwitcher
        }
    }
</script>

<style lang = "less">
navbar {
  border: 0;
  z-index: 2000;
}
.navbar-default {
  background-color :#2f4050;
}
.navbar-top-links li {
  display: inline-block;
}
.navbar-top-links li:last-child {
  margin-right: 40px;
}
.nav > li > a {
  color: #a7b1c2;
  font-weight: 600;
  padding: 14px 20px 14px 25px;
}
.nav > li.active > a {
  color: #ffffff;
}
.navbar-default .nav > li > a:hover,
.navbar-default .nav > li > a:focus {
  background-color: #293846;
  color: white;
}
.navbar-static-side {
  width: 220px;
  position: absolute;
}
.nav-header {
    padding-top: 33px;
    padding-bottom: 25px;
    box-sizing: content-box;
    height: 100px;
}
hr {
    margin-top: 0;
}
</style>
