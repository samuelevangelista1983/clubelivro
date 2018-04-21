select 
	ano, 
	mes, 
	sum(previsto) as previsto, 
	sum(realizado) as realizado
from (
		select year(vcto) as ano, 
			month(vcto) as mes,
			sum(valor_nominal) - (count(*) * (select custo from clube_livro_forma_pgto where id = 1)) as previsto,
			0 as realizado
		from clube_livro_boleto
		where situacao = 0 
			and vcto between :inicio and :fim
		group by year(vcto), month(vcto)
	union
		select year(vcto) as ano, 
			month(vcto) as mes, 
			0 as previsto,
			sum(valor_creditado) as realizado
		from clube_livro_boleto
		where situacao in (1, 2) 
			and vcto between :inicio and :fim
		group by year(vcto), month(vcto)
	) as tabela
group by ano, mes
order by ano, mes