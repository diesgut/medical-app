new Vue({
    el: '#searchDoctor',
    data: {
    	doctors:[]
    },
    created(){
// if(jSpeciality!=null){
// this.speciality=JSON.parse(jSpeciality);
// }
    },
    mounted() {
    	this.loadDoctors();
    },
    methods: {
    	loadDoctors(){
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
        }
    }
});
