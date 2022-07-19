package br.com.sortech.buscadordecep.servico.mapper;

import br.com.sortech.buscadordecep.dominio.Cep;
import br.com.sortech.buscadordecep.servico.DTO.CepDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CepMapper extends EntityMepper<CepDTO, Cep> {
}

