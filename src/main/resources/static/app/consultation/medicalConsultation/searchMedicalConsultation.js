new Vue({
    el: '#searchMedicalConsultation',
    data: {
    	medicalConsultations:[]
    },
    created(){

    },
    mounted() {
    	 this.loadMedicalConsultations();
    },
    methods: {
    	loadMedicalConsultations(){
        	
        	fetch('/consultation/medicalconsultations/medicalConsults')
        	  .then(function(res) {
        	    return res.json();
        	  })
        	  .catch(error =>{
        			$.notify(error, "error");
        		})
        	  .then(response => {
        		  console.dir(response);
      			this.medicalConsultations=response;
    		});
        }
    }
});
