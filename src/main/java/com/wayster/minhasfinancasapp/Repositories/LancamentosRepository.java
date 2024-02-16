package com.wayster.minhasfinancasapp.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wayster.minhasfinancasapp.Entity.Lancamentos;

public interface LancamentosRepository extends JpaRepository<Lancamentos, Long> {
    
}
