package es.deusto.ingenieria.sd.strava.server.data.dto;

import java.util.ArrayList;
import java.util.List;

import es.deusto.ingenieria.sd.strava.server.data.domain.Reto;

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
		dto.setCreador(reto.getCreador());
		dto.setParticipantes(reto.getParticipantes());
		dto.setTipoReto(reto.getTipoReto());

		return dto;
	}
	
	public Reto dtoToReto (RetoDTO dto) {
		Reto reto = new Reto();

		reto.setDistancia(dto.getDistancia());
		reto.setFechaFin(dto.getFechaFin());
		reto.setFechaInicio(dto.getFechaInicio());
		reto.setNombre(dto.getNombre());
		reto.setTiempo(dto.getTiempo());
		reto.setCreador(dto.getCreador());
		reto.setParticipantes(dto.getParticipantes());
		reto.setTipoReto(dto.getTipoReto());

		return reto;
	}
	
	public List<RetoDTO> retoToDTO(List<Reto> retos) {
		List<RetoDTO> retosDTO = new ArrayList<>();
		
		for (Reto reto : retos) {
			
			retosDTO.add(this.retoToDTO(reto));
		}

		return retosDTO;
	}
	
	public List<Reto> dtoToReto(List<RetoDTO> retosDTO) {
		List<Reto> retos = new ArrayList<>();
		
		for (RetoDTO retoDTO : retosDTO) {
			
			retos.add(this.dtoToReto(retoDTO));
		}

		return retos;
	}
}