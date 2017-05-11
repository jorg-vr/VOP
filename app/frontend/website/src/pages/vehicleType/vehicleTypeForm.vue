<template>
    <div>
        <text-input-form-group :object="vehicleType" name="name" :text="$t('vehicleType.vehicleType')" :rules="'required'"></text-input-form-group>
    </div>

</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
    import textInputFormGroup from '../../assets/form/FormGroups/TextInputFormGroup.vue'

    export default {
        props: {
            vehicleType: Object
        },
        components: {
            textInputFormGroup
        }
    }
</script>