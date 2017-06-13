package com.ucergy.ilocator.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by l_a_s on 04/06/2017.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserMovesReceived implements Serializable {

    private String mobileId;
    private String floor;
    private String region;
    private String category;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private int duration;

}
