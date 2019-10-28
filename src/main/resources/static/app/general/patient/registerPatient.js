new Vue({
    el: '#registerPatientMain',
    data: {
    	patient:{}
    },
    created(){
    	if(jPatient!=null){
    		this.patient=JSON.parse(jPatient);
    	}
    },
    mounted() {

    },
    methods: {
        save() {
        	console.log("save");
        	let message="Registro guardado!";
        	if(this.patient.id!=null){
        		message="Registro actualizado!";
        	}
        	fetch('/general/patients/save', {
        		  method: 'POST', // or 'PUT'
        		  body: JSON.stringify(this.patient),
        		  headers:{
        		    'Content-Type': 'application/json'
        		  }
        		}).then(res => res.json())
        		.catch(error =>{
        			console.dir(error);
        			$.notify(error, "error");
        		})
        		.then(response => {
        			this.patient=response;
        			$.notify(message, "success");
        		});
        }
    }
});
