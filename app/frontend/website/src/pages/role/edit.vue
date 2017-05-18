<template>
    <role-form :id="id" :role="role" :actions="actions" :back="back"></role-form>
</template>
<script>
    import {mapActions} from 'vuex'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import RoleForm from './RoleForm.vue'

    export default {
        data(){
            return {
                actions: actions.UPDATE,
                role: {},
                back:{name:resources.ROLE.name.plural()}
            }
        },
        created(){
            this.fetchRole({id: this.id}).then(role => {
                this.role = role;
            })
        },
        components: {
            RoleForm
        },
        props: {
            id: String
        },
        methods: {
            ...mapActions([
                'fetchRole'
            ])

        }
    }
</script>


