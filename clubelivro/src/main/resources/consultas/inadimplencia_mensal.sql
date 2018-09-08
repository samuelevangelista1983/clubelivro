select 
	year(vcto) as ano, 
	month(vcto) as mes,
	sum(valor_nominal) as valor
from clube_livro_boleto b
where vcto < :datalimite
	and situacao = 0
group by year(vcto), month(vcto)