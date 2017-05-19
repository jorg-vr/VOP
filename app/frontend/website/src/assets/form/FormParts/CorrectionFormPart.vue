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
            <div v-if="error" class="row text-center">
                <ul class="list-group">
                    <li class="list-group-item list-group-item-danger" v-for="errorEle in error.errors">
                        {{$t('error.' + errorEle.message, {subject: $t('field.' + errorEle.field)}) | capitalize}}
                    </li>
                </ul>
            </div>
            <slot></slot>
            <div class="row">
                <button-back :route="back" buttonClass="pull-right btn btn-sm btn-default form-component-button"
                             :text="$t('common.cancel')">
                </button-back>
                <button-action @click="SubmitFormHandler.submit()" buttonClass="pull-right btn btn-sm btn-primary form-component-button">
                    {{ submitText }}
                </button-action>
            </div>
        </form>

                <!-- Confirmation Modam -->
        <confirm-modal v-show="showModal"
                       @cancelModal="showModal=false"
                       @confirmModal="confirmCorrection()"
                       @close="showModal=false"
                       :modalHeaderTitle=" $t('modal.titleCorrection') | capitalize"
                       :modalBodyText="$t('modal.bodyCorrection') + $t('modal.textConfirm') | capitalize"
                       :confirmButtonText="$t('modal.button1') | capitalize "
                       :cancelButtonText="$t('modal.button2') | capitalize ">
        </confirm-modal>

    </div>
</template>
<script>
    import {getResourceActionText} from '../../../utils/utils'
    import buttonBack from '../../buttons/buttonBack.vue'
    import buttonAction from '../../buttons/buttonAction.vue'
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import {SubmitFormHandler} from '../SubmitFormHandler'
    import confirmModal from '../../../assets/general/modal.vue'

    export default {
        data(){
            return {
                submitText:  getResourceActionText(this.resource.name, this.actions.name),
                SubmitFormHandler: SubmitFormHandler,
                showModal:false,
                checked: false
            }
        },
        components: {
            buttonAction, buttonBack,confirmModal
        },
        props: {
            back: Object, //link to previous page
            actions: Object, //The action of this form
            resource: Object, //The name of the resource configured by this form
            object: Object, //The resource configured by this form
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
                'error',
            ])
        },
        methods: {
            /**
             * Submit this form. This will create/update the object configured by this fleet and will navigate to the
             * index page of the resource of the object.
             */
            submit(){
                let promise
                if(!this.correctionHandler()){
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
                }
            },
            initializeFormHandler(components){
                SubmitFormHandler.setInputComponents(components)
                SubmitFormHandler.setSubmitFunction(this.submit)
            },

            /*  Function to check if a correction needs to be performed. 
                It will check if the current resource is an insurances and
                show the modal if the start date of this insurance is in the past
                by calling datesHandler()

            */
            correctionHandler(){
                if(this.checked == false){
                    // check dates
                    if(this.datesHandler()){
                        this.showModal = true
                        return true
                    }
                }
                return false
            },

            /* Function used to check if start date is in the past 
                @return true if start date is in the past
                @return false if start is current date or in the future
            */
            datesHandler(){
                var d = new Date(this.object.startDate.substring(0,4), this.object.startDate.substring(5,7)-1,this.object.startDate.substring(8,10))
                var dnow = new Date();
                // Make sure current date is not considered as date in the past
                dnow.setHours(0,0,0,0);
                if (d < dnow) {
                  return true
                }
                else{
                    return false
                }
            },
            /*  Function to handle request to perform a correction
                It will create a correction object with the right fields
                and call the createCorrction function. Finally it calls
                the submit() function again to continue the submission of the form.
            */

            confirmCorrection: function(){
                // Allow submit method to continue
                this.checked = true
                this.submit()
            },
        }
    }
</script>
<style>
    .form-component-button {
        margin-top: 25px;
        margin-left: 20px;
    }
    .errors {
        margin-left: 50px;
    }

    .list-group-item-danger {
        color: #a94442;
        background-color: #f2dede;
    }
</style>