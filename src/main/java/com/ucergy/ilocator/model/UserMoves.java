package com.ucergy.ilocator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Date;
import java.util.UUID;

/**
 * Created by l_a_s on 01/06/2017.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
public class UserMoves {

    @PrimaryKey
    private UUID id;
    private String mobileId;
    private String floor;
    private String region;
    private String category;
    private Date date;
    private int duration;


}
