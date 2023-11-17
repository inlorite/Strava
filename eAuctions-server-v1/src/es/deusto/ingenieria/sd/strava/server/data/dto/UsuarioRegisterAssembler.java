package es.deusto.ingenieria.sd.strava.server.data.dto;

import java.util.ArrayList;

import es.deusto.ingenieria.sd.strava.server.data.domain.SesionEntrenamiento;
import es.deusto.ingenieria.sd.strava.server.data.domain.Usuario;

//This class is part of the DTO pattern. It also implements Singleton Pattern.
public class UsuarioRegisterAssembler {
	private static UsuarioRegisterAssembler instance;

	private UsuarioRegisterAssembler() {
	}

	public static UsuarioRegisterAssembler getInstance() {
		if (instance == null) {
			instance = new UsuarioRegisterAssembler();
		}

		return instance;
	}

	public Usuario dtoToUsuario(UsuarioRegisterDTO dto) {
		Usuario usuario = new Usuario();

		usuario.setNombre(dto.getNombre());
		usuario.setAltura(dto.getAltura());
		usuario.setPeso(dto.getPeso());
		usuario.setFechaNacimiento(dto.getFechaNacimiento());
		usuario.setFrecuenciaCardiacaMax(dto.getFrecuenciaCardiacaMax());
		usuario.setFrecuenciaCardiacaReposo(dto.getFrecuenciaCardiacaReposo());
		usuario.setEmail(dto.getEmail());
		usuario.setContrasena(dto.getContrasena());
		usuario.setSesionesEntrenamiento(new ArrayList<SesionEntrenamiento>());

		return usuario;
	}
}