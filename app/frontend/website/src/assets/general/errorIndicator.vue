<template>
    <div class="row text-center">
        <div v-if="error && isServerError" class="alert alert-danger alert-dismissible col-sm-6" role="danger">
            <button type="button" class="close" @click="setError(null)" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>{{error.error}}</strong> <span v-if="error.message">{{error.message}}</span>
        </div>
    </div>

</template>
<script>
    import {mapGetters, mapMutations} from 'vuex'

    export default {
        computed: {
            ...mapGetters([
                'error'
            ]),
            isServerError(){
                return this.error.status !== undefined && this.error.status.toString().startsWith('5')
            }
        },
        methods: {
            ...mapMutations([
                'setError'
            ])
        }
    }
</script>
<style>

</style>