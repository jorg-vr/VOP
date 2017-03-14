<template>
    <div class="row">
        <router-link :to="{name: this.show, params: {id: objectId}}">
            <div class="panel panel-default col-sm-10">
                <div class="panel-body">
                    <table>
                        <tr>
                            <td v-for="text in textValues">{{text}}</td>
                        </tr>
                    </table>
                </div>
            </div>
        </router-link>
        <router-link :to="{name: this.edit, params: {id: objectId}}">
            <button type="button" class="btn btn-md btn-info">
                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
            </button>
        </router-link>
        <button type="button" class="btn btn-md btn-danger" v-on:click="this.remove">
            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
        </button>
    </div>
</template>

<script>

export default {
    props: {
        textValues: Array, //Array with text to show
        objectId: Number,
        show: String, //Name of link to show page of the given object.
        edit: String, //Name of link to edit page of the given object.
        remove: Function, //Function to remove the given object.
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

.panel table  {
    /* Create columns with equal width */
    table-layout: fixed;
}
a td {
    color: black;
    /* Trigger columns with equal width */
    width: 2%;
}
a div.panel:hover {
    background-color: #eee;
}

</style>
