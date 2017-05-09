<!--
    Component usable for letting the user select his function.
    Once a function is chosen the page will reload and the user will be redirected to the home page in order to reset state.
-->
<template>
    <!--TODO update this component to SelectInputFormGrouplect
    <form-select selectClass="picker" optionKey="roleName" :options="userFunctions"
                 @input="updateActiveFunction(accountFunction.id)"
                 v-model="accountFunction.id"></form-select>-->
    <select class="picker form-control" :value="accountFunction.id" @change="onInput($event.target.value)" >
        <option v-for="userFunction in userFunctions" :selected="accountFunction.id===userFunction.id">{{userFunction.roleName}}</option>
    </select>
</template>
<script>
    import formSelect from '../form/FormGroups/SelectInputFormGroup.vue'
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    export default {
        data(){
            return {
                accountFunction: {}
            }
        },
        created(){
            if(this.userFunctions.length === 0){
                this.fetchUserFunctions()
            }
            this.accountFunction = this.activeFunction
        },
        components: {
            formSelect
        },
        computed: {
            ...mapGetters([
                'userFunctions',
                'activeFunction'
            ]),
        },
        methods: {
            ...mapActions([
                'setActiveFunction',
                'fetchUserFunctions'
            ]),
            onInput(functionId){
                let userFunction = this.userFunctions.filter(obj => obj.id === functionId)[0]
                this.setActiveFunction(userFunction)
                //Navigate back to home once the function has changed.
                //This method is used, and not the router, as the page has to reload in order to remove unwanted state
                location.reload();
                this.$router.push({name:'homeClient'})
            }
        }
    }
</script>
