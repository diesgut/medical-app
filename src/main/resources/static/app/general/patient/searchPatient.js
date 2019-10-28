new Vue({
    el: '#searchPatient',
    data: {
    	patients:[]
    },
    created(){
// if(jSpeciality!=null){
// this.speciality=JSON.parse(jSpeciality);
// }
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
        }
    }
});
