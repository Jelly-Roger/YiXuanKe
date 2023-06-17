package com.easy_select_course.springboot.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@Setter
@Getter
public class InfoStructure {

    @JsonProperty("infoId")
    private int infoId;

    @JsonProperty("infoTar")
    private String infoTar;

    @JsonProperty("infoTarId")
    private String infoTarId;

    @JsonProperty("infoDate")
    private String infoDate;

    @JsonProperty("infoTitle")
    private String infoTitle;

    @JsonProperty("infoContent")
    private String infoContent;

    @JsonProperty("readStatus")
    private String readStatus;
}
