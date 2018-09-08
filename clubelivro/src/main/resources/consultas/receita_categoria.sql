select 
	c.nome,
	sum(valor_pago) as recebido
from clube_livro_boleto b
	inner join clube_livro_integrante i on i.id = b.id_sacado
    inner join clube_livro_classificacao c on c.id = i.id_classificacao
where pgto < :datalimite
group by c.nome