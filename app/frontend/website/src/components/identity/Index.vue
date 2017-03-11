<template>
    <div>
        <div class="page-header">
            <h1> {{this.$route.query.type }} en {{ this.$route.fullPath }}</h1>
        </div>

        <div class="row">
            <div class="col-sm-3">
                <button type="button" class="btn btn-primary btn-md" v-on:click="add"> <i class="fa fa-plus fa-2x" aria-hidden="true"></i></i> </button>

            </div>
            <div class="col-sm-3">
                <button type="button" class="btn btn-primary btn-md" v-on:click="edit"> <i class="fa fa-pencil fa-2x" aria-hidden="true"></i></button>

            </div>
            <div class="col-sm-3">
                <button type="button" class="btn btn-primary btn-md" v-on:click="remove"> <i class="fa fa-trash fa-2x" aria-hidden="true"></i></button>

            </div>
            <div class="col-sm-3">
                <button type="button" class="btn btn-primary btn-md" v-on:click="list"> <i class="fa fa-list-ul fa-2x" aria-hidden="true"></i></button>

            </div>
        </div>
    </div>
</template>
<script>
    export default {
        data(){
            return {
                identityType: this.$route.query.type
            }
        },
        methods: {
            // Methods for routing purposes
            add: function () {
                this.$router.push({path: 'identities/new', query: {type: this.$route.query.type}})
            },
            edit: function () {
                this.$router.push({path: 'identities/:id/edit', query: {type: this.$route.query.type}})
            },
            remove: function () {
                this.$router.push({path: 'identities/:id/remove', query: {type: this.$route.query.type}})
            },
            list: function () {
                this.$router.push({path: 'identities/:id', query: {type: this.$route.query.type}})
            }
        }
    }
</script>