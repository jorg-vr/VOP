<template>
    <protected-element :permission="permission">
        <div>
            <button  type="button" class="btn btn-default btn-block" data-toggle="collapse" data-target=".panel-collapse" aria-expanded="false">
                {{$t('commission.commissions')}}
                <span class="caret"></span>
            </button>
            <div class="panel-collapse collapse">
                <checkbox-input-form-group class="labelfix"
                        :object="editc" name="edit" :text="$t('common.edit')" >
                </checkbox-input-form-group>
                <delete-component class="pull-right up" :resource="permission.resource" id="" :edit="false" :back="{}" :ids="{resource:loc,resourceId:id}"></delete-component>
                <edit v-if="editc.edit" :id="id" :loc="loc" :back="setEditFalse" ></edit>
                <show v-else :id="id" :loc="loc"></show>
            </div>
        </div>
    </protected-element>
</template>
<script>
    import show from './show.vue'
    import edit from './edit.vue'
    import CheckboxInputFormGroup from '../../assets/form/FormGroups/CheckBoxInputGroup.vue'
    import ProtectedElement from '../../assets/protection/ProtectedElement.js'
    import actions from '../../constants/actions'
    import resources from '../../constants/resources'
    import deleteComponent from '../../assets/general/deleteComponent.vue'
    export default {
        data(){
            return{
                permission: {
                    resource: resources.COMMISSION,
                    actions: actions.UPDATE
                },
                edit:{
                    edit:false
                }
            }
        },
        computed:{
            editc(){
                return this.edit;
            }
        },
        props:{
            id:String,
            loc:String
        },
        components: {
            show,edit,CheckboxInputFormGroup,ProtectedElement,deleteComponent
        },
        methods:{
            setEditFalse(){
                this.edit.edit=false;
            }
        }
    }
</script>
<style>
    .up button{
        transform: translateX(-20px) translateY(-40px);
    }
    .labelfix label{
        padding-top: 13px;
    }
    .labelfix {
        padding-bottom: -10px;
        margin-bottom: -10px;
    }
</style>
