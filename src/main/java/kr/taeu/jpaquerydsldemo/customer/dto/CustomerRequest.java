package kr.taeu.jpaquerydsldemo.customer.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerRequest {
    private String name;
    private Integer age;

    @Builder
    public CustomerRequest(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
