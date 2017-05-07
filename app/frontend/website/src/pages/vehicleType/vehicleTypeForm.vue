<template>
    <div>
        <form-input :placeholder="$t('common.name') + ' ' + $t('vehicleType.vehicleType') | capitalize" v-model="vehicleType.name"></form-input>
    </div>

</template>
<script>
    import {mapGetters, mapActions} from 'vuex'
    import formInput from '../../assets/form/elements/formInput.vue'

    export default {
        props: {
            vehicleType: Object
        },
        components: {
            formInput
        }
    }
</script>