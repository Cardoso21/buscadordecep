package br.com.sortech.buscadordecep.servico;

import br.com.sortech.buscadordecep.dominio.Cep;
import br.com.sortech.buscadordecep.repositorio.CepRepositorio;
import br.com.sortech.buscadordecep.servico.DTO.CepDTO;
import br.com.sortech.buscadordecep.servico.excecao.ObjectnotFoundException;
import br.com.sortech.buscadordecep.servico.mapper.CepMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CepService implements Serializable {

    private final CepRepositorio cepRepositorio;
    private final CepMapper cepMapper;

    public CepDTO encontrarPorId(Long id){
        Cep cep = cepRepositorio.findById(id).orElseThrow(ObjectnotFoundException::new);
        return cepMapper.toDTO(cep);
    }
    public List<CepDTO> buscarTodos(){
        return cepMapper.toDTO(cepRepositorio.findAll());
    }
    public boolean validarCep(CepDTO cepDTO){
        cepRepositorio.existsByCep(cepDTO.getCep());
        return true;
    }
    public CepDTO salvar (CepDTO cepDTO){
        if(validarCep(cepDTO)){
            Cep cep = cepMapper.toEntity(cepDTO);
            Cep cepSalvar = cepRepositorio.save(cep);
            return cepMapper.toDTO(cepSalvar);
        }
        throw new ObjectnotFoundException("" +cepDTO.getCep());
    }
    public CepDTO editarCep(CepDTO cepDTO){
        Cep cep = cepMapper.toEntity(cepDTO);
        Cep cepAtualizar = cepRepositorio.save(cep);
        return cepMapper.toDTO(cepAtualizar);
    }
    public void deletar(Long id){
        cepRepositorio.deleteById(id);
    }

}
