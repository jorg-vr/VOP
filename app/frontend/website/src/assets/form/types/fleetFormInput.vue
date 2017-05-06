<!--
All of the fields for user input for the fleet form

@param fleet: This object will be configured with the input of this component.
-->
<template>
    <div v-if="object">
        <form-input :object="object" name="name" :text="$t('fleet.name')" :rules="'required'"></form-input>
        <form-select v-if="showCompanySelect"
                     :object="object" name="company" optionPropertyName="id" visibleKey="name"
                     :text="$t('fleet.company')" :rules="'required'" :options="clients">
        </form-select>
    </div>

</template>
<script>
    import resources from '../../../constants/resources'
    import actions from '../../../constants/actions'
    import {mapGetters, mapActions} from 'vuex'
    import formInput from '../elements/formInput.vue'
    import formSelect from '../elements/formSelect.vue'
    export default {
        data() {
            return {
                showCompanySelect: false
            }

        },
        created(){
            if(this.isAuthorizedForAllResources(resources.FLEET, actions.READ_ALL)){
                this.fetchClients()
                if(!this.object.company){
                    this.showCompanySelect = true
                }
            }
        },
        props: {
            object: Object,
        },
        components: {
            formInput, formSelect
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
