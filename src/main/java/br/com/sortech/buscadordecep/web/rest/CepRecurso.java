package br.com.sortech.buscadordecep.web.rest;

import br.com.sortech.buscadordecep.servico.CepService;
import br.com.sortech.buscadordecep.servico.DTO.CepDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@CrossOrigin()
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/cep")
public class CepRecurso {

    private final CepService cepService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<CepDTO> buscarPorId(@PathVariable Long id) {
        CepDTO cepDTO = cepService.encontrarPorId(id);
        return ResponseEntity.ok(cepDTO);
    }
    @GetMapping
    public ResponseEntity <List<CepDTO>> buscarTodos(){
        List<CepDTO> cepList = cepService.buscarTodos();
        return ResponseEntity.ok(cepList);
    }
    @PostMapping
    public ResponseEntity<CepDTO> salvar(@RequestBody CepDTO cepDTO) throws Exception {


        return ResponseEntity.ok(cepService.salvar(cepDTO));
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<CepDTO> editar(@RequestBody CepDTO cepDTO,@PathVariable long id){
        cepDTO.setId(id);
        cepDTO=cepService.editarCep(cepDTO);
        return ResponseEntity.ok(cepDTO);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity <Void>deletar(@PathVariable Long id){
        cepService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
