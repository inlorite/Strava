package es.deusto.ingenieria.sd.auctions.server.data.dto;

import es.deusto.ingenieria.sd.auctions.server.data.domain.Usuario;

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

	public UsuarioDTO userToDTO(Usuario usuario) {
		UsuarioDTO dto = new UsuarioDTO();

		dto.setNombre(usuario.getNombre());
		dto.setAltura(usuario.getAltura());
		dto.setPeso(usuario.getPeso());
		dto.setFechaNacimiento(usuario.getFechaNacimiento());
		dto.setFrecuenciaCardiacaMax(usuario.getFrecuenciaCardiacaMax());
		dto.setFrecuenciaCardiacaReposo(usuario.getFrecuenciaCardiacaReposo());

		return dto;
	}
}