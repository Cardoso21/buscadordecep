package br.com.sortech.buscadordecep.servico;

import br.com.sortech.buscadordecep.dominio.Cep;
import br.com.sortech.buscadordecep.repositorio.CepRepositorio;
import br.com.sortech.buscadordecep.servico.DTO.CepDTO;
import br.com.sortech.buscadordecep.servico.excecao.ObjectnotFoundException;
import br.com.sortech.buscadordecep.servico.mapper.CepMapper;
import com.google.gson.Gson;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
    public CepDTO salvar (CepDTO cepDTO) throws Exception {
        //**Consumindo API externa
        URL url = new URL("https://viacep.com.br/ws/"+cepDTO.getCep()+"/json/");
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        String cep= "";
        StringBuilder jsonCep = new StringBuilder();
        while ((cep = br.readLine()) != null) {
            jsonCep.append(cep);
        }
        Cep cepAux = new Gson().fromJson(jsonCep.toString(),Cep.class);

        cepDTO.setCep(cepAux.getCep());
        cepDTO.setLogradouro(cepAux.getLogradouro());
        cepDTO.setComplemento(cepAux.getComplemento());
        cepDTO.setBairro(cepAux.getBairro());
        cepDTO.setLocalidade(cepAux.getLocalidade());
        cepDTO.setUf(cepAux.getUf());
        cepDTO.setIbge(cepAux.getIbge());
        cepDTO.setGia(cepAux.getGia());
        cepDTO.setDdd(cepAux.getDdd());
        cepDTO.setSiafi(cepAux.getSiafi());

        //**Consumindo API externa

        if(validarCep(cepDTO)){
            Cep ceps = cepMapper.toEntity(cepDTO);
            Cep cepSalvar = cepRepositorio.save(ceps);
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
