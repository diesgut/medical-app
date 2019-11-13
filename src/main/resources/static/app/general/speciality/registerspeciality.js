new Vue({
    el: '#specialityMain',
    data: {
    	speciality:{}
    },
    created(){
    	if(jSpeciality!=null){
    		this.speciality=JSON.parse(jSpeciality);
    	}
    },
    mounted() {

    },
    methods: {
        save() {
        	console.log("save");
        	if(!$("#frmSpeciality").parsley().validate()){
        		return;
        	}
        	return;
        	let message="Registro guardado!";
        	if(this.speciality.id!=null){
        		message="Registro actualizado!";
        	}
        	fetch('/general/specialities/save', {
        		  method: 'POST', // or 'PUT'
        		  body: JSON.stringify(this.speciality),
        		  headers:{
        		    'Content-Type': 'application/json'
        		  }
        		}).then(res => res.json())
        		.catch(error =>{
        			console.dir(error);
        			$.notify(error, "error");
        		})
        		.then(response => {
        			this.speciality=response;
        			$.notify(message, "success");
        		});
        }
    }
});
