//NUEVA UBICACION
//MANDRIL USER torrenegrajr@gmail.com
//MANDRIL PASSWORD****************************========================= cenfol1111   OJO!!!!!!!
  
 
//EJEMPLO DE CREACION DE FUNCION EN CLOUD
Parse.Cloud.define("hello2", function(request, response) {
  
  response.success(ssbcc);
});
 
 
 
 
//JOB PARA MENSAJES PUSH FIN DE MES ENVIO DE REPORTE AL LIDER
 
Parse.Cloud.job("pushfinmes", function(request, status) {
     
    var today = new Date();
    var dd = today.getDate();
    var mm=today.getMonth();
    var aa=today.getFullYear();
     
    var lastDay = new Date(today.getFullYear(), today.getMonth() + 1, 0);
    var ld=lastDay.getDate();
     
 
if (dd==ld){
  //MENSAJE EN LOCALE ESPA�OL
  Parse.Push.send({
   
  expiration_time: new Date(aa, mm+1, 3),
  channels: [ "jalert"],
  data: {
    alert: "Recordar Enviar R07D! Bendiciones!"
  }
}, {
  success: function() {
  status.success("Enviado dentro de dia fin mes OK");  // Push was successful
  },
  error: function(error) {
  status.error("Error al env�o dentro de dia fin mes Pailander!");  // Handle error
  }
});
   
 //MENSAJE EN LOCALE INGLES
 Parse.Push.send({
   
  expiration_time: new Date(aa, mm+1, 3),
  channels: [ "jalerting"],
  data: {
    alert: "R07D Send Reminder! Blessings!"
  }
}, {
  success: function() {
  status.success("Enviado dentro de dia fin mes OK");  // Push was successful
  },
  error: function(error) {
  status.error("Error al env�o dentro de dia fin mes Pailander!");  // Handle error
  }
});
  
  
  
 }else{
     
    status.success("Ejecutado por fuera de fin de mes");
      
 }
  
 
 });
  
  
 
  
 //JOB PARA ENVIO EMAILS DE ANIMO COMIENZO DE MES
  
 Parse.Cloud.job("emailcomienzomes", function(request, status) {
     
 
  var Mandrill = require('mandrill');
  Mandrill.initialize('1JXzvtkvJOoZ_VVMpNt2aQ');
   
  var query = new Parse.Query(Parse.User);
   
  var today = new Date();
  var dd = today.getDate();
   
  var i=1;
  var j=1;
 
if(dd==1){
 
 
 var myArray = [];
 var myArray2 = [];
 myArray[0] = {email:"torrenegrajr@gmail.com", name:"", type:"to"};
 myArray2[0] = {email:"torrenegrajr@gmail.com", name:"", type:"to"};
  
  
 query.each(function(user) {
       
  var ue = user.getEmail();
  var loc = user.get("locale");
   
  //DISTINGUIENDO ARREGLOS EN BASE A LOCALE
 if (/espa\xF1ol/i.test(loc)) {
     
  myArray[i] = {email:ue, name:"",type:"bcc"};
  i=i+1;
   
   }else{
   
  myArray2[j] = {email:ue, name:"",type:"bcc"};
  j=j+1;
      
   }
    
    
 }).then(function() { //si todo ok en query.each {
       
 //EMAIL LOCALE ESPA�OL
 Mandrill.sendEmail({
   
  message: {
    html: "<!DOCTYPE html><html><body><p>Empieza el mes con toda&#33;&#44;en El Secreto&#44;cercano al Se&#241;or&#33;<br><br>Bendiciones&#33;<br><br><br><br>Cada vez somos mas&#33; Comparte la aplicaci&#243;n con tus amigos y conocidos&#33;<br><a href=\"https://play.google.com/store/apps/details?id=com.xoaquin.r07d \">Desc&#225;rgala en Google Play</a> </p></body></html>",
    subject: "COMUNIDAD R07D",
    from_email: "xoaquin@torrenegra.co",
    from_name: "Xoaquin",
    to: myArray
  },
  async: true
},{
  success: function(httpResponse) {
    console.log(httpResponse);
    response.success("Email sent!");
  },
  error: function(httpResponse) {
    console.error(httpResponse);
    response.error("Uh oh, something went wrong");
  }
});   // Cierre Mandrill SendEmail
  
  
 //EMAIL LOCALE INGLES
 Mandrill.sendEmail({
   
  message: {
    html: "<!DOCTYPE html><html><body><p>Start this month with all you got&#33;&#44;in The Secret&#44;close to the Lord&#33;<br><br>Blessings&#33;<br><br><br><br>Growing more and more each day&#33; Share the application with your friends and acquaintances&#33;<br><a href=\"https://play.google.com/store/apps/details?id=com.xoaquin.r07d \">Download it at Google Play</a> </p></body></html>",
    subject: "R07D COMMUNITY",
    from_email: "xoaquin@torrenegra.co",
    from_name: "Xoaquin",
    to: myArray2
  },
  async: true
},{
  success: function(httpResponse) {
    console.log(httpResponse);
    response.success("Email sent!");
  },
  error: function(httpResponse) {
    console.error(httpResponse);
    response.error("Uh oh, something went wrong");
  }
});   // Cierre Mandrill SendEmail
 
 
        
    status.success("EMAIL COMIENZO DE MES ENVIADO");
     
 
}, function(error) {  //si algo salio mal en query.each
     
    status.error("ALGO SALIO MAL!!");
  });
 
 
 
 
 
  
}else{ // Cierre del If comienzo mes
   status.success("NO ES COMIENZO DE MES");
}
  
 
  
}); //cierre Job
 
 
 
 
//FUNCION EMAIL INFO NUEVO USUARIO Y CONTEO TOTAL USUARIOS
Parse.Cloud.define("infonewusers", function(request, response) {
  
var Mandrill = require('mandrill');
Mandrill.initialize('1JXzvtkvJOoZ_VVMpNt2aQ');
 
 
var query = new Parse.Query(Parse.User);
 
query.count({
  success: function(count) {
    // The count request succeeded. Show the count
    
   
   Mandrill.sendEmail({
  message: {
    text: "NUEVO USUARIO: "+request.params.correo+"   TOTAL USUARIOS: "+count,
    subject: "NUEVO REGISTRO DE USUARIO EN R07D",
    from_email: "xoaquin@outlook.com",
    from_name: "Xoaquin",
    to: [
      {
        email: "carlos@torrenegra.co",
        name: "CARLOS TORRENEGRA"
      }
    ]
  },
  async: true
},{
  success: function(httpResponse) {
    console.log(httpResponse);
    response.success("Email sent!");
  },
  error: function(httpResponse) {
    console.error(httpResponse);
    response.error("Uh oh, something went wrong");
  }
});
    
 
    
  },
  error: function(error) {
    // The request failed
  }
});
 
 
 
  
   
});//cierre funcion email nuevo usuario



//ENVIO EMAIL BIENVENIDA USUARIO
Parse.Cloud.define("bienvenidausr", function(request, response) {
  
    var Mandrill = require('mandrill');
    Mandrill.initialize('1JXzvtkvJOoZ_VVMpNt2aQ');
 
  
  
    Mandrill.sendEmail({
  message: {
    //text: request.params.correo+"\n\nBienvenido a la comunidad R07D, Bendiciones! Usted va a recibir una notificacion para verficar su correo electronico. Si no la recibe, buscar en SPAM / No-Deseados.\n\nWelcome to the R07D community, Blessings! You're going to receive a notification to verfy your email. If not received please check the SPAM folder. ",
    html: "<!DOCTYPE html><html><body><p><br><br>Bienvenido a la comunidad R07D, Bendiciones! Usted va a recibir una notificaci&#243;n para verficar su correo electr&#243;nico. Si no la recibe, buscar en SPAM / No-Deseados.<br><br>Welcome to the R07D community, Blessings! You're going to receive a notification to verfy your email. If not received please check the SPAM folder. </p></body></html>",
    subject: "BIENVENIDO / WELCOME R07D",
    from_email: "xoaquin@torrenegra.co",
    from_name: "xoaquin@torrenegra.co",
    to: [
      {
        email: request.params.correo,
        name: request.params.correo
      }
    ]
  },
  async: true
},{
  success: function(httpResponse) {
    console.log(httpResponse);
    response.success("Email sent!");
  },
  error: function(httpResponse) {
    console.error(httpResponse);
    response.error("Uh oh, something went wrong");
  }
});
 
});
 



 //JOB PARA ENVIO CUSTOM EMAILS A TODOS
  
 Parse.Cloud.job("emailtodos", function(request, status) {
     
 
  var Mandrill = require('mandrill');
  Mandrill.initialize('1JXzvtkvJOoZ_VVMpNt2aQ');
   
  var query = new Parse.Query(Parse.User);
   
  
   
  var i=1;
  var j=1;
 

 
 
 var myArray = [];
 var myArray2 = [];
 myArray[0] = {email:"torrenegrajr@gmail.com", name:"", type:"to"};
 myArray2[0] = {email:"torrenegrajr@gmail.com", name:"", type:"to"};
  
  
 query.each(function(user) {
       
  var ue = user.getEmail();
  var loc = user.get("locale");
   
  //DISTINGUIENDO ARREGLOS EN BASE A LOCALE
 
 if (/espa\xF1ol/i.test(loc)) {
   
  myArray[i] = {email:ue, name:"",type:"bcc"};
  i=i+1;
   
   }else{
   
  myArray2[j] = {email:ue, name:"",type:"bcc"};
  j=j+1;
    
   }
    
    
 }).then(function() { //si todo ok en query.each {
       


 //EMAIL LOCALE ESPAÑOL
 Mandrill.sendEmail({
   
  message: {
    html: "<!DOCTYPE html><html><body><p>Favor instalar actualizacion<br><br><a href=\"https://play.google.com/store/apps/details?id=com.xoaquin.r07d \">Ver: 5.1 (Uzias)</a> </p></body></html>",  //ojo con los caracteres en esta vaina, muy quisquilloso!!!!!!!!!!!!
    subject: "R07D ACTUALIZACION IMPORTANTE ",
    from_email: "xoaquin@torrenegra.co",
    from_name: "Xoaquin",
    to: myArray
     
  },
  async: true
},{
  success: function(httpResponse) {
    console.log(httpResponse);
    response.success("Email sent!");
  },
  error: function(httpResponse) {
    console.error(httpResponse);
    response.error("Uh oh, something went wrong");
  }
});   // Cierre Mandrill SendEmail
 

        
    
    
 //EMAIL LOCALE INGLES
 Mandrill.sendEmail({
   
  message: {
    html: "<!DOCTYPE html><html><body><p>Please install update<br><br><a href=\"https://play.google.com/store/apps/details?id=com.xoaquin.r07d \">Ver: 5.1 (Uzias)</a> </p></body></html>",
    subject: "R07D IMPORTANT UPDATE ",
    from_email: "xoaquin@torrenegra.co",
    from_name: "Xoaquin",
    to: myArray2
  
     },
  async: true
},{
  success: function(httpResponse) {
    console.log(httpResponse);
    response.success("Email sent!");
  },
  error: function(httpResponse) {
    console.error(httpResponse);
    response.error("Uh oh, something went wrong");
  }
});   // Cierre Mandrill SendEmail
    
 
    
    
    status.success("EMAILS ENVIADOS");
     
 
}, function(error) {  //si algo salio mal en query.each
     
    status.error("ALGO SALIO MAL!!");
  });
 
 
 
 
 
  
 
  
}); //cierre Job
 
 


Parse.Cloud.job("pushavisoimportante", function(request, status) {
   
    Parse.Push.send({
  channels: [ "todos" ],
  data: {
    action:"com.xoaquin.r07d.AVISO"
    
  }
}, {
  success: function() {
    status.success("Aviso Importante Enviado");  // Push was successful
  },
  error: function(error) {
    status.error("Aviso Importante No Enviado ERROR"); // Handle error
  }
});
});


Parse.Cloud.job("puntajes", function(request, status) {
    
    
 var today = new Date();
  var dd = today.getDate();
  
  if(dd==1){
      
  
    Parse.Push.send({
  channels: [ "todos" ],
  data: {
    action:"com.xoaquin.r07d.NIVEL"
    
    
  }
}, {
  success: function() {
    status.success("Puntajes Enviados");  // Push was successful// Push was successful
  },
  error: function(error) {
    status.error("Puntajes No Enviados ERROR"); // Handle error// Handle error
  }
});


  }
  
  status.success("No es dia 1 Puntaje No enviado"); 
  

});