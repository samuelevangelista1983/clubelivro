package crvnluz.boletos.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crvnluz.boletos.entidade.BoletoPendente;

@Repository
public interface BoletoPendenteRepositorio extends JpaRepository<BoletoPendente, Long> {
	
}
