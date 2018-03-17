package br.org.crvnluz.editora.clubelivro.integrante;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.eti.sen.utilitarios.tempo.DataUtil;
import br.org.crvnluz.editora.clubelivro.configuracao.classificacao.Classificacao;
import br.org.crvnluz.editora.clubelivro.configuracao.entrega.FormaEntrega;
import br.org.crvnluz.editora.clubelivro.configuracao.formapgto.FormaPgto;
import br.org.crvnluz.editora.clubelivro.configuracao.frequencia.Frequencia;
import br.org.crvnluz.editora.clubelivro.integrante.documento.Documento;
import br.org.crvnluz.editora.clubelivro.integrante.endereco.Endereco;
import br.org.crvnluz.editora.clubelivro.integrante.pessoa.Pessoa;

public class IntegranteMapper implements RowMapper<Integrante> {

	@Override
	public Integrante mapRow(ResultSet rs, int rowNum) throws SQLException {
		Integrante integrante = new Integrante();
		integrante.setId(rs.getLong("id"));
		Pessoa pessoa = new Pessoa(rs.getLong("id_pessoa"));
		pessoa.setNome(rs.getString("nome"));
		String nascimento = rs.getString("nascimento");
		pessoa.setNascimento(nascimento != null ? DataUtil.parserData(nascimento, "yyyy-MM-dd") : null);
		integrante.setPessoa(pessoa);
		integrante.setDocumento(new Documento(rs.getLong("id_documento")));
		integrante.setEnderecoCobranca(new Endereco(rs.getLong("id_endereco_cobranca")));
		integrante.setEnderecoEntrega(new Endereco(rs.getLong("id_endereco_entrega")));
		integrante.setFormaEntrega(new FormaEntrega(rs.getLong("id_entrega")));
		Classificacao classificacao = new Classificacao();
		classificacao.setId(rs.getLong("id_classificacao"));
		classificacao.setNome(rs.getString("c.nome"));
		integrante.setClassificacao(classificacao);
		integrante.setFrequencia(new Frequencia(rs.getLong("id_frequencia")));
		integrante.setFormaPgtoPref(new FormaPgto(rs.getLong("id_forma_pgto_pref")));
		integrante.setDiaPgtoPref(rs.getInt("dia_pgto_pref"));
		integrante.setAtivo(rs.getBoolean("ativo"));
		String dtInativo = rs.getString("dt_inativo");
		integrante.setDtDesativacao(dtInativo != null ? DataUtil.parserData(dtInativo, "yyyy-MM-dd") : null);
		String dtCadastro = rs.getString("dt_cadastro");
		integrante.setDtCadastro(dtCadastro != null ? DataUtil.parserData(dtCadastro, "yyyy-MM-dd") : null);
		return integrante;
	}

}
