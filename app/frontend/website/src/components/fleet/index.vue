<template>
    <div>
        <div class="page-header">
            <h1>Vloten </h1>
        </div>
            <info-pane v-for="fleet in fleets" :object="fleet" :key="fleet.id"></info-pane>
        <button type="button" class="btn btn-primary btn-circle btn-lg">+</button>
    </div>
</template>

<script>
import infoPane from "../listComponent.vue"
export default {
    components: {
        'info-pane' : infoPane
    },   
    data: function() {
        return {
            fleets : [ //dummy fleets
            {id: 1, text: ['Test company 1'], edit: 'id/edit', show: 'fleets/1'},
            {id: 2, text: ['Test company 2'], edit: 'id/edit'},
            {id: 3, text: ['Test company 3'], edit: 'id/edit'},
            {id: 4, text: ['Test company 4'], edit: 'id/edit'}
            ]
        }
    }
}

</script>
<style>
.btn-circle.btn-lg {
  position: fixed;
  left: 230px;
  bottom: 40px;
  width: 50px;
  height: 50px;
  padding: 10px 16px;
  font-size: 18px;
  line-height: 1.33;
  border-radius: 25px;
}
</style>
