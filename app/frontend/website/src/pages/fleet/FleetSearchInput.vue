<!--
All of the fields for user input for the fleet form

@param fleet: This object will be configured with the input of this component.
-->
<template>
    <div v-if="fleet">
        <text-input-form-group :object="fleet" name="name" :text="$t('fleet.name')"></text-input-form-group >
        <select-input-form-group v-if="showCompanySelect"
                                 :object="fleet" name="company" optionPropertyName="id" visibleKey="name"
                                 :text="$t('fleet.company')" :options="clients">
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
        data() {
            return {
                showCompanySelect: false
            }
        },
        created(){
            if(this.isAuthorizedForAllResources(resources.FLEET, actions.READ_ALL)){
                this.fetchClients()
                this.showCompanySelect = true
            }
        },
        props: {
            fleet: Object,
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
                'fetchClients'
            ])
        }
    }
</script>
