<!--
This page is used to edit or create a certain user.

@param id (optional): ID of the old object
@param actions: the action this form is intended for (create/update)
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1>{{ title }}</h1>
        </div>
        <user-form :actions="actions" :oldUser="oldUser"></user-form>
    </div>
</template>
<script>
    import UserForm from '../../../assets/form/types/userForm.vue'
    import actions from '../../../constants/actions'
    import {mapActions} from 'vuex'
    import {getResourceActionText} from '../../../utils/utils'

    export default {
        data(){
            return {
                title: getResourceActionText('user', this.actions.name),
                oldUser: null
            }
        },
        components: {
            UserForm
        },
        created(){
            if(this.id){
                this.fetchUser({id: this.id}).then(user => {
                    this.oldUser = user
                })
            }
        },
        props: {
            id: String,
            actions: Object //The action for this form.
        },
        methods: {
            ...mapActions([
                'fetchUser'
            ])
        }
    }
</script>

