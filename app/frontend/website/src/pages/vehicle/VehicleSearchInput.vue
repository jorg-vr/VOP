<!--
All of the fields for user input for the vehicle form

@param vehicle: This object will be configured with the input of this component.
-->
<template>
    <div>
        <text-input-form-group :object="vehicle" name="licensePlate" :text="$t('vehicle.licensePlate')"></text-input-form-group>
        <text-input-form-group :object="vehicle" name="vin" :text="$t('vehicle.vin')"></text-input-form-group>
        <text-input-form-group :object="vehicle" name="brand" :text="$t('vehicle.brand')"></text-input-form-group>
        <text-input-form-group :object="vehicle" name="model" :text="$t('vehicle.model')"></text-input-form-group>
        <select-input-form-group :object="vehicle" name="type" optionPropertyName="id" visibleKey="name"
                                 :text="$t('vehicle.vehicleType')" :options="vehicleTypes">
        </select-input-form-group>
        <select-input-form-group v-if="authorizedForClients" :object="vehicle" name="company" optionPropertyName="id" visibleKey="name"
                                 :text="$t('resource.company')" :options="clients">
        </select-input-form-group>
        <select-input-form-group v-if="fleets.length>0" :object="vehicle" name="fleet" optionPropertyName="id" visibleKey="name"
                                 :text="$t('resource.fleet')" :options="fleets">
        </select-input-form-group>
        <text-input-form-group :object="vehicle" name="year" :text="$t('vehicle.year')"></text-input-form-group>
    </div>
</template>
<script>
    import TextInputFormGroup from '../../assets/form/FormGroups/TextInputFormGroup.vue'
    import SelectInputFormGroup from '../../assets/form/FormGroups/SelectInputFormGroup.vue'
    import clientTypes from '../../constants/clientTypes'
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import resources from '../../constants/resources'
    import actions from '../../constants/actions'

    export default {
        props: {
            vehicle: Object,
        },
        components: {
            TextInputFormGroup, SelectInputFormGroup
        },
        created(){
            this.clearFleets()
            this.fetchVehicleTypes()
            if(this.authorizedForClients){
                this.fetchClients()
            }
            if(this.isAuthorizedForOwnResourcesButNotAll(resources.FLEET, actions.READ_ALL)){
                this.fetchFleetsBy({filters: {company: this.activeFunction.company}})
            }
            else if(this.isAuthorizedForAllResources(resources.FLEET, actions.READ_ALL)){
                this.fetchFleets()
            }
        },
        computed: {
            ...mapGetters([
                'activeFunction',
                'isAuthorizedForAllResources',
                'isAuthorizedForOwnResourcesButNotAll',
                'fleets',
                'clients',
                'vehicleTypes'
            ]),
            authorizedForClients(){
                return this.isAuthorizedForAllResources(resources.CLIENT, actions.READ_ALL)
            }
        },
        methods: {
            ...mapActions([
                'fetchVehicleTypes',
                'fetchClients',
                'fetchFleets',
                'fetchFleetsBy'
            ]),
            ...mapMutations([
                'clearFleets'
            ])
        },

    }
</script>