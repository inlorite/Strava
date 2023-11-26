package es.deusto.ingenieria.sd.strava.server.data.dto;

import java.util.ArrayList;
import java.util.List;

import es.deusto.ingenieria.sd.strava.server.data.domain.Usuario;

//This class is part of the DTO pattern. It also implements Singleton Pattern.
public class UsuarioAssembler {
	private static UsuarioAssembler instance;

	private UsuarioAssembler() {
	}

	public static UsuarioAssembler getInstance() {
		if (instance == null) {
			instance = new UsuarioAssembler();
		}

		return instance;
	}

	public UsuarioDTO usuarioToDTO(Usuario usuario) {
		UsuarioDTO dto = new UsuarioDTO();

		dto.setNombre(usuario.getNombre());
		dto.setAltura(usuario.getAltura());
		dto.setPeso(usuario.getPeso());
		dto.setFechaNacimiento(usuario.getFechaNacimiento());
		dto.setFrecuenciaCardiacaMax(usuario.getFrecuenciaCardiacaMax());
		dto.setFrecuenciaCardiacaReposo(usuario.getFrecuenciaCardiacaReposo());
		dto.setTipoServicio(usuario.getTipoServicio());

		return dto;
	}
	
	public Usuario dtoToUsuario(UsuarioDTO dto) {
		Usuario usuario = new Usuario();
		
		usuario.setNombre(dto.getNombre());
		usuario.setAltura(dto.getAltura());
		usuario.setPeso(dto.getPeso());
		usuario.setFechaNacimiento(dto.getFechaNacimiento());
		usuario.setFrecuenciaCardiacaMax(dto.getFrecuenciaCardiacaMax());
		usuario.setFrecuenciaCardiacaReposo(dto.getFrecuenciaCardiacaReposo());
		usuario.setTipoServicio(dto.getTipoServicio());
		
		return usuario;
	}
	
	public List<UsuarioDTO> usuarioToDTO(List<Usuario> usuario) {
		List<UsuarioDTO> dto = new ArrayList<>();

		for (Usuario u : usuario) {
			dto.add(this.usuarioToDTO(u));
		}

		return dto;
	}
	
	public List<Usuario> dtoToUsuario(List<UsuarioDTO> usuario) {
		List<Usuario> usuarios = new ArrayList<>();
		
		for (UsuarioDTO udto : usuario) {
			usuarios.add(this.dtoToUsuario(udto));
		}
		
		return usuarios;
	}
}