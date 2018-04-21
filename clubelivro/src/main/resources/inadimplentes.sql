select
	p.nome as pessoa,
	c.nome as categoria,
	count(*) as qtd_atraso
from clube_livro_integrante i
	inner join pessoa p on p.id = id_pessoa
	inner join clube_livro_boleto b on i.id = id_sacado
	inner join clube_livro_classificacao c on c.id = id_classificacao
where situacao = 0 and vcto < now()
	and (:idClassificacao is null or c.id = :idClassificacao)
group by id_sacado
order by qtd_atraso desc, p.nome