<template>
    <abstract-form :actions="actions" :object="role" :back="back" :resource="resource">
        <form-input :role="role" :id="id" ></form-input>
    </abstract-form>
</template>
<script>
    import {mapActions} from 'vuex'
    import abstractForm from '../../assets/form/AbstractForm.vue'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import formInput from './RoleFormInput.vue'

    export default {
        data(){
            return {
                resource: resources.ROLE,
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
            abstractForm,formInput
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


