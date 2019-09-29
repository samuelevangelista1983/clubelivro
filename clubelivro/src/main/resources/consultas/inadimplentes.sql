select
	integrante.nome as integrante,
	categoria.nome as categoria,
	count(*) as qtd_atraso
from boleto
	inner join integrante on integrante.id = id_integrante
	inner join categoria on categoria.id = integrante.id_categoria
where situacao = 0 
	and vcto < now()
	and (:idCategoria is null or categoria.id = :idCategoria)
group by integrante.nome, categoria.nome
order by qtd_atraso desc, integrante.nome