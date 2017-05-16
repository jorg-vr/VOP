<!--
    This page is used to edit a certain user.

    @param id: The id of the user which is edited.


-->
<template>
    <abstract-form :actions="actions" :back="back" :resource="resource" :object="functionObj" :ids="params">
        <form-input :object="functionObj"></form-input>
    </abstract-form>
</template>
<script>
    import {mapActions} from 'vuex'
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import formInput from './FunctionFormInput.vue'

    export default {
        data(){
            return {
                resource: resources.FUNCTION,
                actions: actions.CREATE,
                functionObj: {user: this.userId},
                back:{name:resources.USER.name, params: {id: this.userId}},
                params: {userId: this.userId}
            }
        },
        created(){
            console.log(this.params)
        },
        components: {
            abstractForm,formInput
        },
        props: {
            userId: String
        }
    }
</script>


