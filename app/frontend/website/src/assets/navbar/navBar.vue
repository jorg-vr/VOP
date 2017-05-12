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
                <router-link :to="{name: 'homeClient'}" class="navbar-brand">SOLVAS FLEET</router-link>
            </div><!-- /.navbar-header -->
            
            <!-- Navbar with toggable links -->
            <div class="collapse navbar-collapse" id="navbar-collapse-1">
                <ul class="nav navbar-nav">
                     <!-- conditional group rendering for navbar links-->
                    <template v-if="hasActiveAccount">
                        <li>
                            <resources-link :resource="resources.FLEET"></resources-link>
                        </li>
                        <li>
                            <resources-link :resource="resources.USER"></resources-link>
                        </li>
                        <li>
                            <resources-link :resource="resources.CLIENT"></resources-link>
                        </li>
                        <li>
                            <resources-link :resource="resources.VEHICLE"></resources-link>
                        </li>
                        <li>
                            <resources-link :resource="resources.CONTRACT"></resources-link>
                        </li>
                        <li>
                            <resources-link :resource="resources.VEHICLE_TYPE"></resources-link>
                        </li>
                    </template>
                </ul><!-- /.navbar-nav -->

                <ul class="nav navbar-nav navbar-right">
                    <li v-if="hasActiveAccount == false">
                        <router-link :to="{path: '/login'}"> {{$t("login.login") | capitalize }}  </router-link>
                    </li>
                    <li>
                        <language-picker></language-picker>
                    </li>
                    <li>
                        <function-picker></function-picker>
                    </li>
                    <!-- condition group rendering for navbar login info-->
                    <template v-if="hasActiveAccount">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" ariahaspopup="true" aria-expanded="false">
                        {{activeAccount.email}}<span class="caret"></span></a>
                        <ul class="dropdown-menu">  
                            <li role="separator" class="divider"></li>
                            <li>
                                <a href="#">View activeAccount</a>
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
import resources from '../../constants/resources'
import languagePicker from './languagePicker.vue'
import functionPicker from './functionPicker.vue'
import formSelect from '../form/FormGroups/SelectInputFormGroup.vue'
import ResourcesLink from './ResourcesLink.vue'

    export default {
        data(){
            return {
                resources: resources
            }
        },
        components: {
            languagePicker, functionPicker, formSelect, ResourcesLink
        },
        computed: {
            ...mapGetters([
                'hasActiveAccount', 'activeAccount'
            ])
        },
        methods:{
            ...mapActions([
                'logout'
            ]),
            confirmLogout:function(){
                this.logout()
                this.$router.push({path: '/login'})
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
