package ifw.daw.sger.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "asistentes")
public class Asistentes {
	
    @Id
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_usuario")
    private Usuarios usuario;
    
    @Column(name = "puntos_fidelidad")
    private Integer puntosFidelidad;
    
    public Asistentes() {}
    
    // getters
    public Integer getIdUsuario() {
		return idUsuario;
	}
    
    public Usuarios getUsuario() {
		return usuario;
	}
    
    public Integer getPuntosFidelidad() {
		return puntosFidelidad;
	}
    
    // setters
    public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
    
    public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}
    
    public void setPuntosFidelidad(Integer puntosFidelidad) {
		this.puntosFidelidad = puntosFidelidad;
	}
	
}
