<!--
All of the fields for user input for the fleet form

@param fleet: This object will be configured with the input of this component.
-->
<template>
    <div v-if="object">
        <text-input-form-group :object="object" name="name" :text="$t('fleet.name')" :rules="'required'"></text-input-form-group >
        <select-input-form-group v-if="showCompanySelect"
                     :object="object" name="company" optionPropertyName="id" visibleKey="name"
                     :text="$t('fleet.company')" :rules="'required'" :options="clients">
        </select-input-form-group>
    </div>

</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
    import resources from '../../constants/resources'
    import actions from '../../constants/actions'
    import TextInputFormGroup from '../../assets/form/FormGroups/TextInputFormGroup.vue'
    import SelectInputFormGroup from '../../assets/form/FormGroups/SelectInputFormGroup.vue'
    import clientTypes from '../../constants/clientTypes'

    export default {
        data() {
            return {
                showCompanySelect: false
            }

        },
        created(){
            if(this.isAuthorizedForAllResources(resources.FLEET, actions.READ_ALL)){
                this.fetchClientsBy({filters: {type: clientTypes.CUSTOMER.type}})
                if(this.object && !this.object.company){
                    this.showCompanySelect = true
                }
            }
        },
        mounted(){
            this.$parent.$emit('mounted', this.$children)
        },
        props: {
            object: Object,
        },
        components: {
            TextInputFormGroup, SelectInputFormGroup
        },
        computed: {
            ...mapGetters([
                'clients',
                'isAuthorizedForAllResources'
            ])
        },
        methods: {
            ...mapActions([
                'fetchClientsBy'
            ])
        }
    }
</script>
