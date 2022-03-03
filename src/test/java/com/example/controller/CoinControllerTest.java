package com.example.controller;

import com.example.model.Coin;
import com.example.service.CoinService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class CoinControllerTest {

    @Autowired
    private CoinController coinController;

    @Autowired
    private CoinService coinService;


    @Order(0)
    @Test
    void test_loadData() throws ParseException, JsonProcessingException {
        System.out.println("beforeClass");
        coinController.collect();
    }

    @Test
    @Order(1)
    void test1_queryCoin() throws JsonProcessingException {
        System.out.println("=====test1_queryCoin=====");
        String ename = "USD";
        List<Coin> coins = coinService.find(ename);
        assertNotEquals(0, coins.size());
        ObjectMapper mapper = new ObjectMapper();
        String out = mapper.writeValueAsString(coins);
        System.out.println(out);

    }

    @Test
    @Order(2)
    void test2_saveCoin(){
        System.out.println("=====test2_saveCoin=====");
        Coin coin = new Coin("TWD", 12345.678F, new Date());
        coinService.save(coin);
    }

    @Test
    @Order(3)
    void test3_updateCoin(){
        System.out.println("=====test3_updateCoin=====");
        Coin coin = coinService.find(1);
        coin.setUpdate_time(new Date());
        coinService.saveOrUpdateByEname(coin);
    }

    @Test
    @Order(4)
    void test4_deleteCoin(){
        System.out.println("=====test4_deleteCoin=====");
        coinService.delete(1);
    }

    @Test
    @Order(5)
    void test5_showCoindesk() throws JsonProcessingException {
        System.out.println("=====test5_showCoindesk=====");
        String coindesk = coinController.coindesk();
        System.out.println(coindesk);
    }

    @Test
    @Order(6)
    void test6_showCoin() throws JsonProcessingException {
        System.out.println("=====test6_showCoin=====");
        String coin = coinController.coin();
        System.out.println(coin);
    }
}