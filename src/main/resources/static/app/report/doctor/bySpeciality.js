Vue.component("multiselect", window.VueMultiselect.default);

new Vue({
    el: '#doctorBySpeciality',
    data: {
    	doctors:[],
    	speciality:{},
    	specialities:[]
    },
    created(){

    },
    mounted() {
    	  this.loadSpecialities();
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
        	if(this.speciality.id==null){
        		$.notify("Debe seleccionar una especialidad.", "error");
        		return;
        	}
        	fetch('/reports/doctor/byspeciality/'+this.speciality.id)
        	  .then(function(res) {
        	    return res.json();
        	  })
        	  .catch(error =>{
        			console.dir(error);
        			$.notify(error, "error");
        		})
        	  .then(response => {
      			this.doctors=response;
    		});
        }, searchMedicalConsultationsByPatientPdf(){
        	let vue=this;
        	if(this.speciality.id==null){
        		$.notify("Debe seleccionar una especialidad.", "error");
        		return;
        	}
       // 	window.open('/reports/doctor/byspecialitypdf/'+this.speciality.id, '_top');

            $.fileDownload('/reports/doctor/byspecialitypdf/'+this.speciality.id, {
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
        },loadSpecialities(){
        	let vue=this;
        	fetch('/general/specialities/specialities')
        	  .then(function(res) {
        	    return res.json();
        	  })
        	  .catch(error =>{
        			console.dir(error);
        			$.notify(error, "error");
        		})
        	  .then(response => {
      			this.specialities=response;
    		});
        }
    }
});
