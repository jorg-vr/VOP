<!--
All of the fields for insurance input for the insurance form

@param insurance: This object will be configured with the input of this component.
-->
<template>
    <div>
        <checkbox-input-form-group
                :object="object" name="flat" :text="$t('surety.flat')" >
        </checkbox-input-form-group>
        <!--surety type-->
        <select-input-form-group
                :object="object" name="suretyType" optionPropertyName="name" visibleKey="translation"
                :text="$t('surety.surety')" :rules="'required'" :options="suretyTypes">
        </select-input-form-group>
        <!-- premium -->

        <euro-input-form-group
                :object="object" name="premium"
                :rules="'required'"
                :text="object.flat==true ? $t('surety.premium'): $t('surety.minPremium')" >
        </euro-input-form-group>

        <!-- premium percentage -->

        <percent-input-form-group v-if="object.flat==false||object.flat==undefined"
                                  :object="object" name="premiumPercentage" :text="$t('surety.premiumPercentage')" :rules="'required'">
        </percent-input-form-group>

        <!-- Insurance Company -->
        <select-input-form-group
                :object="object" name="insuranceCompany" optionPropertyName="id" visibleKey="name"
                :text="$t('clientTypes.insuranceCompany')" :rules="'required'" :options="clients">
        </select-input-form-group>

        <div class="page-header">
            <h2>{{$t("surety.coverage") | capitalize }} </h2>
        </div>

        <div class="page-header">
            <select-input-form-group
                    :object="selectedCondition" name="id" optionPropertyName="id" visibleKey="referenceCode"
                    :text="$t('condition.condition')" :options="conditions" rules="">
            </select-input-form-group>

            <button @click='pushCondition()' type="button" class="btn pull-right btn btn-primary"> {{$t("common.add") | capitalize }} </button>
        </div>

        <!-- All special conditions of surety -->
        <list-component :resource="resource" :listObject="listObject" :show="false" :edit="false"></list-component>

    </div>
</template>
<script>
    import {mapGetters, mapActions,mapMutations} from 'vuex'
    import percentInputFormGroup from '../../assets/form/FormGroups/PercentInputformGroup.vue'
    import EuroInputFormGroup from '../../assets/form/FormGroups/EuroInputFormGroup.vue'
    import CheckboxInputFormGroup from '../../assets/form/FormGroups/CheckBoxInputGroup.vue'
    import SelectInputFormGroup from '../../assets/form/FormGroups/SelectInputFormGroup.vue'
    import DateInputFormGroup from '../../assets/form/FormGroups/DateInputFormGroup.vue'
    import clientTypes from '../../constants/clientTypes'
    import resources from '../../constants/resources'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import listComponent from "../../assets/general/listComponent.vue"
    import suretyTypes from "../../constants/suretyTypes"
    import buttonEdit from '../../assets/buttons/buttonEdit.vue'
    import buttonRemove from '../../assets/buttons/buttonRemove.vue'
    import confirmModal from '../../assets/general/modal.vue'

    export default {
        data(){
            return{
                selectedCondition: {},
                suretyTypes:suretyTypes,
                resource: resources.CONDITION,
                flatData: [{text:'true', value: true},{text:"false" , value: false}],
                showModal: false
            }
        },
        props: {
            actions: Object,
            object: Object,
        },
        components: {
            percentInputFormGroup,CheckboxInputFormGroup,SelectInputFormGroup,DateInputFormGroup,listComponent,buttonAdd,EuroInputFormGroup,buttonRemove,buttonEdit,confirmModal
        },
        mounted(){
            this.$parent.$emit('mounted', this.$children)
        },
        computed: {
            ...mapGetters([
                'clients',
                'conditions',
                'selectedConditions'
            ]),
            listObject() {
                let listObj = {};
                listObj.headers = ['referenceCode','title','text'];
                listObj.values = this.selectedConditions;
                return listObj;
            }
        },
        methods: {
            ...mapActions([
                'fetchConditions',
                'fetchCondition',
                'fetchClientsBy',
            ]),
            ...mapMutations([
                'setConditions',
                'setCondition',
                'addSelectedCondition',
                'clearSelectedConditions'
            ]),
            pushCondition(){
                // fetch info for selected special condition
                this.fetchCondition({id: this.selectedCondition.id}).then(condition => {
                    // add selected condition to list
                    this.addSelectedCondition(condition)
                    this.object.specialConditions = this.selectedConditions
                })
            },
            tdclick: function(value) {
                this.$router.push({name: this.resource.name, params: {id:value.id}});
            },
            tdshowModal: function(id) {
                this.showModal = true
                this.selectedvalue=id
            },
            confirmAction: function(){
                // hide modal
                this.showModal=false
                this.object.specialConditions = this.removeSpecialCondition(this.selectedvalue,this.object.specialConditions)
            },
            removeSpecialCondition : function(id,arr){
                for(let i=0; i<arr.length; i++){
                    if(arr[i].id === id){
                        let newArr = arr.filter(obj => obj.id !== id)
                        this.selectedConditions.splice(i, 1);
                        return newArr
                    }
                }
            }
        },
        created(){
            // set correct insurance company id
            this.object.insuranceCompany = this.insuranceCompanyId
            // clear previous storage for selected conditions
            this.clearSelectedConditions()
            // Fetch all special conditions
            this.fetchConditions()
            // get all insurance companies
            this.fetchClientsBy({filters: {type: clientTypes.INSURANCE_COMPANY.type}})
            // bind specialConditions to seleced conditions
            this.object.specialConditions = this.selectedConditions
        }
    }
</script>