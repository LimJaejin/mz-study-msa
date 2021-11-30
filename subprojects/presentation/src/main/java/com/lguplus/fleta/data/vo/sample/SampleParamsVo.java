package com.lguplus.fleta.data.vo.sample;

import com.lguplus.fleta.data.annotation.AlphabetAndNumberPattern;
import com.lguplus.fleta.data.dto.sample.SampleMemberDto;
import com.lguplus.fleta.data.dto.sample.SampleMemberMappDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SampleParamsVo implements Serializable {

    @ApiModelProperty(value = "가입자번호", example = "M14090300006", required = true, position = 1)
    @NotBlank(message = "SA_ID 는 빈값을 입력할 수 없습니다")
    @AlphabetAndNumberPattern
    @Length(min = 7, max = 12)
    private String SA_ID;

    @ApiModelProperty(value = "가입자 STB MAC Address", example = "9893.cc25.bc23", required = true, position = 2)
    @NotBlank(message = "STB_MAC 는 빈값을 입력할 수 없습니다")
    @AlphabetAndNumberPattern
    @Length(min = 14, max = 14)
    private String STB_MAC;

    @ApiModelProperty(value = "샘플 멤버 이름", example = "전강욱", position = 3)
    private String NAME;

    @ApiModelProperty(value = "샘플 멤버 이메일", example = "realsnake1975@gmail.com", position = 4)
    private String EMAIL;

    public SampleMemberDto toDto() {
        return new SampleMemberDto(this.NAME, this.EMAIL);
    }

    public SampleMemberMappDto toMappDto() {
        return new SampleMemberMappDto(this.SA_ID, this.STB_MAC);
    }

}
