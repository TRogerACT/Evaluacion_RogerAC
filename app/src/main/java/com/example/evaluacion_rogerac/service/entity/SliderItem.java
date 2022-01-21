package com.example.evaluacion_rogerac.service.entity;

public class SliderItem {
    private String tituto;
    private int imagen;

    public SliderItem() {
    }

    public SliderItem(int imagen, String tituto) {
        this.imagen = imagen;
        this.tituto = tituto;
    }

    public String getTituto() {
        return tituto;
    }

    public void setTituto(String tituto) {
        this.tituto = tituto;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
