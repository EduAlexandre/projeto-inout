$(document).ready(function () { 

    jQuery.validator.addMethod("cpf", function(value, element) {
        value = jQuery.trim(value);

        value = value.replace('.','');
        value = value.replace('.','');
        cpf = value.replace('-','');
        while(cpf.length < 11) cpf = "0"+ cpf;
        var expReg = /^0+$|^1+$|^2+$|^3+$|^4+$|^5+$|^6+$|^7+$|^8+$|^9+$/;
        var a = [];
        var b = new Number;
        var c = 11;
        for (i=0; i<11; i++){
            a[i] = cpf.charAt(i);
            if (i < 9) b += (a[i] * --c);
        }
        if ((x = b % 11) < 2) { a[9] = 0 } else { a[9] = 11-x }
        b = 0;
        c = 11;
        for (y=0; y<10; y++) b += (a[y] * c--);
        if ((x = b % 11) < 2) { a[10] = 0; } else { a[10] = 11-x; }

        var retorno = true;
        if ((cpf.charAt(9) != a[9]) || (cpf.charAt(10) != a[10]) || cpf.match(expReg)) retorno = false;

        return this.optional(element) || retorno;

    }, "Informe um CPF válido");

    //FORM REGISTRO                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
    $("#formCheck").validate({
	    rules:{
	    	nome:{
	    		required: true
	    	},
	        cpf:{
	            required: true,
	            cpf: true,
	            minlength: 11,
	            checkFlag: true,
	            remote:{
	                url: '/verificationCPF',
	                type: "post",
	                data:{
	                    cpf: function()
	                    {
	                        return $('#cpf').val();
	                    }
	                }
	            }
	        },
	        submitHandler: function(form){
	            form.submit()
	        }
	        
	    },
	    messages:{
	    	nome:{
	    		require: "O campo é obrigatório."
	    	},
	        cpf:{
	            required: "O campo cpf é obrigatório.",
	            remote: "Acesso negado!",
	            checkFlag: "Bem vindo e aproveite o dia",
	            cpf: "CPF invalido",
	            
	        }	
	    }
            
    });
   
    jQuery.validator.addMethod("checkFlag", function(value, element) {
		   value = jQuery.trim(value);
		   var teste = true;
		   $.ajax({
                type:'GET',
                url:'/verificationFlag',
                async: false, 
                dataType: "json",
                data:{
             	   cpf: function()
	                      {
	                          return $('#cpf').val();
	                      }
                }
		   }).done(function(retorno) {
	            if(retorno == true){
	            	teste = true;
	            }else{
	            	teste = false;
	            }
	            
	       })
		   return teste;
		   

	}), "oiioio";


 })