package com.controlatupeso;

public class Historico 
{

	private int id;
	private int id_perfil;
	private String peso;
	private String fecha;
	private String comentario;
	private String imc;
	private int dif;
	private String peso_objetivo;
	
	private int imageNumber;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_perfil() {
		return id_perfil;
	}
	public void setId_perfil(int id_perfil) {
		this.id_perfil = id_perfil;
	}
	public String getPeso() {
		return peso;
	}
	public void setPeso(String peso) {
		this.peso = peso;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getImc() {
		return imc;
	}
	public void setImc(String imc) {
		this.imc = imc;
	}
	public int getDif() {
		return dif;
	}
	public void setDif(int dif) {
		this.dif = dif;
	}
	public int getImageNumber() {
		return imageNumber;
	}
	public void setImageNumber(int imageNumber) {
		this.imageNumber = imageNumber;
	}
	public String getPeso_objetivo() {
		return peso_objetivo;
	}
	public void setPeso_objetivo(String peso_objetivo) {
		this.peso_objetivo = peso_objetivo;
	}
}
