package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column
    public String ename;
    @Column
    public String cname;
    @Column
    public float rate;
    @Column
    public Date update_time;

    public Coin(String ename, float rate, Date update_time) {
        this.ename = ename;
        this.rate = rate;
        this.update_time = update_time;
    }
}
