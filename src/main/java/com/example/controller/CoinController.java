package com.example.controller;

import com.example.model.Coin;
import com.example.service.CoinService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/coin/api/v1")
@RestController
public class CoinController {

    @Autowired
    private CoinService coinService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("coindesk")
    public String coindesk() throws JsonProcessingException {
        String URL = "https://api.coindesk.com/v1/bpi/currentprice.json";
        String out = restTemplate.getForObject(URL, String.class);
//        ObjectMapper mapper = new ObjectMapper();
//        out = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(out));
        return out;
    }

    @GetMapping("/collectData")
    public String collect() throws ParseException, JsonProcessingException {
        String input = coindesk();
        JSONObject json = new JSONObject(input);
        //時間格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy HH:mm:ss Z", Locale.US);

        String str_date =  json.getJSONObject("time").getString("updated");
        Date update_date = dateFormat.parse(str_date);

        JSONObject bpi = json.getJSONObject("bpi");
        System.out.println(bpi);
        Iterator<String> keys = bpi.keys();
        while(keys.hasNext()){
            String key = keys.next();

            JSONObject coinData = bpi.getJSONObject(key);
            String code = coinData.getString("code");
            float rate = Float.parseFloat(coinData.getString("rate_float"));
//            float rate = coinData.getFloat("rate_float");

            Coin coin = new Coin(code, rate, update_date);
            coinService.saveOrUpdateByEname(coin);
        }


        return "讀取/存入結束";
    }



    @GetMapping("/coin")
    public String coin() throws JsonProcessingException {
        List<Coin> coins = coinService.find();
        ObjectMapper mapper = new ObjectMapper();
        String out = mapper.writeValueAsString(coins);
        return out;
    }

    @GetMapping("/coin/{ename}")
    public String coin(@PathVariable String ename){
        List<Coin> coins = coinService.find(ename);
        JSONArray array = new JSONArray(coins);
        return array.toString();
    }

    @PutMapping("/updateTime")
    public void updateTimeByEname(@RequestBody Map map){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        String ename = (String) map.get("ename");
        String datetime = (String) map.get("datetime");
        if(ename.equals("") || datetime.equals("")){
            return;
        }

        List<Coin> coins = coinService.find(ename);
        if(coins.size()>0){
            coins.forEach((coin)->{
                try {
                    coin.setUpdate_time(simpleDateFormat.parse(datetime));
                    coinService.saveOrUpdateByEname(coin);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });

        }
    }

    @PutMapping("/updateCname")
    public void updateCnameByEname(@RequestBody Map map){

        String ename = (String) map.get("ename");
        String cname = (String) map.get("cname");
        if(ename.equals("") || cname.equals("")){
            return;
        }

        List<Coin> coins = coinService.find(ename);
        if(coins.size()>0){
            coins.forEach((coin)->{
                coin.setCname(cname);
                coinService.saveOrUpdateByEname(coin);
            });

        }
    }
}
