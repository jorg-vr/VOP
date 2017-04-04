<!--
    Navigation bar of the website
-->
<template>
    <div id="sidebar-wrapper">
        <ul class="sidebar-nav">
            <li class="sidebar-brand">Solvas fleet</li>
            <!-- User info dropdown -->
            <li class="dropdown">
                <a data-toggle="collapse" data-target="#submenu">
                        <span><i aria-hidden="true" class="fa fa-user" id="usericon"></i></span>
                            Voornaam <span class="caret"></span>
                        </a>
                        <ul id="submenu" class="collapse">
                            <li> Volledige naam </li>
                            
                            <li> {{this.loginInfo.username}} </li>
                            <li> Rol  </li>
                            <li> <a href="#"> {{$t("login.logout") | capitalize}} </a></li>
                            <li><language-switcher></language-switcher></li>
                            
                        </ul>
            </li>
            <li><router-link :to="{name: 'fleets'}">{{$t("fleet.fleets") | capitalize}}</router-link></li>
            <li><router-link :to="{name: 'users'}">{{$t("user.users") | capitalize}}</router-link></li>
            <li><router-link :to="{name: 'clients'}">{{$t("client.clients") | capitalize}}</router-link></li>
            <li><router-link :to="{name: 'vehicles'}">{{$t("vehicle.vehicles") | capitalize}}</router-link></li>
        </ul>

    </div>
</template>
<script>
    import { mapGetters } from 'vuex'
    import languageSwitcher from './languagePicker.vue'
    export default {
        components: {
            languageSwitcher
        },
        computed: {
            ...mapGetters([
                'loginInfo'
            ])
        },

    }
</script>

<style scoped>

    #submenu li{
        list-style-type: none;
        padding: 0;
        margin: 0;
        text-indent: 0px;
        color: #ecf0f1;
    }

    #usericon{
        margin-right: 5px;
        font-size: 20px;
    }
    .dropdown{
        text-indent: 10px;
    }

    #logout{
        background:#1AB394;
        color:white;
        width: 100px;
        margin: 0;
        margin-top: -50px;
        font-weight: 600;
    }
</style>