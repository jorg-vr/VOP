<template>
    <div>
        <div class="page-header">
            <h1> {{$t('vehicle.import') | capitalize}} {{$t('vehicle.vehicles')}} </h1>
        </div>
        <form class="import-vehicles form-horizontal col-xs-12 col-sm-11 col-md-9 col-lg-7">
            <div id="example" class="row">
                <a href="https://vopro5.ugent.be/test/app/api/fleets/1/vehicles/import/example" class="btn btn-primary">
                    {{$t('vehicle.example')}}
                </a>
            </div>
            <div class="row">
                <div class="input-group col-lg-6 col-sm-6 col-12">
                    <label class="input-group-btn">
                    <span id="browse"  class="btn btn-primary">
                        {{$t('common.browse') | capitalize}}&hellip;
                        <input type="file" style="display: none;" @change="filesChange($event.target.files)" accept=".csv">
                    </span>
                    </label>
                    <input type="text" class="form-control" :value="file ? file.name : ''" readonly>
                </div>
                <div class="fail-import">
                    <span v-show="failImport" class="alert alert-danger" role="alert">{{$t('vehicle.failImport')}}</span>
                </div>
            </div>
            <div class="row button-row">
                <button-back :route="back" buttonClass="pull-left btn btn-sm btn-default form-component-button"
                             :text="$t('common.cancel')">
                </button-back>
                <button-action @click="onClick()" buttonClass="pull-left btn btn-sm btn-primary form-component-button">
                    {{$t('vehicle.import') | capitalize }}
                </button-action>
            </div>
        </form>

    </div>
</template>
<script>
    import buttonBack from '../../assets/buttons/buttonBack.vue'
    import buttonAction from '../../assets/buttons/buttonAction.vue'
    import AbstractForm from '../../assets/form/AbstractForm.vue'
    import {mapActions} from 'vuex'

    export default {
        data() {
            return {
                failImport: false,
                file: null,
                back: {name: 'fleet', params: {id: this.fleetId}}
            }
        },
        components: {
            buttonBack, AbstractForm, buttonAction
        },
        props: {
            fleetId: String
        },
        methods: {
            filesChange(fileList){
                this.failImport = false
                if(fileList.length===1){
                    this.file = fileList[0]
                }
            },
            onClick(){
                //Source: https://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects
                let data = new FormData();
                data.append("file", this.file)
                this.importVehicles({fleetId: this.fleetId, data}).then(() => {
                    this.$router.push(this.back)
                }, () => {
                    this.failImport = true
                })
            },
            ...mapActions([
                'importVehicles',
            ])

        }
    }
</script>
<style>
    #example a {
        margin-left: 0;
        margin-bottom: 20px;
        height: 40px;
    }
    #browse {
        height: 40px;
    }
    .fail-import {
        margin-top: 20px;

    }
</style>