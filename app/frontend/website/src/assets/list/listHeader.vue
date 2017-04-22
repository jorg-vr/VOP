
<template>
        <div class="col-md-10 col-xs-9 list-group-item list-header">
            <table>
                <tr>
                    <th v-for="key in visibleKeys"> {{$t(resource.name + '.' + key).capitalize()}}</th>
                </tr>
            </table>
        </div>
</template>
<style>
.list-header {
    border-width: 0;
}
</style>
<script>

    export default {
        props: {
            visibleKeys: Array,
            resource: Object
        }
    }

</script>
