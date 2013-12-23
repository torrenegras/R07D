
//NUEVA UBICACION
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
  status.error("Error al envío dentro de dia fin mes Pailander!");  // Handle error
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
    
if(dd==1){

 var myArray = [];
 myArray[0] = {email:"torrenegrajr@gmail.com", name:"", type:"to"};
 
 query.each(function(user) {
      
  var ue = user.getEmail();
  myArray[i] = {email:ue, name:"",type:"bcc"};
  i=i+1;
 
 }).then(function() { //si todo ok en query.each {
      
 
Mandrill.sendEmail({
  
  message: {
    html: "<!DOCTYPE html><html><body><p>Empieza el mes con toda&#33;&#44;en El Secreto&#44;cercano al Se&#241;or&#33;<br><br>Bendiciones&#33;<br><br><br><br>Cada vez somos mas&#33; Comparte la aplicaci&#243;n con tus amigos y conocidos&#33;<br><a href=\"https://play.google.com/store/apps/details?id=com.xoaquin.r07d \">Desc&#225;rgala en Google Play</a> </p></body></html>",
    subject: "COMUNIDAD R07D",
    from_email: "xoaquin@outlook.com",
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
 
       
    status.success("EMAIL COMIENZO DE MES ENVIADO");
    

}, function(error) {  //si algo salio mal en query.each
    
    status.error("ALGO SALIO MAL!!");
  });
	


 
}else{ // Cierre del If comienzo mes
   status.success("NO ES COMIENZO DE MES");
}
 

 
}); //cierre Job






