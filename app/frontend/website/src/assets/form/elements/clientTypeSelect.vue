<template>
    <form-item label="Type">
        <div class="select-row">
            <select class="form-control select-item" :value="value" @change="updateValue($event.target.value)" :id="inputId" ref="select">
                <option value="" disabled hidden>{{$t('actions.select', {subject: 'type'}).capitalize()}}</option>
                <option  v-for="clientType in clientTypes" :selected="clientType.value === value" :value="clientType.value">
                    {{clientType.translation().capitalize()}}
                </option>
            </select>
        </div>
    </form-item>

</template>
<script>
    import formItem from './formItem.vue'
    import formSelect from './formSelect.vue'
    import clientTypes from '../../../constants/clientTypes'
    export default {
        props: {
            value: String, //Initial value
        },
        mixins: [formSelect],
        components: {
            formItem
        },
        data(){
            return {
                clientTypes: clientTypes
            }
        },
        created(){
            console.log(this.clientTypes)
        }

    }
</script>
