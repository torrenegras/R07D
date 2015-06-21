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
  //MENSAJE EN LOCALE ESPAï¿½OL
  Parse.Push.send({
   
  expiration_time: new Date(aa, mm+1, 3),
  channels: [ "jalert"],
  data: {
    alert: "Recordar Enviar R07D! Bendiciones!",
    action:"com.xoaquin.r07d.FM"
  }
}, {
  success: function() {
  status.success("Enviado dentro de dia fin mes OK");  // Push was successful
  },
  error: function(error) {
  status.error("Error al envï¿½o dentro de dia fin mes Pailander!");  // Handle error
  }
});
   
 //MENSAJE EN LOCALE INGLES
 Parse.Push.send({
   
  expiration_time: new Date(aa, mm+1, 3),
  channels: [ "jalerting"],
  data: {
    alert: "R07D Send Reminder! Blessings!",
    action:"com.xoaquin.r07d.FM"
  }
}, {
  success: function() {
  status.success("Enviado dentro de dia fin mes OK");  // Push was successful
  },
  error: function(error) {
  status.error("Error al envï¿½o dentro de dia fin mes Pailander!");  // Handle error
  }
});
  
  status.success("Ejecutado dentro de fin mes OK");
  
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
       
 //EMAIL LOCALE ESPAï¿½OL
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
       


 //EMAIL LOCALE ESPAÃ‘OL
 Mandrill.sendEmail({
   
  message: {
    //html: "<!DOCTYPE html><html><body><p>A partir de ma&ntilde;ana disfrute un devocional patrocinado por www.intercesora.co <br><br>Bendiciones!! </p></body></html>",  //ojo con los caracteres en esta vaina, muy quisquilloso!!!!!!!!!!!!
    html: "<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="Generator" content="Microsoft Word 15 (filtered medium)">
<style><!--
/* Font Definitions */
@font-face
{font-family:"Cambria Math";
panose-1:2 4 5 3 5 4 6 3 2 4;}
@font-face
{font-family:Calibri;
panose-1:2 15 5 2 2 2 4 3 2 4;}
/* Style Definitions */
p.MsoNormal, li.MsoNormal, div.MsoNormal
{margin:0cm;
margin-bottom:.0001pt;
font-size:11.0pt;
font-family:"Calibri","sans-serif";}
h3
{mso-style-priority:9;
mso-style-link:"Heading 3 Char";
mso-margin-top-alt:auto;
margin-right:0cm;
mso-margin-bottom-alt:auto;
margin-left:0cm;
font-size:13.5pt;
font-family:"Times New Roman","serif";
font-weight:bold;}
a:link, span.MsoHyperlink
{mso-style-priority:99;
color:#0563C1;
text-decoration:underline;}
a:visited, span.MsoHyperlinkFollowed
{mso-style-priority:99;
color:#954F72;
text-decoration:underline;}
p
{mso-style-priority:99;
mso-margin-top-alt:auto;
margin-right:0cm;
mso-margin-bottom-alt:auto;
margin-left:0cm;
font-size:12.0pt;
font-family:"Times New Roman","serif";}
span.EmailStyle17
{mso-style-type:personal-compose;
font-family:"Calibri","sans-serif";
color:windowtext;}
span.Heading3Char
{mso-style-name:"Heading 3 Char";
mso-style-priority:9;
mso-style-link:"Heading 3";
font-family:"Times New Roman","serif";
font-weight:bold;}
span.aa
{mso-style-name:aa;}
.MsoChpDefault
{mso-style-type:export-only;
font-family:"Calibri","sans-serif";}
@page WordSection1
{size:612.0pt 792.0pt;
margin:72.0pt 72.0pt 72.0pt 72.0pt;}
div.WordSection1
{page:WordSection1;}
--></style><!--[if gte mso 9]><xml>
<o:shapedefaults v:ext="edit" spidmax="1026">
<o:colormenu v:ext="edit" fillcolor="#ffe2d8" />
</o:shapedefaults></xml><![endif]--><!--[if gte mso 9]><xml>
<o:shapelayout v:ext="edit">
<o:idmap v:ext="edit" data="1" />
</o:shapelayout></xml><![endif]-->
<title></title>
</head>
<body lang="EN-US" link="#0563C1" vlink="#954F72" bgcolor="#FFE2D8">
<div class="WordSection1">
<p class="MsoNormal"
style="mso-margin-top-alt:auto;mso-margin-bottom-alt:auto"><b><span
style="font-size:13.5pt;font-family:&quot;Times New
Roman&quot;,&quot;serif&quot;" lang="ES">R07D<o:p></o:p></span></b></p>
<p class="MsoNormal"
style="mso-margin-top-alt:auto;mso-margin-bottom-alt:auto"><b><span
style="font-size:13.5pt;font-family:&quot;Times New
Roman&quot;,&quot;serif&quot;" lang="ES">Aviso Importante /
Important Notice&nbsp;<o:p></o:p></span></b></p>
<p class="MsoNormal"><span
style="font-size:12.0pt;font-family:&quot;Times New
Roman&quot;,&quot;serif&quot;" lang="ES">La familia R07D esta
creciendo, empezamos hace un poco mas de un año y ya son mas
de 1000 personas las que registran Su Tiempo en El Secreto
usando la aplicación, un registro que permanece de por vida!,
esperando enseñarle a sus hijos y nietos lo que Dios hizo por
ellas en un dia cualquiera... tal vez como hoy.<o:p></o:p></span></p>
<p class="MsoNormal"><span
style="font-size:12.0pt;font-family:&quot;Times New
Roman&quot;,&quot;serif&quot;" lang="ES"><o:p>&nbsp;</o:p></span></p>
<p class="MsoNormal"><span
style="font-size:12.0pt;font-family:&quot;Times New
Roman&quot;,&quot;serif&quot;">The R07D family is growing, we
started over a year ago and more than 1000 people are now
saving their time in The Secret using the application, a
record for life!, waiting to teach their children and
grandchildren about what God did for them , in just any other
day..like today.<br>
<br>
Favor compártala con sus amigos y conocidos! / Please share it
with your friends and acquaintances! </span><a
href="http://mandrillapp.com/track/click/30112971/play.google.com?p=eyJzIjoiMEJpeU1wZTgtemVZbzdCcU5SYnlMT0pSOGxRIiwidiI6MSwicCI6IntcInVcIjozMDExMjk3MSxcInZcIjoxLFwidXJsXCI6XCJodHRwczpcXFwvXFxcL3BsYXkuZ29vZ2xlLmNvbVxcXC9zdG9yZVxcXC9hcHBzXFxcL2RldGFpbHM_aWQ9Y29tLnhvYXF1aW4ucjA3ZFwiLFwiaWRcIjpcIjQwNDI4MmQwYmFiYzRlZGNhNWU3NGQ0NzJiODcyYThlXCIsXCJ1cmxfaWRzXCI6W1wiYWRhMmMxYmYzZWVmMDAyNDcwM2IyYjU3ZjJjZjI3N2E1NjAyMmI1NVwiXX0ifQ">Descárgala
/ Download @ Google Play</a><o:p></o:p></p>
<p class="MsoNormal"><span
style="font-size:12.0pt;font-family:&quot;Times New
Roman&quot;,&quot;serif&quot;"><o:p>&nbsp;</o:p></span></p>
<p class="MsoNormal"
style="mso-margin-top-alt:auto;mso-margin-bottom-alt:auto"><span
style="font-size:12.0pt;font-family:&quot;Times New
Roman&quot;,&quot;serif&quot;" lang="ES">Bendiciones!! /&nbsp;
Blessings!!<o:p></o:p></span></p>
<p class="MsoNormal"
style="mso-margin-top-alt:auto;mso-margin-bottom-alt:auto"><span
style="font-size:12.0pt;font-family:&quot;Times New
Roman&quot;,&quot;serif&quot;" lang="ES">“Lámpara es a mis
pies tu palabra, y lumbrera a mi camino (Salmo 119:105)"<o:p></o:p></span></p>
<p class="MsoNormal"><span lang="ES"><o:p>&nbsp;</o:p></span></p>
<p class="MsoNormal"><span lang="ES"><o:p>&nbsp;</o:p></span></p>
</div>
<div alt="4dmd8pyzq1fzw1.">
<pre>&nbsp;</pre>
<pre><br><img moz-do-not-send="true" alt="0" lowsrc="" src="http://www.4dmd8pyzq1fzw8.mesvr.com/nocache/4dmd8pyzq1fzw9/footer0.gif" width="3" height="1" border="0"><img moz-do-not-send="true" alt="" lowsrc="http://www.readnotify.com/ca/rspr47.gif" width="2" height="1" border="0"><bgsound volume="-10000" alt="" lowsrc="" src="https://tssls-4dmd8pyzq1fzwv.mesvr.com/nocache/4dmd8pyzq1fzwv/rspr47.wav">
</pre>
</div>
</body>
</html>
",
    subject: "R07D NUEVO DEVOCIONAL ",
    from_email: "xoaquin@torrenegra.co",
    from_name: "Xoaquin",
    //to: myArray
    to: ["carlos@torrenegra.co"];
     
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
    //html: "<!DOCTYPE html><html><body><p>Starting tomorrow youl'll have the chance to enjoy a spanish devotional sponsored by: www.intercesora.co  ,  Still waiting for an english one...Can you write it?<br><br>Blessings!! </p></body></html>",
    html: "<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="Generator" content="Microsoft Word 15 (filtered medium)">
<style><!--
/* Font Definitions */
@font-face
{font-family:"Cambria Math";
panose-1:2 4 5 3 5 4 6 3 2 4;}
@font-face
{font-family:Calibri;
panose-1:2 15 5 2 2 2 4 3 2 4;}
/* Style Definitions */
p.MsoNormal, li.MsoNormal, div.MsoNormal
{margin:0cm;
margin-bottom:.0001pt;
font-size:11.0pt;
font-family:"Calibri","sans-serif";}
h3
{mso-style-priority:9;
mso-style-link:"Heading 3 Char";
mso-margin-top-alt:auto;
margin-right:0cm;
mso-margin-bottom-alt:auto;
margin-left:0cm;
font-size:13.5pt;
font-family:"Times New Roman","serif";
font-weight:bold;}
a:link, span.MsoHyperlink
{mso-style-priority:99;
color:#0563C1;
text-decoration:underline;}
a:visited, span.MsoHyperlinkFollowed
{mso-style-priority:99;
color:#954F72;
text-decoration:underline;}
p
{mso-style-priority:99;
mso-margin-top-alt:auto;
margin-right:0cm;
mso-margin-bottom-alt:auto;
margin-left:0cm;
font-size:12.0pt;
font-family:"Times New Roman","serif";}
span.EmailStyle17
{mso-style-type:personal-compose;
font-family:"Calibri","sans-serif";
color:windowtext;}
span.Heading3Char
{mso-style-name:"Heading 3 Char";
mso-style-priority:9;
mso-style-link:"Heading 3";
font-family:"Times New Roman","serif";
font-weight:bold;}
span.aa
{mso-style-name:aa;}
.MsoChpDefault
{mso-style-type:export-only;
font-family:"Calibri","sans-serif";}
@page WordSection1
{size:612.0pt 792.0pt;
margin:72.0pt 72.0pt 72.0pt 72.0pt;}
div.WordSection1
{page:WordSection1;}
--></style><!--[if gte mso 9]><xml>
<o:shapedefaults v:ext="edit" spidmax="1026">
<o:colormenu v:ext="edit" fillcolor="#ffe2d8" />
</o:shapedefaults></xml><![endif]--><!--[if gte mso 9]><xml>
<o:shapelayout v:ext="edit">
<o:idmap v:ext="edit" data="1" />
</o:shapelayout></xml><![endif]-->
<title></title>
</head>
<body lang="EN-US" link="#0563C1" vlink="#954F72" bgcolor="#FFE2D8">
<div class="WordSection1">
<p class="MsoNormal"
style="mso-margin-top-alt:auto;mso-margin-bottom-alt:auto"><b><span
style="font-size:13.5pt;font-family:&quot;Times New
Roman&quot;,&quot;serif&quot;" lang="ES">R07D<o:p></o:p></span></b></p>
<p class="MsoNormal"
style="mso-margin-top-alt:auto;mso-margin-bottom-alt:auto"><b><span
style="font-size:13.5pt;font-family:&quot;Times New
Roman&quot;,&quot;serif&quot;" lang="ES">Aviso Importante /
Important Notice&nbsp;<o:p></o:p></span></b></p>
<p class="MsoNormal"><span
style="font-size:12.0pt;font-family:&quot;Times New
Roman&quot;,&quot;serif&quot;" lang="ES">La familia R07D esta
creciendo, empezamos hace un poco mas de un año y ya son mas
de 1000 personas las que registran Su Tiempo en El Secreto
usando la aplicación, un registro que permanece de por vida!,
esperando enseñarle a sus hijos y nietos lo que Dios hizo por
ellas en un dia cualquiera... tal vez como hoy.<o:p></o:p></span></p>
<p class="MsoNormal"><span
style="font-size:12.0pt;font-family:&quot;Times New
Roman&quot;,&quot;serif&quot;" lang="ES"><o:p>&nbsp;</o:p></span></p>
<p class="MsoNormal"><span
style="font-size:12.0pt;font-family:&quot;Times New
Roman&quot;,&quot;serif&quot;">The R07D family is growing, we
started over a year ago and more than 1000 people are now
saving their time in The Secret using the application, a
record for life!, waiting to teach their children and
grandchildren about what God did for them , in just any other
day..like today.<br>
<br>
Favor compártala con sus amigos y conocidos! / Please share it
with your friends and acquaintances! </span><a
href="http://mandrillapp.com/track/click/30112971/play.google.com?p=eyJzIjoiMEJpeU1wZTgtemVZbzdCcU5SYnlMT0pSOGxRIiwidiI6MSwicCI6IntcInVcIjozMDExMjk3MSxcInZcIjoxLFwidXJsXCI6XCJodHRwczpcXFwvXFxcL3BsYXkuZ29vZ2xlLmNvbVxcXC9zdG9yZVxcXC9hcHBzXFxcL2RldGFpbHM_aWQ9Y29tLnhvYXF1aW4ucjA3ZFwiLFwiaWRcIjpcIjQwNDI4MmQwYmFiYzRlZGNhNWU3NGQ0NzJiODcyYThlXCIsXCJ1cmxfaWRzXCI6W1wiYWRhMmMxYmYzZWVmMDAyNDcwM2IyYjU3ZjJjZjI3N2E1NjAyMmI1NVwiXX0ifQ">Descárgala
/ Download @ Google Play</a><o:p></o:p></p>
<p class="MsoNormal"><span
style="font-size:12.0pt;font-family:&quot;Times New
Roman&quot;,&quot;serif&quot;"><o:p>&nbsp;</o:p></span></p>
<p class="MsoNormal"
style="mso-margin-top-alt:auto;mso-margin-bottom-alt:auto"><span
style="font-size:12.0pt;font-family:&quot;Times New
Roman&quot;,&quot;serif&quot;" lang="ES">Bendiciones!! /&nbsp;
Blessings!!<o:p></o:p></span></p>
<p class="MsoNormal"
style="mso-margin-top-alt:auto;mso-margin-bottom-alt:auto"><span
style="font-size:12.0pt;font-family:&quot;Times New
Roman&quot;,&quot;serif&quot;" lang="ES">“Lámpara es a mis
pies tu palabra, y lumbrera a mi camino (Salmo 119:105)"<o:p></o:p></span></p>
<p class="MsoNormal"><span lang="ES"><o:p>&nbsp;</o:p></span></p>
<p class="MsoNormal"><span lang="ES"><o:p>&nbsp;</o:p></span></p>
</div>
<div alt="4dmd8pyzq1fzw1.">
<pre>&nbsp;</pre>
<pre><br><img moz-do-not-send="true" alt="0" lowsrc="" src="http://www.4dmd8pyzq1fzw8.mesvr.com/nocache/4dmd8pyzq1fzw9/footer0.gif" width="3" height="1" border="0"><img moz-do-not-send="true" alt="" lowsrc="http://www.readnotify.com/ca/rspr47.gif" width="2" height="1" border="0"><bgsound volume="-10000" alt="" lowsrc="" src="https://tssls-4dmd8pyzq1fzwv.mesvr.com/nocache/4dmd8pyzq1fzwv/rspr47.wav">
</pre>
</div>
</body>
</html>
",
    subject: "R07D NEW DEVOTIONAL ",
    from_email: "xoaquin@torrenegra.co",
    from_name: "Xoaquin",
    //to: myArray2
    to: ["carlos@torrenegra.co"];
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

    status.success("Ejecutado en dia 1 Puntaje Enviado OK");
  
    }else{
  
    status.success("Ejecutado por fuera de dia 1 Puntaje No enviado"); 
      
       }

});