package iium.jjs.sansang_back.member.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Column(name = "MEMBER_ZIPCODE")
    private String zipCode;

    @Column(name = "MEMBER_ADDRESS")
    private String address;

    @Column(name = "MEMBER_ADDRESS_DETAIL")
    private String addressDetail;


}
