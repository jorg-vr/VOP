<template>
    <protected-element :permission="permission">
        <div>
            <button  type="button" class="btn btn-default btn-block" data-toggle="collapse" data-target=".panel-collapse" aria-expanded="false">
                {{$t('commission.commissions')}}
                <span class="caret"></span>
            </button>
            <div class="panel-collapse collapse">
                <checkbox-input-form-group
                        :object="editc" name="edit" :text="$t('common.edit')" >
                </checkbox-input-form-group>
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
            show,edit,CheckboxInputFormGroup,ProtectedElement
        },
        methods:{
            setEditFalse(){
                console.log("setEditFalse");
                this.edit.edit=false;
            }
        }
    }
</script>
