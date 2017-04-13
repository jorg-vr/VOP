<template>
    <form class="form-horizontal col-xs-12 col-sm-11 col-md-9 col-lg-7">
        <slot></slot>
        <div class="row">
            <button-link :route="failroute" buttonClass="pull-right btn btn-sm btn-default form-component-button">
                {{ $t('common.cancel') | capitalize }}
            </button-link>
            <button-action @click="proceed" buttonClass="pull-right btn btn-sm btn-primary form-component-button">
                {{ $t('actions.' + action, {subject: $t(resource + '.' + resource)}) | capitalize }}
            </button-action>
        </div>
    </form>
</template>
<script>
    import buttonLink from '../buttons/buttonLink.vue'
    import buttonAction from '../buttons/buttonAction.vue'

    export default {
        components: {
            buttonLink, buttonAction
        },
        props: {
            action: String, //The action of this form
            resource: String, //The name of the resource configured by this form
            object: Object //The resource configured by this form
        },
        created(){
            document.addEventListener("keyup", e => {
                if(e.keyCode === 13){
                    this.proceed()
                }
            })
        },
        methods: {
            proceed(){
                this.$store.dispatch(this.action + this.resource.capitalize(), object).then(() => {
                    this.submit(this.client).then(() => {
                        this.$router.push({name: resource.plural()})
                    })
                })
            }
        }
    }
</script>
<style>
    .form-component-button {
        margin-top: 25px;
        margin-left: 20px;
    }
</style>