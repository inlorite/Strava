package es.deusto.ingenieria.sd.strava.server.data.dto;

import java.util.ArrayList;
import java.util.List;

import es.deusto.ingenieria.sd.strava.server.data.domain.SesionEntrenamiento;

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
		dto.setDeporte(sesionEntrenamiento.getDeporte());
		dto.setMapImg(sesionEntrenamiento.getMapImg());

		return dto;
	}

	public SesionEntrenamiento dtoToSesionEntrenamiento(SesionEntrenamientoDTO dto) {
		SesionEntrenamiento sesionEntrenamiento = new SesionEntrenamiento();

		sesionEntrenamiento.setTitulo(dto.getTitulo());
		sesionEntrenamiento.setDistancia(dto.getDistancia());
		sesionEntrenamiento.setFechaInicio(dto.getFechaInicio());
		sesionEntrenamiento.setHoraInicio(dto.getHoraInicio());
		sesionEntrenamiento.setDuracion(dto.getDuracion());
		sesionEntrenamiento.setDeporte(dto.getDeporte());
		sesionEntrenamiento.setMapImg(dto.getMapImg());

		return sesionEntrenamiento;
	}

	public List<SesionEntrenamientoDTO> sesionEntrenamientoToDTO(List<SesionEntrenamiento> sesionesEntrenamiento) {
		List<SesionEntrenamientoDTO> dtos = new ArrayList<>();

		for (SesionEntrenamiento s : sesionesEntrenamiento) {

			dtos.add(this.sesionEntrenamientoToDTO(s));
		}

		return dtos;
	}

	public List<SesionEntrenamiento> dtoToSesionEntrenamiento(List<SesionEntrenamientoDTO> sesionesEntrenamientoDTO) {
		List<SesionEntrenamiento> sesiones = new ArrayList<>();

		for (SesionEntrenamientoDTO dto : sesionesEntrenamientoDTO) {

			sesiones.add(this.dtoToSesionEntrenamiento(dto));
		}

		return sesiones;
	}
}
