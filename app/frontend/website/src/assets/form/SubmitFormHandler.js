import Vue from 'vue';
export const SubmitFormHandler = new Vue({
    data: {
        inputComponents: [],
        submitFunction: null
    },
    methods: {
        /**
         * Set the list of inputComponents which each have their own validator.
         * @param components
         */
        setInputComponents(components){
            this.inputComponents = components
        },
        /**
         * Function for submitting the form
         * @param submitFunction
         */
        setSubmitFunction(submitFunction){
            this.submitFunction = submitFunction
        },
        /**
         * Validate the form, and if the validations succeed, then submit the form.
         */
        submit(){
            //Create a list of promises. Only if all validations (these are promises) succeed,
            //then the form can be submitted.
            let promises = []
            for(let i=0; i<this.inputComponents.length; i++){
                let validator = this.inputComponents[i].$validator
                promises.push(validator.validateAll())
            }
            Promise.all(promises).then(() => {
                this.submitFunction()
            })
        }
    }
});