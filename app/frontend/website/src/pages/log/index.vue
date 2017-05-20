<template>
    <div>
        <table class="table-hover table">
            <thead>
            <tr>
                <th v-for="head in listObject.headers">
                    {{$t('log.' + head).capitalize()}}
                </th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="value in listObject.values" class="list-tr">
                <td class="clickable-td" @click="tdclick(value.id, value)">
                    {{value.showableDateTime}}
                </td>
                <td class="clickable-td" @click="tdclick(value.id, value)">
                    {{$t('log.' + value.action.toLowerCase()) | capitalize}}
                </td>
                <td class="clickable-td" @click="tdclick(value.id, value)">
                    {{$t('resource.' + value.resource.toLowerCase()) | capitalize}}
                </td>
                <td class="clickable-td" @click="tdclick(value.id, value)">
                    <span v-if="value.user!==null">{{value.user.firstName}} {{value.user.lastName}}</span>
                    <span v-else>{{$t('log.user')}} {{$t('common.removed')}}</span>
                </td>
            </tr>
            </tbody>
        </table>
        <button-back :route="{name: this.resource.rtrim('s'), params: {id: this.id}}"></button-back>
    </div>
</template>
<script>
    import {mapActions} from 'vuex'
    import buttonBack from '../../assets/buttons/buttonBack.vue'

    export default {
        data(){
            return {
                logs: [],
            }
        },
        components: {
            buttonBack
        },
        props: {
            resource: String,
            id: String
        },
        created(){
            this.fetchLogs({id: this.id, resource: this.resource}).then(logs => {
                this.logs = logs
            })
        },
        computed: {
            listObject(){
                return {
                    headers: ["showableDateTime", "action", "resource", "user"],
                    values: this.logs
                }
            }
        },
        methods: {
            ...mapActions([
                'fetchLogs'
            ]),
            tdclick: function(id, value) {
                //TODO: remove value
                this.$router.push({name: this.resource.rtrim('s') + '_log', params: {resourceId: this.id, id: id, entry: value}});
            }
        }
    }
</script>