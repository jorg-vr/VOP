<template>
    <button-link :route="route"  buttonClass="btn btn-md btn-info">
        <i aria-hidden="true" class="fa fa-pencil"></i>
    </button-link>
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
