package ac.sw.mvcTest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private int id;
    private String name;
    private String email;
    private String pwd;
    private int age;
}
