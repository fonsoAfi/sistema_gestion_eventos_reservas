package ifw.daw.sger.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

@Entity
@Table(name = "usuarios")
public class Usuarios {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private int idUsuario;
    
    @NotEmpty(message = "El nombre propio no puede estar vacio")
	@NotBlank(message= "El nombre propio debe contener carateres alfanumericos")
    @Column(name = "nombre_real")
	private String nombreReal;
    
    @NotEmpty(message = "El nombre de perfil no puede estar vacio")
	@NotBlank(message= "El nombre de perfil debe contener carateres alfanumericos")
    @Column(name = "nombre_perfil")
	private String nombrePerfil;
    
    @NotEmpty(message = "El primer apellido no puede estar vacio")
	@NotBlank(message= "El primer apellido debe contener carateres alfanumericos")
    @Column(name = "apellido1")
	private String apellido1;

    @NotEmpty(message = "El segundo apellido propio no puede estar vacio")
	@NotBlank(message= "El segundo apellido debe contener carateres alfanumericos")
    @Column(name = "apellido2")
	private String apellido2;
    
	@NotBlank(message= "El email no puede estar vacio")
	@Email(message = "El email introducido inválido, revisa que este bien formado, ej: ejemplo@domain.com")
	@Column(name = "mail", unique = true)
	private String mail;
    
    @NotEmpty(message = "El numero de telefono no puede estar vacio")
	@NotBlank(message = "El numero de telefono debe contener carateres numericos")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Size(min = 9, message = "El numero de telefono de de tener como minimo 9 digitos")
    @Size(max = 20, message = "El numero de telefono de de tener como maximo 20 digitos")
    @Column(name = "telefono")
	private String telefono;
    
    @NotNull(message = "La fecha de nacimientos no puede ser nula")
    @Past(message = "La fecha de nacimiento debe estar en el pasado")
    @Column(name = "fecha_nacimiento")
	private LocalDate fechaNacimiento;
    
    @NotNull(message = "El pais no puede ser nulo")
    @Column(name = "pais")
	private String pais;
    
    @NotBlank(message= "La clave no puede estar vacia")
    @Size(min = 8, max = 64, message="La clave debe tener un minimo 8 caracteres y un maximo 64 caracteres")
    @Column(name = "clave")
	private String clave;
    
    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_cuenta", insertable=false)
	private EstadoCuenta estadoCuenta;
    
    @Column(name = "ultimo_acceso")
	private LocalDateTime ultimoAcceso;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "rol_usuario")
	private Rol rol;
    
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, optional = true)
    private Asistentes asistentes;
    
    @OneToOne(mappedBy = "usuario", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = true)
    private Organizadores organizadores;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reembolsos> reembolsos = new ArrayList<>();
    
    
    public Usuarios() {}
    
    public Usuarios(int idUsuario) {
    	this.idUsuario = idUsuario;
    }
    
    public Usuarios(int idUsuario, String mail, String clave, Rol rol) {
    	this.idUsuario = idUsuario;
    	this.mail = mail;
    	this.clave = clave;
    	this.rol = rol;
    }
    
    
    // getters
    public int getIdUsuario() {
		return idUsuario;
	}
    
    public String getNombreReal() {
		return nombreReal;
	}
    
    public String getNombrePerfil() {
		return nombrePerfil;
	}
    
    public String getApellido1() {
		return apellido1;
	}
    
    public String getApellido2() {
		return apellido2;
	}
    
    public String getMail() {
		return mail;
	}
    
    public String getTelefono() {
		return telefono;
	}
    
    public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}
    
    public String getPais() {
		return pais;
	}
    
    public String getClave() {
		return clave;
	}
    
    public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}
    
    public EstadoCuenta getEstadoCuenta() {
		return estadoCuenta;
	}
    
    public LocalDateTime getUltimoAcceso() {
		return ultimoAcceso;
	}
    
    public Rol getRol() {
		return rol;
	}
    
    public Asistentes getAsistentes() {
		return asistentes;
	}
    
    public Organizadores getOrganizadores() {
		return organizadores;
	}
    
    public List<Reembolsos> getReembolsos() {
		return reembolsos;
	}

    
    // setters
    public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
    
    public void setNombreReal(String nombreReal) {
		this.nombreReal = nombreReal;
	}
    
    public void setNombrePerfil(String nombrePerfil) {
		this.nombrePerfil = nombrePerfil;
	}
    
    public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
    
    public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
    
    public void setMail(String mail) {
		this.mail = mail;
	}
    
    public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
    
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
    
    public void setPais(String pais) {
		this.pais = pais;
	}
    
    public void setClave(String clave) {
    	this.clave = clave;
	}
    
    public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
    
    public void setEstadoCuenta(EstadoCuenta estadoCuenta) {
		this.estadoCuenta = estadoCuenta;
	}
    
    public void setUltimoAcceso(LocalDateTime ultimoAcceso) {
		this.ultimoAcceso = ultimoAcceso;
	}
    
    public void setRol(Rol rol) {
		this.rol = rol;
	}
    
    public void setAsistentes(Asistentes asistentes) {
		this.asistentes = asistentes;
	}
    
    public void setOrganizadores(Organizadores organizadores) {
		this.organizadores = organizadores;
	}
    
    public void setReembolsos(List<Reembolsos> reembolsos) {
		this.reembolsos = reembolsos;
	}

    
    @Override
    public String toString() {
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append("Usuario {")
    	  .append("\nid_usuario = ").append(idUsuario)
    	  .append("\nnombre_real = ").append(nombreReal)
          .append("\nnombre_perfil = ").append(nombrePerfil)
    	  .append("\napellido1 = ").append(apellido1)
    	  .append("\napellido2 = ").append(apellido2)
    	  .append("\nmail = ").append(mail)
    	  .append("\ntelefono = ").append(telefono)
    	  .append("\nfecha_nacimiento = ").append(fechaNacimiento)
    	  .append("\npais = ").append(pais)
    	  .append("\nclave = ").append(clave)
    	  .append("\nestado_cuenta = ").append(estadoCuenta)
    	  .append("\nultimo_acceso = ").append(ultimoAcceso)
    	  .append("\nrol_usuario = ").append(rol)
    	  .append("\n}");  
    	
    	return sb.toString();
    }
}