new Vue({
    el: '#specialitySearch',
    data: {
    	specialities:[]
    },
    mounted() {
    	this.loadSpecialities();
    },
    methods: {
        loadSpecialities(){
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
        },deleteSpec(spec) {
        	console.log("deleteSpec");
        	
        	fetch('/general/specialities/delete', {
      		  method: 'DELETE', // or 'PUT'
      		  body: JSON.stringify(spec),
      		  headers:{
      		    'Content-Type': 'application/json'
      		  }
      		}).then(res => {
      			return res.text();
      		})
      		.catch(error =>{
      			$.notify(error, "error");
      		})
      		.then(response => {
      			$.notify(response, "success");
      			this.loadSpecialities();
      		});
        }
    }
});
