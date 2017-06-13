package com.ucergy.ilocator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * Created by l_a_s on 02/06/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Place {

    @PrimaryKey
    String name;
    int floor;
    String category;
    float entryPointX;
    float entryPointY;
    int importantVisit;

}
