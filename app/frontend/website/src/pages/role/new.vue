<template>
    <role-form :role="role" :actions="actions" :back="back"></role-form>
</template>
<script>
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import RoleForm from './RoleForm.vue'

    export default {
        data(){
            return {
                actions: actions.CREATE,
                role: {},
                back:{name:resources.ROLE.name.plural()}
            }
        },
        components: {
            RoleForm
        }
    }
</script>


