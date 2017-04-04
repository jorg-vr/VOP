<template>
    <div class="container-fluid text-center exception-page">
        <div class="page-header"><h1>{{statusCode}}</h1><h2>{{$t('exception.e' + statusCode)}}</h2></div>

        <button-link :route="{name: 'home'}" buttonClass="btn btn-primary">{{$t('exception.return')}}</button-link>
        <button class="btn btn-primary">{{$t('exception.contact')}}</button>
    </div>
</template>
<script>
    import buttonLink from '../../assets/buttons/buttonLink.vue'

    export default {
        props: {
            statusCode: Number,
            message: String,
            details: String
        },
        components: {
            buttonLink
        }
    }
</script>
<style>
    .exception-page h1, .exception-page h2 {
        color: #333;
    }
</style>