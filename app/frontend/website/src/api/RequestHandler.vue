<script>
    export default {
        data(){
            return {
                base_url: 'https://vopro5.ugent.be/app/api/',
                headers: {
                    Accept: "application/json",
                }
            }
        },
        methods : {
            get(url) {
                return this.$http.get(this.base_url + url, this.headers)
            },
            post(url, object){
                this.$http.post(this.base_url + url, object, this.headers).then(response => {
                    return response;
                })
            },
            put(url, object){
                this.$http.put(this.base_url + url, object, this.headers).then(response => {
                    return response;
                })
            },
            delete(url, object){
                this.$http.delete(this.base_url + url, object, this.headers).then(response => {
                    return response;
                })
            }
        }
    }
</script>

