<template>
    <div>
        <div v-for="commission in commissions">
            <percent-input-form-group
                    :object="commission"
                    name="commission"
                    :text="$t('suretyTypes.'+commission.suretyType)"
                    :rules="'required'">
            </percent-input-form-group>
        </div>
    </div>

</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
    import percentInputFormGroup from '../../assets/form/FormGroups/PercentInputformGroup.vue'
    import suretyTypes from '../../constants/suretyTypes'

    export default {
        props: {
            commissions: {
                type: Array
            }
        },
        components: {
            percentInputFormGroup
        }
    }
</script>