<template>
    <button-link :route="route"  buttonClass="btn btn-primary btn-circle btn-lg" buttonId="add">+</button-link>
</template>
<script>
    import buttonLink from './buttonLink.vue'

    export default {
        components: {
            buttonLink
        },
        props: {
            route: Object,
        }
    }
</script>
