package com.example.repository;

import com.example.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoinRepository extends JpaRepository<Coin, Integer> {

    List<Coin> findByEname(String ename);
}
