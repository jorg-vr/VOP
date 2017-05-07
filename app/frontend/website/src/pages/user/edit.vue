<!--
    This page is used to edit a certain user.

    @param id: The id of the user which is edited.


-->
<template>
    <abstract-form :actions="actions" :object="user" :back="back" :resource="resource">
        <form-input :user="user"></form-input>
    </abstract-form>
</template>
<script>
    import {mapActions} from 'vuex'
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import formInput from '../../assets/form/types/userFormInput.vue'

    export default {
        data(){
            return {
                resource: resources.USER,
                actions: actions.UPDATE,
                user: {},
                back:{name:resources.USER.name.plural()}
            }
        },
        created(){
            if(this.id){
                this.fetchUser({id: this.id}).then(o => {
                    this.user = o;
                })
            }
        },
        components: {
            abstractForm,formInput
        },
        props: {
            id: String
        },
        methods: {
            ...mapActions([
                'fetchUser'
            ])

        }
    }
</script>


