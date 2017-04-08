<template>
    <button-action @click="emitClicked" buttonClass="btn btn-default btn-sm btn-succes pull-right" buttonId="success">Voeg gebruiker toe</button-action>
</template>

<style>

</style>

<script>
    import buttonAction from './buttonAction.vue'

    export default {
        components: {
            buttonAction
        },
        mixins: [buttonAction]
    }
</script>

