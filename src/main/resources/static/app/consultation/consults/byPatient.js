Vue.component("multiselect", window.VueMultiselect.default);

new Vue({
    el: '#consultByPatient',
    data: {
    	medicalConsultations:[],
    	patient:{},
    	patients:[]
    },
    created(){

    },
    mounted() {
    	  this.loadPatients();
    },
    methods: {
    	loadPatients(){
        	let vue=this;
        	fetch('/general/patients/patients')
        	  .then(function(res) {
        	    return res.json();
        	  })
        	  .catch(error =>{
        			console.dir(error);
        			$.notify(error, "error");
        		})
        	  .then(response => {
      			this.patients=response;
    		});
        }, searchMedicalConsultationsByPatient(){
        	let vue=this;
        	if(this.patient.id==null){
        		$.notify("Debe seleccionar un paciente.", "error");
        		return;
        	}
        	fetch('/consultation/medicalconsultations/medicalConsultationByPatient/'+this.patient.id)
        	  .then(function(res) {
        	    return res.json();
        	  })
        	  .catch(error =>{
        			console.dir(error);
        			$.notify(error, "error");
        		})
        	  .then(response => {
      			this.medicalConsultations=response;
    		});
        }
    }
});
