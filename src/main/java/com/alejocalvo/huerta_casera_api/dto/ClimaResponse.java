package com.alejocalvo.huerta_casera_api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ClimaResponse {

    @JsonAlias("latitude")
    private double latitud;

    @JsonAlias("longitude")
    private double longitud;

    @JsonAlias("timezone")
    private String zonaHoraria;

    private ClimaActual current;

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getZonaHoraria() {
        return zonaHoraria;
    }

    public void setZonaHoraria(String zonaHoraria) {
        this.zonaHoraria = zonaHoraria;
    }

    public ClimaActual getCurrent() {
        return current;
    }

    public void setCurrent(ClimaActual current) {
        this.current = current;
    }

    public static class ClimaActual {

        @JsonAlias("time")
        private String hora;

        @JsonAlias("temperature_2m")
        private double temperatura;

        @JsonAlias("relative_humidity_2m")
        private int humedad;

        @JsonAlias("precipitation")
        private double precipitacion;

        @JsonAlias("wind_speed_10m")
        private double velocidadViento;

        public String getHora() {
            return hora;
        }

        public void setHora(String hora) {
            this.hora = hora;
        }

        public double getTemperatura() {
            return temperatura;
        }

        public void setTemperatura(double temperatura) {
            this.temperatura = temperatura;
        }

        public int getHumedad() {
            return humedad;
        }

        public void setHumedad(int humedad) {
            this.humedad = humedad;
        }

        public double getPrecipitacion() {
            return precipitacion;
        }

        public void setPrecipitacion(double precipitacion) {
            this.precipitacion = precipitacion;
        }

        public double getVelocidadViento() {
            return velocidadViento;
        }

        public void setVelocidadViento(double velocidadViento) {
            this.velocidadViento = velocidadViento;
        }
    }
}
