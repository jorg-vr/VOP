<template>
    <button-link :route="route" buttonClass="btn btn-danger btn-md" buttonId="fail">
        <i  class="fa fa-times"></i>
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
