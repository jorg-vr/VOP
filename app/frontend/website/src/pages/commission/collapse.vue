<template>
    <div>
        <button  type="button" class="btn btn-default btn-block" data-toggle="collapse" data-target=".panel-collapse" aria-expanded="false">
            {{$t('commission.commissions')}}
            <span class="caret"></span>
        </button>
        <div class="panel-collapse collapse">
            <checkbox-input-form-group
                    :object="edit" name="edit" :text="$t('common.edit')" >
            </checkbox-input-form-group>
            <show v-if="edit.edit===false" :id="id" :loc="loc"></show>
            <edit v-if="edit.edit===true" :id="id" :loc="loc"></edit>
        </div>
    </div>
</template>
<script>
    import show from './show.vue'
    import edit from './edit.vue'
    import CheckboxInputFormGroup from '../../assets/form/FormGroups/CheckBoxInputGroup.vue'
    export default {
        data(){
            return{
                edit:{
                    edit:false
                }
            }
        },
        props:{
            id:String,
            loc:String
        },
        components: {
            show,edit,CheckboxInputFormGroup
        }
    }
</script>
