new Vue({
    el: '#registerDoctorMain',
    data: {
    	doctor:{speciality:{}},
    	specialities:[]
    },
    created(){
    	this.loadSpecialities();
    	if(jDoctor!=null){
    		this.doctor=JSON.parse(jDoctor);
    	}
    },
    mounted() {

    },
    methods: {
        save() {
        	console.log("save");
        	if(!$("#frmDoctors").parsley().validate()){
        		return;
        	}
        	let message="Registro guardado!";
        	if(this.doctor.id!=null){
        		message="Registro actualizado!";
        	}
        	fetch('/general/doctors/save', {
        		  method: 'POST', // or 'PUT'
        		  body: JSON.stringify(this.doctor),
        		  headers:{
        		    'Content-Type': 'application/json'
        		  }
        		}).then(res => res.json())
        		.catch(error =>{
        			console.dir(error);
        			$.notify(error, "error");
        		})
        		.then(response => {
        			this.doctor=response;
        			$.notify(message, "success");
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
