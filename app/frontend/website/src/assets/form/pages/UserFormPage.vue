<!--
    This page is used to edit a certain user.
-->
<template>
    <div id="content-wrapper">
        <div class="page-header">
            <h1>{{ title }}</h1>
        </div>
        <user-form :actions="actions" :oldUser="user"></user-form>
    </div>
</template>
<script>
    import UserForm from '../../../assets/form/types/userForm.vue'
    import actions from '../../../constants/actions'
    import {mapGetters, mapActions} from 'vuex'
    import {getResourceActionText} from '../../../utils/utils'

    export default {
        data(){
            return {
                title: getResourceActionText('user', this.actions.name)
            }
        },
        components: {
            UserForm
        },
        created(){
            if(this.id){
                this.fetchUser({id: this.id})
            }
        },
        props: {
            id: String,
            actions: Object //The action for this form.
        },
        computed: {
            ...mapGetters([
                'user'
            ])
        },
        methods: {
            ...mapActions([
                'fetchUser',
            ])
        }
    }
</script>

