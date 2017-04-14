<template>
    <form class="form-horizontal col-xs-12 col-sm-11 col-md-9 col-lg-7">
        <slot></slot>
        <div class="row">
            <button-link :route="failroute" buttonClass="pull-right btn btn-sm btn-default form-component-button">
                {{ $t('common.cancel') | capitalize }}
            </button-link>
            <button-action @click="submit" buttonClass="pull-right btn btn-sm btn-primary form-component-button">
                {{ submitText }}
            </button-action>
        </div>
    </form>
</template>
<script>
    import {getResourceActionText} from '../../utils/utils'
    import buttonLink from '../buttons/buttonLink.vue'
    import buttonAction from '../buttons/buttonAction.vue'

    export default {
        data(){
            return {
                failroute: {name: this.resource.name.plural()}, //The failroute is the index page of the resource.
                submitText:  getResourceActionText(this.resource.name, this.actions.name)

            }
        },
        components: {
            buttonLink, buttonAction
        },
        props: {
            actions: Object, //The action of this form
            resource: Object, //The name of the resource configured by this form
            object: Object //The resource configured by this form
        },
        created(){
            document.addEventListener("keyup", e => {
                if(e.keyCode === 13){
                    this.submit()
                }
            })
        },
        methods: {
            submit(){
                this.$store.dispatch(this.actions.name + this.resource.name.capitalize(), this.object).then(object => {
                    if(this.resource.name === 'vehicle'){ //Exception
                        this.$router.push({name: 'fleet', params: {id: object.fleet} })
                    }
                    else {
                        this.$router.push({name: this.resource.name.plural()})
                    }
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