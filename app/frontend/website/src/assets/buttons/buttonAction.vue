<template>
    <button type="button" :class="buttonClass" @click="emitClicked">
        <slot></slot>
    </button>
</template>
<script>
    export default {
        props: {
            action: Function,
            buttonClass: String,
            htmlClass: String //Class of the button
        },
        methods: {
            emitClicked(){
                this.$emit('click')
            }
        }
    }
</script>