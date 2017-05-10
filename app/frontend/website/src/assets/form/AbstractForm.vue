<!--
Generic component for a form. Every form should be encapsulated in this component.

@param actions: The action this form is intended for.
@param resource: The name of the resource configured by this form
@param object: The resource configured by this form
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1>{{ submitText }}</h1>
        </div>
        <form class="form-horizontal col-xs-12 col-sm-11 col-md-9 col-lg-7">
            <slot></slot>
            <div class="row">
                <!-- <button-link :route="back" buttonClass="pull-right btn btn-sm btn-default form-component-button">
                    {{ $t('common.cancel') | capitalize }}
                </button-link> -->
                <button-action @click="cancel" buttonClass="pull-right btn btn-sm btn-default form-component-button">
                    {{ $t('common.cancel') | capitalize }}
                </button-action>
                <button-action @click="submit" buttonClass="pull-right btn btn-sm btn-primary form-component-button">
                    {{ submitText }}
                </button-action>
            </div>
        </form>
    </div>
</template>
<script>
    import {getResourceActionText} from '../../utils/utils'
    import buttonLink from '../buttons/buttonLink.vue'
    import buttonAction from '../buttons/buttonAction.vue'
    import { mapGetters, mapActions, mapMutations } from 'vuex'

    export default {
        data(){
            return {
                submitText:  getResourceActionText(this.resource.name, this.actions.name)
            }
        },
        components: {
            buttonLink, buttonAction
        },
        props: {
            back: Object, //link to previous page
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

        computed: {
            ...mapGetters([
                'contractId'
            ])
        },
        methods: {
            /**
             * Submit this form. This will create/update the object configured by this fleet and will navigate to the
             * index page of the resource of the object.
             */
            submit(){
                this.$store.dispatch(this.actions.name + this.resource.name.capitalize(), {resource:this.object,ids:this.contractId}).then(object => {
                    this.$router.go(-1)
                })
            },
            cancel(){
                this.$router.go(-1)
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