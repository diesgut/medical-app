Vue.component("multiselect", window.VueMultiselect.default);

new Vue({
    el: '#clinicalHistory',
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
        }, formatDate(date){
        	 return moment(date).format('YYYY-MM-DD HH:mm:ss');// THH:mm:ss
        }, searchMedicalConsultationsByPatientPdf(){
        	let vue=this;
        	if(this.patient.id==null){
        		$.notify("Debe seleccionar un paciente.", "error");
        		return;
        	}

            $.fileDownload('/reports/patient/clinicalhistory/'+this.patient.id, {
                httpMethod: "GET",
                successCallback: function (responseHtml, url) {
// console.log('aqui');
                },
                onFail: function (e) {
                    console.log(e);
                },
                failCallback: function (responseHtml, url) {
                    $.notify("Error al descargar el reporte.", "error");
                }
            });
        }
    }
});
