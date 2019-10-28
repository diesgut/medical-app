new Vue({
    el: '#formContent',
    data: {
        usuario:{userName:"diesgut",password:"diesgut"}
    },
    mounted() {
       
    },
    methods: {
        logear() {
                let $vue=this;
               $.ajax({
                url: '/login',
               dataType: "json",
                contentType: "application/json",
                            type: 'POST',
                async: true,
                data:  JSON.stringify($vue.usuario),
                success(response) {
                    if (response.success) {
                        console.dir(response);
                        location.href = '/home';
                    } else {
                        alert("usuario incorrecto");
                        console.dir(response);
                    }
                },
                error() {
                    
                }
            });
        }
    }
});
