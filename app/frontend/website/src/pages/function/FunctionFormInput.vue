<!--
All of the fields for user input for the fleet form

@param fleet: This object will be configured with the input of this component.
-->
<template>
    <div v-if="object">
        <select-input-form-group
                                 :object="object" name="role" optionPropertyName="id" visibleKey="name"
                                 :text="$t('function.roleName')" :rules="'required'" :options="roles">
        </select-input-form-group>
        <select-input-form-group
                :object="object" name="company" optionPropertyName="id" visibleKey="name"
                :text="$t('function.companyName')" :rules="'required'" :options="clients">
        </select-input-form-group>
    </div>
</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
    import resources from '../../constants/resources'
    import actions from '../../constants/actions'
    import TextInputFormGroup from '../../assets/form/FormGroups/TextInputFormGroup.vue'
    import SelectInputFormGroup from '../../assets/form/FormGroups/SelectInputFormGroup.vue'

    export default {
        props: {
            object: Object,
        },
        components: {
            TextInputFormGroup, SelectInputFormGroup
        },
        mounted(){
            this.$parent.$emit('mounted', this.$children)
        },
        created(){
              this.fetchRoles();
              this.fetchClients();
        },
        computed: {
            ...mapGetters([
                'roles',
                'clients'
            ])
        },
        methods: {
            ...mapActions([
                'fetchRoles',
                'fetchClients'
            ])
        }
    }
</script>
