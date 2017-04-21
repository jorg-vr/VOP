
<template>
    <div :class="row">
        <div class="panel panel-default col-sm-10">
            <div class="panel-body">
                <table>
                    <tr>
                        <td v-for="key in visibleKeys">{{key}}</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</template>
<script>

    export default {
        props: {
            visibleKeys: Array

        }
    }

</script>
