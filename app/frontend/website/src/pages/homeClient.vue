
<template>
    <client-show :id=activeFunction.company ></client-show>
</template>
<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import clientShow from './client/show.vue'

    export default {
        data() {
            return {
                resource: resources.CLIENT
            }
        },
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
