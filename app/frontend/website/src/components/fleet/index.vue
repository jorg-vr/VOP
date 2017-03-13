<template>
    <div>
        <div class="page-header">
            <h1>Vloten </h1>
        </div>
        <div class="row">
            <info-pane v-for="fleet in fleets" :fleet="fleet" :key="fleet.id"></info-pane>
        </div>
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
            {id: 1, company : 'Test company 1'},
            {id: 2, company : 'Test company 2'},
            {id: 3, company : 'Test company 3'},
            {id: 4, company : 'Test company 4'}
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
