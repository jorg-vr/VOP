
<template>
    <client-show :id=activeFunction.company ></client-show>
</template>
<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import clientShow from './client/show.vue'

    export default {
        components: {
            clientShow
        },
        computed: {
            ...mapGetters([
                'activeFunction'
            ])
        }
    }
</script>
