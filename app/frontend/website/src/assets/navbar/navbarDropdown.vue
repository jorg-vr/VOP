<!--
    Component usable creating a dropdown with different pages
-->
<template>
    <li  v-if="list.length>1" class="dropdown">
        <a href="#" class="dropdown-toggle"  role="button" ariahaspopu="true" aria-expanded="false">
            <resources-link :resource="title" ></resources-link>
            <span class="caret"></span>
        </a>
        <ul class="dropdown-menu">
            <li v-for="resource in list">
                <resources-link :resource="resource"></resources-link>
            </li>
        </ul>

    </li>
    <li  v-else-if="title">
        <resources-link :resource="title" ></resources-link>
    </li>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import ResourcesLink from './ResourcesLink.vue'
    import {PagePermissions} from '../../constants/PagePermissions'
    import actions from '../../constants/actions'
    export default {
        data(){
            return {
                title:{},
                list:[]
            }
        },
        created(){
            for(let i=0;i<this.resources.length;i++){
                let permission= PagePermissions[actions.READ_ALL.path(this.resources[i].name)];
                if(this.$store.getters.hasPermission(permission)){
                    this.list.push(this.resources[i]);
                }
            }
            this.title=this.list[0];
        },
        props:{
            resources:Array,
        },
        components: {
            ResourcesLink
        }
    }
</script>
<style>
    a a{
        color: white;
    }

    a a:hover, a a:focus{
        text-decoration: none;
    }
    .dropdown:hover .dropdown-menu {
        display: block;
    }
</style>
