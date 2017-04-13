<template>
    <!-- Wrap select into <p> to be able to add language flag -->
    <form-select selectClass="picker" optionKey="roleName" :options="userFunctions"
                 @input="updateActiveFunction(accountFunction.id)"
                 v-model="accountFunction.id"></form-select>
</template>
<script>
    import formSelect from '../form/elements/formSelect.vue'
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
            updateActiveFunction(functionId){
                let userFunction = this.userFunctions.filter(obj => obj.id === functionId)[0]
                this.setActiveFunction(userFunction)
            }
        }
    }
</script>
