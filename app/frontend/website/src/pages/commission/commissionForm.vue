<template>
    <div>
        <div  v-for="commission in commissions">
            <text-input-form-group

                    :object="commission"
                    name="commission"
                    :text="$t('suretyTypes.'+commission.suretyType)"
                    :rules="'required'"
            ></text-input-form-group>
        </div>
    </div>

</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
    import textInputFormGroup from '../../assets/form/FormGroups/TextInputFormGroup.vue'

    export default {
        data(){
            return {
                commission: {}
            }
        },
        props: {
            commissions: Array
        },
        components: {
            textInputFormGroup
        }
    }
</script>