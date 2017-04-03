<template>
    <div class="row">
        <div class="form-group">
            <label v-if="label" class="col-sm-4 control-label">{{label}}</label>
            <div :class="getColumnClass">
                <slot></slot>
            </div>

        </div>
    </div>
</template>
<script>
    export default {
        props: {
            label: String
        },
        computed: {
            getColumnClass(){
                if(this.label){
                    return "col-sm-8"
                }
                else {
                    return ""
                }
            }
        }
    }
</script>
