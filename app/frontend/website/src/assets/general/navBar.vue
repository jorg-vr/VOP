<!--
    Navigation bar of the website
-->
<template>
    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <!-- Header with button to toggle on mobile and brand logo -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse-1" aria-expanded="false">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <router-link :to="{name: 'home'}" class="navbar-brand">SOLVAS FLEET</router-link>
            </div><!-- /.navbar-header -->
            
            <!-- Navbar with toggable links -->
            <div class="collapse navbar-collapse" id="navbar-collapse-1">
                <ul class="nav navbar-nav">
                     <!-- conditional group rendering for navbar links-->
                    <template v-if="hasActiveAccount">
                    <li>
                        <router-link :to="{name: 'fleets'}">{{$t("fleet.fleets") | capitalize}}</router-link>
                    </li>
                    <li>
                        <router-link :to="{name: 'users'}">{{$t("user.users") | capitalize}}</router-link>
                    </li>
                    <li>
                        <router-link :to="{name: 'clients'}">{{$t("client.clients") | capitalize}}</router-link>
                    </li>
                    <li>
                        <router-link :to="{name: 'vehicles'}">{{$t("vehicle.vehicles") | capitalize}}</router-link>
                    </li>
                    </template>
                </ul><!-- /.navbar-nav -->

                <ul class="nav navbar-nav navbar-right">
                    <li v-if="hasActiveAccount == false">
                        <router-link :to="{name: 'login'}"> {{$t("login.login") | capitalize }}  </router-link>
                    </li>
                    <li>
                        <language-switcher></language-switcher>
                    </li>
                    <!-- condition group rendering for navbar login info-->
                    <template v-if="hasActiveAccount">
                    <li v-if="accountFunction">
                        <p class="navbar-text">Logged in as {{accountFunction.roleName}}</p>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" ariahaspopup="true" aria-expanded="false">
                        {{account.email}}<span class="caret"></span></a>
                        <ul class="dropdown-menu">  
                            <li role="separator" class="divider"></li>
                            <li>
                                <a href="#">View account</a>
                            </li>
                            <li>
                                <a @click='confirmLogout()'> {{$t("login.logout") | capitalize }} </a>
                            </li>
                        </ul>
                    </li>
                    </template>
                </ul><!-- ./navbar-right -->
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav><!-- /.navbar -->
</template>
<script>
import { mapGetters, mapActions, mapMutations } from 'vuex'
import languageSwitcher from './languagePicker.vue'
import formSelect from '../../assets/form/elements/formSelect.vue'
import * as types from '../../store/constants/mutationTypes'

    export default {
        components: {
            languageSwitcher, formSelect
        },
        computed: {
            ...mapGetters([
                'hasActiveAccount','account', 'accountFunction'
            ])
        },
        methods:{
            ...mapActions([
                'logout',
                'loginInfo'
            ]),
            ...mapMutations({
                setActiveFunction: types.SET_ACTIVE_FUNCTION
            }),
            confirmLogout:function(){
                this.logout()
                this.$router.push({name: 'login'})
            }
        }
    }
</script>
<style>
    .navbar-right {
        margin-right: 0px;
    }
    .navbar-default .navbar-brand:hover, .navbar-default .navbar-brand:focus {
        color: white;
    }
    .navbar-default .navbar-nav>.open>a, .navbar-default .navbar-nav>.open>a:hover, .navbar-default .navbar-nav>.open>a:focus {
        background-color: #2c3e50;
        color: #18bc9c;
    }

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
