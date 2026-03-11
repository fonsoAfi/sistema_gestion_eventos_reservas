package ifw.daw.sger.services;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifw.daw.sger.models.Categorias;
import ifw.daw.sger.models.Usuarios;
import ifw.daw.sger.repositories.AuthRepository;
import ifw.daw.sger.repositories.CategoriasRepository;
import jakarta.transaction.Transactional;

@Service
public class CategoriasService {

	@Autowired
	AuthRepository authRepo;
	
	@Autowired
	CategoriasRepository categoriasRepo;
	
	public List<Categorias> mostrarCategorias() {
		return categoriasRepo.findAll();
	}
	
	public Categorias getCategoria(int idCategoria) throws Exception {
		return categoriasRepo.findById(idCategoria).orElseThrow(() -> new Exception("Usuario no encontrado"));
	}
	
	@Transactional
	public void borrarCategoria(int idCategoria, Principal principal) throws Exception {
		Categorias categoria =  getCategoria(idCategoria);
		
		if (categoria == null) {			
			throw new RuntimeException("Categoria no encontrada");
		}
		
		Usuarios usuario = authRepo.findUserByMail(principal.getName()).get(0);
		if (!usuario.getRol().isAdmin()) {
			throw new Exception("El usuario no esta autorizado no es un administrador");
		}
		
		categoriasRepo.delete(categoria);
		
	}
	
	public void crearNuevaCategoria(Categorias categoriaForm, Principal principal) throws Exception {
		Usuarios usuario = authRepo.findUserByMail(principal.getName()).get(0);
		if (!usuario.getRol().isAdmin()) {
			throw new Exception("El usuario no esta autorizado no es un administrador");
		}
		categoriasRepo.save(categoriaForm);
		if (getCategoria(categoriaForm.getIdCategoria()) == null) {
			
			if (categoriaForm.getNombreCategoria() != null || !categoriaForm.getNombreCategoria().isBlank()) {	
				categoriasRepo.save(categoriaForm);
			} else {
				throw new RuntimeException("No has escrito un nombre para la categoria");
			}
			
		}
		
		
	}
	
}
