 select
	count(*) - sum(case when ativo = 0 then 1 else 0 end) as ativos,
	sum(case when ativo = 0 then 1 else 0 end) as inativos,
	sum(case when c.id = 1 then 1 else 0 end) as estudo,
	sum(case when c.id = 2 then 1 else 0 end) as romance,
	sum(case when c.id = 3 then 1 else 0 end) as estudo_romance,
	sum(case when c.id = 4 then 1 else 0 end) as estudo_romance_alternado,
	sum(case when e.id = 1 then 1 else 0 end) as correios,
	sum(case when e.id = 2 then 1 else 0 end) as presencial,
	sum(case when f.id = 1 then 1 else 0 end) as mensal,
	sum(case when f.id = 2 then 1 else 0 end) as bimestral,
	sum(case when p.id = 1 then 1 else 0 end) as boleto,
	sum(case when p.id = 2 then 1 else 0 end) as cartao_debito,
	sum(case when p.id = 3 then 1 else 0 end) as cartao_credito,
	sum(case when p.id = 4 then 1 else 0 end) as dinheiro,
	sum(case when p.id = 5 then 1 else 0 end) as cheque
from clube_livro_integrante i
	inner join clube_livro_classificacao c on c.id = i.id_classificacao
	inner join clube_livro_entrega e on e.id = i.id_entrega
	inner join clube_livro_forma_pgto p on p.id = i.id_forma_pgto_pref
	inner join clube_livro_frequencia f on f.id = i.id_frequencia