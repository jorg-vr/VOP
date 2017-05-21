<template>
    <div class="row text-center">
        <div v-if="error && isServerError" class="alert alert-danger alert-dismissible" role="danger">
            <button type="button" class="close" @click="setError(null)" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>{{error.error}}</strong> <span v-if="error.message">{{errorMessage}}</span>
        </div>
    </div>

</template>
<script>
    import {mapGetters, mapMutations} from 'vuex'
    import locale from '../../lang/locales'
    import Vue from 'vue'

    export default {
        computed: {
            ...mapGetters([
                'error'
            ]),
            errorMessage(){
                //Find a more specific translated message
                let errors = locale[Vue.config.lang].error
                for (let error in errors) {
                    if (errors.hasOwnProperty(error) && this.error.message.indexOf(error)!==-1) {
                        return errors[error]
                    }
                }
                return this.error.message
            },
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