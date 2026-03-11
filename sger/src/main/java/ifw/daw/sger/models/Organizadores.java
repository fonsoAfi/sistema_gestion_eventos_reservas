package ifw.daw.sger.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "organizadores")
public class Organizadores {

    @Id
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_usuario")
    private Usuarios usuario;

    @NotEmpty(message = "El nombre del organizador no puede estar vacio")
	@NotBlank(message = "El nombre del organizador debe contener carateres")
    @Column(name = "nombre_organizador")
    private String nombreOrganizador;
    
    @NotEmpty(message = "El tipo organizador no puede estar vacio")
	@NotBlank(message = "El tipo organizador debe contener carateres")
    @Column(name = "tipo")
    private String tipoOrganizador;
   
    @Min(value = 1, message= "El organizador debe tener como minimo 1 evento")
    @Max(value = 50, message= "El organizador no puede tener mas de 50 eventos")
    @Column(name = "limite_eventos")
    private Integer limiteEventos = 0;
    
    public Organizadores() {}
    
    // getters
    public Integer getIdUsuario() {
		return idUsuario;
	}
    
    public Usuarios getUsuario() {
		return usuario;
	}
    
    public String getNombreOrganizador() {
		return nombreOrganizador;
	}
	
    public String getTipoOrganizador() {
		return tipoOrganizador;
	}
    
    public Integer getLimiteEventos() {
		return limiteEventos;
	}
    
    // setters
    public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
    
    public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}
    
    public void setNombreOrganizador(String nombreOrganizador) {
		this.nombreOrganizador = nombreOrganizador;
	}
    
    public void setLimiteEventos(Integer limiteEventos) {
		this.limiteEventos = limiteEventos;
	}
    
    public void setTipoOrganizador(String tipoOrganizador) {
		this.tipoOrganizador = tipoOrganizador;
	}
    
}
