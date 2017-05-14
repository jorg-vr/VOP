<template>
    <div>
        <div v-for="commission in commissions">
            <text-input-form-group
                    :object="commission"
                    name="commission"
                    :text="$t('suretyTypes.'+commission.suretyType)"
                    :rules="'required'">
            </text-input-form-group>
        </div>
    </div>

</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
    import textInputFormGroup from '../../assets/form/FormGroups/TextInputFormGroup.vue'
    import suretyTypes from '../../constants/suretyTypes'

    export default {
        props: {
            commissions: {
                type: Array
            }
        },
        components: {
            textInputFormGroup
        }
    }
</script>