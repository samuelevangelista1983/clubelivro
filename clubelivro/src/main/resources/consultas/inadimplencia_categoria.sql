select 
	c.nome,
	sum(valor_nominal) as valor
from clube_livro_boleto b
	inner join clube_livro_integrante i on i.id = b.id_sacado
	inner join clube_livro_classificacao c on c.id = i.id_classificacao
where vcto < :datalimite
	and situacao = 0
group by c.nome