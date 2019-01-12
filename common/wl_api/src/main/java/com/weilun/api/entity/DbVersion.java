package com.weilun.api.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @author LiangYong
 * 2019/1/6
 * 10:54
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DbVersion implements Serializable {
    private Integer id;
    private Integer version;
}
