package kr.taeu.jpaquerydsldemo.customer.dto;

import kr.taeu.jpaquerydsldemo.customer.domain.CustomerEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerResponse {
    private Long seq;
    private String name;
    private Integer age;

    @Builder
    public CustomerResponse(
            Long seq,
            String name,
            Integer age) {
        this.seq = seq;
        this.name = name;
        this.age = age;
    }

    public static CustomerResponse entityToDto (CustomerEntity entity) {
        return CustomerResponse.builder()
                .seq(entity.getSeq())
                .name(entity.getName())
                .age(entity.getAge())
                .build();
    }
}
