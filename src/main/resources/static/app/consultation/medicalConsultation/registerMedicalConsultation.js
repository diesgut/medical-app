new Vue({
    el: '#registerMedicalConsultationMain',
    data: {
    	medicalConsultation:{doctor:{},patient:{}},
    	doctors:[],
    	patients:[],    	
    	detailConsultation:{},
    	detailConsultations:[]
    },
    created(){
		 if(jMedicalConsultation!=null){
		 this.medicalConsultation=JSON.parse(jMedicalConsultation);
		 this.detailConsultations=this.medicalConsultation.detailConsultations;
		 }
		this.loadDoctors();
    	this.loadPatients();
    },
    mounted() {

    },
    methods: {
        save() {
        	console.log("save");
        	if(!$("#frmConsult").parsley().validate()){
        		return;
        	}
        	let message="Registro guardado!";
        	if(this.medicalConsultation.id!=null){
        		message="Registro actualizado!";
        	}
        	this.medicalConsultation.detailConsultation=this.detailConsultations;
        	fetch('/consultation/medicalconsultations/save', {
        		  method: 'POST', // or 'PUT'
        		  body: JSON.stringify(this.medicalConsultation),
        		  headers:{
        		    'Content-Type': 'application/json'
        		  }
        		}).then(res => res.json())
        		.catch(error =>{
        			console.dir(error);
        			$.notify(error, "error");
        		})
        		.then(response => {
        			this.medicalConsultation=response;
        			$.notify(message, "success");
        		});
        }, loadDoctors(){
        	let vue=this;
        	fetch('/general/doctors/doctors')
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
        }, loadPatients(){
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
        }, addDetail(){
        	if(!$("#frmDetail").parsley().validate()){
        		return;
        	}
        	if(this.detailConsultation.id==null){
        		this.detailConsultations.push({ ...this.detailConsultation });
        	}else{
        		let index=this.detailConsultations.findIndex(x => x.id === this.detailConsultation.id);
        		this.detailConsultations[index]={ ...this.detailConsultation };
        	}
        	this.detailConsultation={};
        	$('#exampleModal').modal('hide');
        	$.notify("Detalle agregado!", "success");
        }, modifyDetail(detail){
        	this.detailConsultation={ ...detail };
        	$('#exampleModal').modal('show');
        }, deleteDetail(detail){
        	let index=this.detailConsultations.indexOf(detail);
        	 this.detailConsultations.splice(index,1);
        }
    }
});
