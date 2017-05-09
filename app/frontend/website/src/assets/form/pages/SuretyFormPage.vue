<!--
This page is used to edit or create a certain insurance.

@param id (optional): ID of the old object
@param actions: the action this form is intended for (create/update)
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1>{{ title }}</h1>
        </div>
        <surety-form :actions="actions" :oldSurety="oldSurety"></surety-form>
    </div>
</template>
<script>
    import SuretyForm from '../../../assets/form/types/SuretyForm.vue'
    import actions from '../../../constants/actions'
    import {mapGetters, mapActions,mapMutations} from 'vuex'
    import {getResourceActionText} from '../../../utils/utils'

    export default {
        data(){
            return {
                title: getResourceActionText('surety', this.actions.name),
                oldSurety: null
            }
        },
        components: {
            SuretyForm
        },
         created(){
           if(this.id){
                this.fetchSurety({id:this.id}).then(surety => {
                    this.oldSurety = surety
                    console.log(this.oldSurety)
                })
            }
           
        },
        props: {
            id: String,
            actions: Object //The action for this form.
        },
        computed: {
            ...mapGetters([
                'surety',
                'suretyData',
                'suretyDetail',
                'insurance',
                'contractId'
            ])
        },
        methods: {
            ...mapActions([
                'fetchSurety',
                'fetchSuretyDetail'
            ]),
            ...mapMutations([
                'clearSurety'
                
            ]),
        }
    }
</script>