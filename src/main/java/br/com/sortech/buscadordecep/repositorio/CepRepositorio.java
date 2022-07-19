package br.com.sortech.buscadordecep.repositorio;

import br.com.sortech.buscadordecep.dominio.Cep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CepRepositorio extends JpaRepository<Cep, Long>, JpaSpecificationExecutor<Cep> {

    boolean existsByCep(String cep);
}
