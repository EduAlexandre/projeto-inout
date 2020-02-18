$(document).ready(function () { 
	$("#cpf").bind('paste', function(e) {
        e.preventDefault();
    });
    //FORM REGISTRO                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
    $("#formCheck").validate({
    	onkeyup: false,
	    rules:{
	    	nome:{
	    		required: true
	    	},
	        cpf:{
	            required: true,
	            cpf: true,
	            minlength: 11,
	            remote:{
	                url: '/verificationCPF',
	                type: "post",
	                data:{
	                    cpf: function()
	                    {
	                        return $('#cpf').val();
	                    }
	                }
	            },
	    		checkFlag: true
	        },
	        submitHandler: function(form){
	            form.submit()
	        }
	        
	    },
	    messages:{
	    	nome:{
	    		required: '<font color="red" class="msError">Este campo é obrigatório.</font>'
	    	},
	        cpf:{
	            required: '<font color="red" class="msError">O campo cpf é obrigatório.</font>',
	            remote: '<font color="red" class="msError">Acesso negado!</font>',
	            checkFlag:'<font color="red" class="msError">Bem vindo e aproveite o dia</font>',
	            cpf: '<font color="red" class="msError">CPF invalido</font>',
	            
	        }	
	    },
	    errorElement: 'span',
	    errorPlacement: function(error, element) {
	        if(element.attr('name') == 'cpf'){
	        	
	        	if(error[0].innerText == "Bem vindo e aproveite o dia"){
	        		$.ajax({
	                    type:'get',
	                    url:'/getName',
	                    async: false, 
	                    dataType: "json",
	                    data:{
	                 	   cpf: function()
	    	                      {
	    	                          return $('#cpf').val();
	    	                      }
	                    },

	                    success : function(resultado) {
	                        var value = "Bem vindo "+resultado.nome;
	                        
	                        toastr.success(value);
	                        $('#cpf').val("")
	                    }
	    		   })
	    		   
	        		
	        		//ta aqui
	        	}else if(error[0].innerText == "Acesso negado!"){
	        		$.ajax({
	                    type:'get',
	                    url:'/getName',
	                    async: false, 
	                    dataType: "json",
	                    data:{
	                 	   cpf: function()
	    	                      {
	    	                          return $('#cpf').val();
	    	                      }
	                    },

	                    success : function(resultado) {
	                    	var value , hora;
	                    	hora = resultado.dataEntrada.substr(-8);
	                        value = resultado.nome+ " Acesso negado, voçê já entrou hoje as "+hora;
	                        
	                        toastr.error(value);
	                        $('#cpf').val("")
	                    }
	    		   })
	        	}else{
	        		$(".cpf").prepend(error);
	        		 $('#cpf').val("")
	        	}
	        	
	         }
	         if(element.attr('name') == 'nome'){
	        	 $(".nome").prepend(error[0]);
	         }
	    },
	    success: function(label) {
            var parentId = " " + label[0].outerHTML;
            var separa = parentId.split('"');

            var valor = separa[1];
            
            switch(valor) {
                case "cpf-error":
                	$("#verificando").attr("hidden",true);
                	$("#1nome").attr("hidden",false);
                    break;
            }                    
        }
            
    });
    
    
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
   
    jQuery.validator.addMethod("checkFlag", function(value, element) {
		   value = jQuery.trim(value);
		   var teste = true;
		   $.ajax({
                type:'post',
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