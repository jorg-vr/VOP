<template>
    <div>
        <div class="page-header">
            <h1>Vloten </h1>
        </div>
        <div class="row">
            <info-pane></info-pane>
            <info-pane></info-pane>
        </div>
        <button type="button" class="btn btn-circle btn-lg"><span class="glyphicon glyphicon-plus"></span></button>
    </div>
</template>

<script>
export default {
    components: {
        infoPane: {
            template: `
                <div class="panel panel-default">
                    <div class="panel-body">
                        {{ fleets }}
                    </div>
                </div>
                `
        }
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
  width: 50px;
  height: 50px;
  padding: 10px 16px;
  font-size: 18px;
  line-height: 1.33;
  border-radius: 25px;
}
</style>
