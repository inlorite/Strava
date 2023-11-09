package es.deusto.ingenieria.sd.auctions.server.data.dto;

import es.deusto.ingenieria.sd.auctions.server.data.domain.Reto;

//This class is part of the DTO pattern. It also implements Singleton Pattern.
public class RetoAssembler {
	private static RetoAssembler instance;

	private RetoAssembler() {
	}

	public static RetoAssembler getInstance() {
		if (instance == null) {
			instance = new RetoAssembler();
		}

		return instance;
	}

	public RetoDTO retoToDTO(Reto reto) {
		RetoDTO dto = new RetoDTO();

		dto.setDistancia(reto.getDistancia());
		dto.setFechaFin(reto.getFechaFin());
		dto.setFechaInicio(reto.getFechaInicio());
		dto.setNombre(reto.getNombre());
		dto.setTiempo(reto.getTiempo());

		return dto;
	}
	
	public Reto dtoToReto(RetoDTO dto) {
		Reto reto = new Reto();

		reto.setDistancia(dto.getDistancia());
		reto.setFechaFin(dto.getFechaFin());
		reto.setFechaInicio(dto.getFechaInicio());
		reto.setNombre(dto.getNombre());
		reto.setTiempo(dto.getTiempo());
		reto.setCreador(dto.getCreador());

		return reto;
	}
}