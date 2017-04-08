<template>
    <form class="form-horizontal col-xs-12 col-sm-11 col-md-9 col-lg-7">
        <slot></slot>
        <div class="row">
            <button-link :route="failroute" buttonClass="pull-right btn btn-sm btn-default form-component-button">{{failButtonText}}</button-link>
            <button-action @click="onSubmit" buttonClass="pull-right btn btn-sm btn-default form-component-button">{{successButtonText}}</button-action>
        </div>
    </form>
</template>
<style>
.form-component-button {
    margin-top: 25px;
    margin-left: 20px;
}
</style>
<script>
    import buttonLink from '../buttons/buttonLink.vue'
    import buttonAction from '../buttons/buttonAction.vue'

    export default {
        components: {
            buttonLink, buttonAction
        },
        props: {
            failroute: Object,
            failButtonText: String,
            successButtonText: String
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
