<template>
    <div class="row">
        <div v-if="visible && error" class="alert alert-danger alert-dismissible col-lg-8 col-md-9 col-sm-11" role="danger">
            <button type="button" class="close" @click="visible=false" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>{{error.error}}:</strong> The page failed to load.
        </div>
    </div>

</template>
<script>
    import {mapGetters} from 'vuex'

    export default {
        data() {
            return {
                visible: true
            }
        },
        computed: {
            ...mapGetters([
                'error'
            ])
        }
    }
</script>
<style>

</style>