<template>
    <div class="row">
        <a :href="object.show">
            <div class="panel panel-default col-sm-10">
                <div class="panel-body">
                    <table>
                        <tr>
                            <td v-for="row in object.text">{{row}}</td>
                        </tr>
                    </table>
                </div>
            </div>
        </a>
        <button type="button" class="btn btn-md btn-info">
            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
        </button>
        <button type="button" class="btn btn-md btn-danger">
            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
        </button>
    </div>
</template>

<script>

export default {
    props: {

        /* 
         * object must contain:
         *      id
         *      text: Array
         *      edit: edit link
         *      show: show link
         *      remove: delete link
        */
        object: Object
    }
}    

</script>
<style>

.btn-md {
   margin-left: 5px;
   margin-top: 8px;
}

.panel {
    margin-right: 5px;
}
a td {
    color: black;
}
a div.panel:hover {
    background-color: #eee;
}

</style>
