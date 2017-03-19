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
                return this.$http.post(this.base_url + url, object, this.headers)
            },
            put(url, object){
                return this.$http.put(this.base_url + url, object, this.headers)
            },
            delete(url, object){
                return this.$http.delete(this.base_url + url, this.headers)
            }
        }
    }
</script>

