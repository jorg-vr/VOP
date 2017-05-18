<template>
    <form>
        <errors></errors>
        <slot></slot>
        <div class="row col-md-12">
            <button-back :route="back" buttonClass="pull-right btn btn-sm btn-default form-component-button"
                         :text="$t('common.cancel')">
            </button-back>
            <button-action @click="SubmitFormHandler.submit()" buttonClass="pull-right btn btn-sm btn-primary form-component-button">
                {{ submitText }}
            </button-action>
        </div>
    </form>
</template>
<script>
    import {getResourceActionText} from '../../utils/utils'
    import buttonBack from '../buttons/buttonBack.vue'
    import buttonAction from '../buttons/buttonAction.vue'
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import {SubmitFormHandler} from './SubmitFormHandler'
    import errors from './AbstractFormErrors.vue'

    export default {
        data(){
            return {
                submitText:  getResourceActionText(this.resource.name, this.actions.name),
                SubmitFormHandler: SubmitFormHandler
            }
        },
        components: {
            buttonAction, buttonBack, errors
        },
        props: {
            back: Object, //link to previous page
            actions: Object, //The action of this form
            resource: Object, //The name of the resource configured by this form
            object: [Object,Array], //The resource configured by this form
            ids: Object, //Object with id's for creating the correct POST/PUT route.
            customSubmit: Function //Custom submit function (optional)
        },
        created(){
            this.$on('mounted', components => this.initializeFormHandler(components))
            document.addEventListener("keyup", e => {
                if(e.keyCode === 13){
                    SubmitFormHandler.submit();
                }
            })
            SubmitFormHandler.setSubmitFunction(this.submit)
        },
        computed: {
            ...mapGetters([
                'error'
            ])
        },
        methods: {
            /**
             * Submit this form. This will create/update the object configured by this fleet and will navigate to the
             * index page of the resource of the object.
             */
            submit(){
                let promise
                if(this.customSubmit){
                    promise = this.customSubmit()
                }
                else {
                    let functionName = this.actions.name + this.resource.name.capitalize()
                    promise = this.$store.dispatch(functionName, {resource: this.object, ids: this.ids})
                }
                promise.then(() => {
                    this.$store.commit('setIsGoingBack', {status: true})
                    let redirectRoute = this.$store.getters.popVisitedRoute;
                    while(!(redirectRoute===undefined || redirectRoute.name===null) && redirectRoute.path === this.$route.path){
                        redirectRoute = this.$store.dispatch('popVisitedRoute');
                    }
                    if(redirectRoute===undefined || redirectRoute.name===null){
                        this.$router.push({name: this.back.name, params: this.back.params})
                    }
                    else {
                        this.$router.push(redirectRoute.path)
                    }

                })
            },
            initializeFormHandler(components){
                SubmitFormHandler.setInputComponents(components)
                SubmitFormHandler.setSubmitFunction(this.submit)
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