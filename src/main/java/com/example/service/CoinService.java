package com.example.service;

import com.example.model.Coin;
import com.example.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoinService {

    @Autowired
    private CoinRepository coinRepository;

    //新增 修改
    public void save(Coin coin){
        coinRepository.save(coin);
    }
    public void saveOrUpdateByEname(Coin coin){
        String ename = coin.getEname();
        List<Coin> list = coinRepository.findByEname(ename);
        if(list.size()>0){
            coin.setId(list.get(0).getId());
        }
        coinRepository.save(coin);
    }

    //刪除
    public void delete(Coin coin){
        coinRepository.delete(coin);
    }
    public void delete(int id){
        coinRepository.deleteById(id);
    }


    //查詢
    public List<Coin> find(){
        List<Coin> all = coinRepository.findAll();
        return all;
    }
    public Coin find(int id){
        Optional<Coin> coin = coinRepository.findById(id);
        if(coin.isPresent()){
            return coin.get();
        }
        return null;
    }
    public List<Coin> find(String ename){
        List<Coin> coins = coinRepository.findByEname(ename);
        return coins;
    }

}
