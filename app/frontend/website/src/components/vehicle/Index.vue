<template>
    <div>
        <div class="page-header">
            <h1> {{message}} </h1>
        </div>
        <p>Some text in index</p>
        <router-view></router-view>
    </div>

</template>
<script>
    export default {
        data: function () {
            return {
                message: this.$i18n.t('vehicle')
            }
        },
        methods:{

        }
    }
</script>