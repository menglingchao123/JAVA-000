package org.demo.starter.pojo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Klass {

    private List<Student> students;
    
    public void dong(){
        System.out.println(this.getStudents());
    }
    
}
