package es.deusto.ingenieria.sd.auctions.server.data.dto;

import es.deusto.ingenieria.sd.auctions.server.data.domain.SesionEntrenamiento;

public class SesionEntrenamientoAssembler {
	private static SesionEntrenamientoAssembler instance;

	private SesionEntrenamientoAssembler() {
	}

	public static SesionEntrenamientoAssembler getInstance() {
		if (instance == null) {
			instance = new SesionEntrenamientoAssembler();
		}

		return instance;
	}

	public SesionEntrenamientoDTO sesionEntrenamientoToDTO(SesionEntrenamiento sesionEntrenamiento) {
		SesionEntrenamientoDTO dto = new SesionEntrenamientoDTO();

		dto.setTitulo(sesionEntrenamiento.getTitulo());
		dto.setDistancia(sesionEntrenamiento.getDistancia());
		dto.setFechaInicio(sesionEntrenamiento.getFechaInicio());
		dto.setHoraInicio(sesionEntrenamiento.getHoraInicio());
		dto.setDuracion(sesionEntrenamiento.getDuracion());

		return dto;
	}
}
