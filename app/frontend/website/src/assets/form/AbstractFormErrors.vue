<template>
    <div v-if="error" class="row text-center">
        <ul class="list-group">
            <li class="list-group-item list-group-item-danger" v-for="errorEle in error.errors">
                {{$t('error.' + errorEle.message, {subject: $t('field.' + errorEle.field)}) | capitalize}}
            </li>
        </ul>
    </div>
</template>
<script>
    import {mapGetters} from 'vuex'
    export default {
        computed: {
            ...mapGetters([
                'error'
            ])
        }
    }
</script>
<style>
    .errors {
        margin-left: 50px;
    }

    .list-group-item-danger {
        color: #a94442;
        background-color: #f2dede;
    }
</style>