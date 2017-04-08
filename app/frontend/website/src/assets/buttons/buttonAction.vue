<template>
    <button type="button" :class="buttonClass" @click="emitClicked" :id="buttonId">
        <slot></slot>
    </button>
</template>
<script>
    export default {
        props: {
            action: Function,
            buttonClass: String,
            buttonId: String
        },
        methods: {
            emitClicked(){
                this.$emit('click')
            }
        }
    }
</script>
