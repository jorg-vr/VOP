<!--
    Component usable creating a dropdown with different pages
-->
<template>
    <li class="dropdown">
        <a href="#" class="dropdown-toggle"  role="button" ariahaspopu="true" aria-expanded="false">
            <resources-link :resource="title" ></resources-link>
             <span class="caret"></span>
        </a>
        <ul class="dropdown-menu">
            <li v-for="resource in resources">
                <resources-link :resource="resource"></resources-link>
            </li>
        </ul>
    </li>

</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import ResourcesLink from './ResourcesLink.vue'
    export default {
        props:{
            resources:Array,
            title:Object
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
