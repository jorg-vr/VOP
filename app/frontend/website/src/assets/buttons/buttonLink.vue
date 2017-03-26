<template>
    <router-link :to="route">
        <button type="button" :class="buttonClass" :id="buttonId">
            <slot></slot>
        </button>
    </router-link>
</template>
<script>
    export default {
        props: {
            route: Object,
            buttonClass: String,
            buttonId: String
        }
    }
</script>