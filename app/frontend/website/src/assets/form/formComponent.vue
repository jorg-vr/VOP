<template>
    <form class="form-horizontal col-xs-12 col-sm-11 col-md-9 col-lg-7">
        <slot></slot>
        <div class="row">
            <button-success @click="onSubmit"></button-success>
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
        props: {
            failroute: Object
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
