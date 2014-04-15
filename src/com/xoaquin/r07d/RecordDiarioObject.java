
//OBJETO PARA ALIMENTAR BASE DE DATOS LOCAL 

package com.xoaquin.r07d;

public class RecordDiarioObject {

	String _adg; //ET accion de gracias 
	String _anio;//A–o,  ej 2014
	String _aop;// boolean asisti oracion presencial
	String _aopi;// boolean asisti oracion por internet
	String _coo; //boolean oracion por coordinadores 
	String _dia; // dia , ej 05
	String _fecha;//fecha , ej 18-03-2014
	String _horaf;// hora fin , ej 10:53 AM
	String _horai;// hora inicial, ej 10:43 PM
	String _lb;// ET lectura biblica 
	String _ldn;// ET lista de nuevos
	String _mes;// Mes, ej 03
	String _mgc;//boolean oracion por miembros de grupo conexion
	String _opd;//boolean oracion por discipulos
	String _oplco;//boolean oracion por la cosecha
	String _opl;//boolean oracion por pastores y lideres
	String _pei; //ET peticiones e intercesion
	String _qmhD;//ET que me hablo Dios
	
   //constructor vacio , un tema de prevenir errores, algo asi...	
	public RecordDiarioObject(){}
	
	//constructor
	public RecordDiarioObject(String adg,String anio,String aop,String aopi,String coo,String dia,String fecha,String horaf, String horai, String lb,String ldn,String mes,String mgc,String opd, String oplco,String opl,String pei,String qmhD){
		this._adg=adg;
		this._anio=anio;
		this._aop=aop;
		this._aopi=aopi;
		this._coo=coo;
		this._dia=dia;
		this._fecha=fecha;
		this._horaf=horaf;
		this._horai=horai;
		this._lb=lb;
		this._ldn=ldn;
		this._mes=mes;
		this._mgc=mgc;
		this._opd=opd;
		this._opl=opl;
		this._oplco=oplco;
		this._pei=pei;
		this._qmhD=qmhD;
	}
	
	//getters y setters del objeto
	
	//getters
	
	public String getadg(){
        return this._adg;
    }
	
	public String getanio(){
        return this._anio;
    }
	
	public String getaop(){
        return this._aop;
    }
	
	public String getaopi(){
        return this._aopi;
    }
	
	public String getcoo(){
        return this._coo;
    }
	
	public String getdia(){
        return this._dia;
    }
	
	public String getfecha(){
        return this._fecha;
    }
	
	public String gethoraf(){
        return this._horaf;
    }
	
	public String gethorai(){
        return this._horai;
    }
	
	public String getlb(){
        return this._lb;
    }
	
	public String getldn(){
        return this._ldn;
    }
	
	public String getmes(){
        return this._mes;
    }
	
	public String getmgc(){
        return this._mgc;
    }
	
	public String getopd(){
        return this._opd;
    }
	
	public String getopl(){
        return this._opl;
    }
	
	public String getoplco(){
        return this._oplco;
    }
	
	public String getpei(){
        return this._pei;
    }
	
	public String getqmhD(){
        return this._qmhD;
    }
	
	
	
	//setters
	
	public void setadg(String adg){
        this._adg = adg;
    }
	
	public void setanio(String anio){
        this._anio = anio;
    }
	public void setaop(String aop){
        this._aop = aop;
    }
	public void setaopi(String aopi){
        this._aopi = aopi;
    }
	public void setcoo(String coo){
        this._coo = coo;
    }
	public void setdia(String dia){
        this._dia = dia;
    }
	public void setfecha(String fecha){
        this._fecha = fecha;
    }
	public void sethoraf(String horaf){
        this._horaf = horaf;
    }
	public void sethorai(String horai){
        this._horai = horai;
    }
	public void setlb(String lb){
        this._lb = lb;
    }
	public void setldn(String ldn){
        this._ldn = ldn;
    }
	public void setmes(String mes){
        this._mes = mes;
    }
	public void setmgc(String mgc){
        this._mgc = mgc;
    }
	public void setopd(String opd){
        this._opd = opd;
    }
	public void setopl(String opl){
        this._opl = opl;
    }
	public void setoplco(String oplco){
        this._oplco = oplco;
    }
	public void setpei(String pei){
        this._pei = pei;
    }
	public void setqmhD(String qmhD){
        this._qmhD = qmhD;
    }
	
	
}
