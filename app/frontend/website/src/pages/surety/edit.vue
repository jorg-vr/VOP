<!--
    This page is used to edit a certain surety.

    @param id: the id of the surety which is edited.
-->
<template>
    <abstract-form :actions="actions" :object="surety" :back="back" :resource="resource">
        <surety-form-input :object="surety" :actions="actions"></surety-form-input>
    </abstract-form>
</template>

<script>
    import {mapActions,mapMutations} from 'vuex'
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import suretyFormInput from './suretyFormInput.vue'

    export default {
        data(){
            return {
                actions: actions.UPDATE,
                resource: resources.SURETY,
                surety:{},
                back:{name:resources.SURETY.name.plural()}
            }
        },
        created(){
            if(this.id){
                this.fetchSurety({id: this.id}).then(surety => {
                    console.log(surety)
                    this.surety = surety;
                    this.setSelectedConditions(this.surety.specialConditions)
                })
            }
        },
        components: {
            abstractForm,suretyFormInput
        },
        props: {
            id: String
        },
        methods: {
            ...mapActions([
                'fetchSurety'
            ]),
            ...mapMutations([
                'setSelectedConditions',
                ]),


        }
    }
</script>



