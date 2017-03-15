<template>
    <div>
        <div class="page-header">
        <h1>Vloot {{id}}</h1>
        </div>
    </div>
</template>

<script>
import tableComponent from "../../assets/tableComponent.vue"
export default {
    props: {
        id: Number
    },
    components: {
        'table-component': tableComponent
    }
    //data: function() {
        //return {
        //}
    //}
}
</script>
