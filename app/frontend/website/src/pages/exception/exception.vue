<!--
    An exception page. For example if the user routes to a non existing page, this page should be shown instead.

    @param statusCode: the code of the status of the exception.
    @param message: A message shortly explaining the exception.
    @param details: A detailed explanation of the exception.
-->
<template>
    <div class="container-fluid text-center exception-page">
        <div class="page-header"><h1>{{statusCode}}</h1><h2>{{$t('exception.e' + statusCode)}}</h2></div>

        <button-link :route="{name: 'homeClient'}" buttonClass="btn btn-primary">{{$t('exception.return')}}</button-link>
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