package com.projeto.desafiourbana.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.desafiourbana.domain.Cartao;
import com.projeto.desafiourbana.repositories.cartao.CartaoRepositoryQuery;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long>, CartaoRepositoryQuery {

}
