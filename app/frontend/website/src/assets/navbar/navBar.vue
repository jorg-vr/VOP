<!--
    Navigation bar of the website
-->
<template>
    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <!-- Header with button to toggle on mobile and brand logo -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <router-link :to="{name: 'homeClient'}" class="navbar-brand">SOLVAS FLEET</router-link>
            </div><!-- /.navbar-header -->
            
            <!-- Navbar with toggable links -->
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                     <!-- conditional group rendering for navbar links-->
                    <template v-if="hasActiveAccount">
                        <dropdown :resources="[resources.CLIENT,resources.FLEET,resources.VEHICLE]"></dropdown>
                        <dropdown :resources="[resources.USER,resources.ROLE]"></dropdown>
                        <dropdown :resources="[resources.CONTRACT,resources.CONDITION,resources.VEHICLE_TYPE]"></dropdown>
                        <li>
                            <billing-resource-link></billing-resource-link>
                        </li>
                    </template>
                </ul><!-- /.navbar-nav -->

                <ul class="nav navbar-nav navbar-right">
                    <li v-if="hasActiveAccount == false">
                        <router-link :to="{path: '/login'}"> {{$t("login.login") | capitalize }}  </router-link>
                    </li>
                    <function-picker></function-picker>
                    <!-- condition group rendering for navbar login info-->
                    <template v-if="hasActiveAccount">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" ariahaspopup="true" aria-expanded="false">
                        {{activeAccount.firstName}} <span class="caret"></span></a>
                        <ul class="dropdown-menu">  
                            <li><a class="no-hover">{{activeAccount.email}}</a></li>
                            <li role="separator" class="divider"></li>
                            <li>
                                <router-link :to="{name: 'user', params: {id: activeAccount.id}}">
                                    {{$t('actions.view', {subject: "account"}) | capitalize}}
                                </router-link>
                            </li>
                            <li>
                                <a @click='confirmLogout()' class="pointer"> {{$t("login.logout") | capitalize }} </a>
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
import functionPicker from './functionPicker.vue'
import formSelect from '../form/FormGroups/SelectInputFormGroup.vue'
import ResourcesLink from './ResourcesLink.vue'
import dropdown from './navbarDropdown.vue'
import billingResourceLink from './billingResourceLink.vue'

    export default {
        data(){
            return {
                resources: resources,
            }
        },
        components: {
            functionPicker, formSelect, ResourcesLink,dropdown,billingResourceLink
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
        },
        mounted() {
            $(document).on('click','.navbar-collapse.in',function(e) {
                if( $(e.target).is('a') ) {
                    $(this).collapse('hide');
                }
            });
        }
    }
</script>
<style>
    .navbar-default .navbar-brand:hover, .navbar-default .navbar-brand:focus {
        color: white;
    }
    .navbar-default .navbar-nav>.open>a, .navbar-default .navbar-nav>.open>a:hover, .navbar-default .navbar-nav>.open>a:focus {
        background-color: #2c3e50;
        color: #18bc9c;
    }
    .nav>li>a {
        padding-right: 5px;
    }
    .nav>li:last-child {
        margin-right: 10px;
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

    #logout{
        background:#1AB394;
        color:white;
        width: 100px;
        margin: 0;
        margin-top: -50px;
        font-weight: 600;
    }
    .pointer {
        cursor:pointer;
    }
    .dropdown-menu>li>a.no-hover:hover {
        color: #7b8a8b;
        background-color: white;
    }
</style>
