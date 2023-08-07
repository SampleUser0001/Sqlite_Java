package ittimfn.sample.sqlite.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
@AllArgsConstructor
public class Model {
    private String id;
    private String value;
}
