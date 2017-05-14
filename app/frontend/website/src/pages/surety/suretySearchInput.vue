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
                :text="$t('surety.surety')" :options="suretyTypes">
        </select-input-form-group>

        <!-- premium -->
        <text-input-form-group
                :object="object" name="premium"
                :text="object.flat==true ? $t('surety.premium'): $t('surety.minPremium')" >
        </text-input-form-group>

        <!-- premium percentage -->
        <text-input-form-group v-if="object.flat==false||object.flat==undefined"
                               :object="object" name="premiumPercentage" :text="$t('surety.premiumPercentage')">
        </text-input-form-group>

        <!-- Insurance Company -->
        <select-input-form-group
                :object="object" name="insuranceCompany" optionPropertyName="id" visibleKey="name"
                :text="$t('clientTypes.insuranceCompany')" :options="clients">
        </select-input-form-group>

        <div class="page-header">
            <button-add :resource="resource"></button-add>
            <h2>{{$t("surety.coverage") | capitalize }} </h2>
        </div>

        <div class="page-header">

            <select-input-form-group
                    :object="selectedCondition" name="id" optionPropertyName="id" visibleKey="referenceCode"
                    :text="$t('condition.condition')" :options="conditions" rules="">
            </select-input-form-group>

            <button @click='pushCondition()' type="button" class="btn pull-right btn btn-primary "> {{$t("common.add") | capitalize }} </button>
        </div>

        <!-- All special conditions of surety -->
        <list-component :resource="resource" :listObject="listObject">
        </list-component>

    </div>
</template>
<script>
    import {mapGetters, mapActions,mapMutations} from 'vuex'
    import TextInputFormGroup from '../../assets/form/FormGroups/TextInputFormGroup.vue'
    import CheckboxInputFormGroup from '../../assets/form/FormGroups/CheckBoxInputGroup.vue'
    import SelectInputFormGroup from '../../assets/form/FormGroups/SelectInputFormGroup.vue'
    import DateInputFormGroup from '../../assets/form/FormGroups/DateInputFormGroup.vue'
    import clientTypes from '../../constants/clientTypes'
    import resources from '../../constants/resources'
    import buttonAdd from '../../assets/buttons/buttonAdd.vue'
    import listComponent from "../../assets/list/listComponent.vue"
    import suretyTypes from "../../constants/suretyTypes"

    export default {
        data(){
            return{
                referenceCode: 'referenceCode',
                selectedCondition: {},
                id:'id',
                suretyTypes:suretyTypes,
                resource: resources.CONDITION,
                flatData: [{text:'true', value: true},{text:"false" , value: false}]
            }
        },
        props: {
            actions: Object,
            object: Object,
        },
        components: {
            TextInputFormGroup,CheckboxInputFormGroup,SelectInputFormGroup,DateInputFormGroup,listComponent,buttonAdd
        },
        mounted(){
            this.$parent.$emit('mounted', this.$children)
        },
        computed: {
            ...mapGetters([
                'clients',
                'fleets',
                'suretyData',
                'suretyDetail',
                'contractId',
                'sureties',
                'vehicles',
                'conditions',
                'insuranceCompanyId',
                'selectedConditions'
            ]),
            listObject() {
                var listObj = {};
                listObj.headers = ['referenceCode','title','text'];
                listObj.values = this.selectedConditions;
                return listObj;
            }
        },
        methods: {
            ...mapActions([
                'fetchSureties',
                'fetchVehicles',
                'fetchConditions',
                'fetchCondition',
                'fetchClientsBy',
            ]),
            ...mapMutations([
                'setConditions',
                'setCondition',
                'setSelectedConditions',
                'addSelectedCondition',
                'clearSelectedConditions'
            ]),
            pushCondition(){
                // fetch info for selected special condition
                this.fetchCondition({id: this.selectedCondition.id}).then(condition => {
                    // add selected condition to list
                    this.addSelectedCondition(condition)
                })
            },
        },
        created(){
            // set correct insurance company id
            this.object.insuranceCompany = this.insuranceCompanyId
            // clear previous storage for selected conditions
            this.clearSelectedConditions()
            // Fetch all special conditions
            this.fetchConditions()
            // get all insurance companies
            this.fetchClientsBy({filters: {type: clientTypes.INSURANCE_COMPANY.type}}).then(() => {
            })
            // bind specialConditions to seleced conditions
            this.object.specialConditions = this.selectedConditions
        }
    }
</script>