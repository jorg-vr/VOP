<template>
    <div>
        <div class="page-header">
            <h1> Gebruiker </h1>
        </div>


        <info-pane v-for="users in userList"
                   :textValues="new Array(users.name)"
                   :remove="remove"
                   :objectId= "users.id"
                   edit="edit_user"
                   show="user"
                   :key="users.id">
        </info-pane>

        <button type="button" class="btn btn-primary btn-circle btn-lg" v-on:click="add()">+</button>

    </div>



</template>
<script>
    import infoPane from "../../assets/listComponent.vue"
    export default {
        components: {
            'info-pane': infoPane
        },
        data(){
            return {
                userList : [
                    {id: "1", name: 'User1'},
                    {id: "2", name: 'User2'},
                    {id: "3", name: 'User3'},
                    {id: "4", name: 'User4'}
                ],
                users:{},
                search: ''
            }
        },
        methods:{
            // Methods for routing purposes
            add: function () {
                this.$router.push({ path: 'users/new'})
            },
            remove : function (id){
                confirm("Wil u doorgaan met het verwijderen van?"+id)

                // API CALL VOOR VERWIJDEREN
                console.log('https://vopro5.ugent.be/app/api/users/'+id)
                /*                        this.$http.delete('https://vopro5.ugent.be/app/api/users/'+id).then(response => {

                 })
                 */

                // remove user in table
                // var i = this.users.indexOf(user);
                // this.users.splice(i, 1)


            },
        },
        computed: {
            searchId: function () {
                var s=this.search.trim().toLowerCase();;
                var listID = []
                listID = this.users
                if(!this.search){
                    return listID
                }
                else{
                    console.log(listID)
                    return listID.filter(function(number){
                        if(number.name.toLowerCase().indexOf(s) != -1){
                            return number
                        }
                    })
                }
            },
            appendString : function (){
                var value = 'Gebruiker zoeken'
                return value
            },
        },
        mounted: function () {
            // Make ajax request to server according to type
            this.$http.get('https://vopro5.ugent.be/app/api/users').then(response => {
                const data = response.body.data;
                for(let i=0; i<data.length; i++){
                    this.userList.push(data[i]);
                }
            })
        },
    }
</script>

<style>
    .btn-circle.btn-lg {
        position: fixed;
        left: 230px;
        bottom: 40px;
        width: 50px;
        height: 50px;
        padding: 10px 16px;
        font-size: 18px;
        line-height: 1.33;
        border-radius: 25px;
    }
</style>