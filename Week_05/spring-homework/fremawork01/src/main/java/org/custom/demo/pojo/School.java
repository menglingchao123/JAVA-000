package org.custom.demo.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class School {

    @Autowired
    Klass kClass;

}
