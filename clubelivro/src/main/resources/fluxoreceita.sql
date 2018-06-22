select 
	ano, 
	mes, 
	sum(previsto) as previsto, 
	sum(realizado) as realizado
from (
		select year(vcto) as ano, 
			month(vcto) as mes,
			sum(valor_nominal) as previsto,
			0 as realizado
		from clube_livro_boleto
		where vcto between :inicio and :fim
		group by year(vcto), month(vcto)
	union
		select year(pgto) as ano, 
			month(pgto) as mes, 
			0 as previsto,
			sum(valor_pago) as realizado
		from clube_livro_boleto
		where situacao in (1, 2) 
			and pgto between :inicio and :fim
		group by year(pgto), month(pgto)
	) as tabela
group by ano, mes
order by ano, mes