package com.example.evaluacion_rogerac.service.entity.service;

public class Cuentas {

    private int id;
    private String correo;
    private String password;
    private File foto;
    private boolean vigencia;
    private boolean listado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public File getFoto() {
        return foto;
    }

    public void setFoto(File foto) {
        this.foto = foto;
    }

    public boolean isVigencia() {
        return vigencia;
    }

    public void setVigencia(boolean vigencia) {
        this.vigencia = vigencia;
    }

    public boolean isListado() {
        return listado;
    }

    public void setListado(boolean listado) {
        this.listado = listado;
    }
}