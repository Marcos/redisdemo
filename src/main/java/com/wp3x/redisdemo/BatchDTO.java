package com.wp3x.redisdemo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Created by marcos.ferreira on 18.08.17.
 */
@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor
public class BatchDTO {

    private List<String> keys;

}
