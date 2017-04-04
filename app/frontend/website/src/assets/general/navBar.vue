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
                </ul><!-- /.navbar-nav -->

                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <p class="navbar-text">Admin</p>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" ariahaspopup="true" aria-expanded="false">Username <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="#">View account</a>
                            </li>
                            <li role="separator" class="divider"></li>
                            <li>
                                <a href="#">Log out</a>
                            </li>
                        </ul>
                    </li>
                </ul><!-- ./navbar-right -->
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav><!-- /.navbar -->
</template>
<script>
import languageSwitcher from './languagePicker.vue'
    export default {
        components: {
            languageSwitcher
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
</style>
