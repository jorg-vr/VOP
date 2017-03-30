<template>
    <form class="form-horizontal">
        <slot></slot>
        <div class="row">
            <button-success @click="onSubmit"></button-success>
            <button-fail :route="failroute"></button-fail>
        </div>
    </form>
</template>
<script>
    import buttonFail from '../buttons/buttonFail.vue'
    import buttonSuccess from '../buttons/buttonSuccess.vue'

    export default {
        components: {
            buttonFail, buttonSuccess
        },
        created(){
            document.addEventListener("keyup", e => {
                if(e.keyCode === 13){
                    this.onSubmit()
                }
            })
        },
        methods: {
            onSubmit(){
                this.$emit('submit')
            }
        }
    }
</script>
