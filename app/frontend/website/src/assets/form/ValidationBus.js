import Vue from 'vue';
export const ValidationBus = new Vue({
    data: {
        validatedCount: 0,
        toValidateCount: 0,
        validationFunction: null
    },
    methods: {
        incrementValidationCount(){
            this.validatedCount++
            if(this.validatedCount===this.toValidateCount){
                this.validationFunction();
            }
        },
        setToValidateCount(count){
            this.toValidateCount=count
        },
        clearValidatedCount(){
            this.validatedCount=0
        }
    }
});