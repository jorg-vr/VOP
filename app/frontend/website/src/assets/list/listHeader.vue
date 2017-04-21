
<template>
    <div class="row">
        <div class="panel panel-default col-sm-10">
            <div class="panel-body">
                <table>
                    <tr>
                        <th v-for="key in visibleKeys">{{$t(resource.name + '.' + key).capitalize()}}</th>
                    </tr>
                </table>
            </div>
        </div>
    </div>

</template>
<script>

    export default {
        props: {
            visibleKeys: Array,
            resource: Object
        }
    }

</script>
