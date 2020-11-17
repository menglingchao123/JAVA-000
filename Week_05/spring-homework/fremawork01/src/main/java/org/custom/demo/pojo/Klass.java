package org.custom.demo.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class Klass {

    @Autowired
    List<Student> students;

    public void dong(){
        System.out.println(this.getStudents());
    }

}
