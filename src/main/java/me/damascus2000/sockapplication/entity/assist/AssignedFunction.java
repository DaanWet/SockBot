package me.damascus2000.sockapplication.entity.assist;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AssignedFunction {

    @JsonProperty("Function")
    private Function function;
    @JsonProperty("Workgroup")
    private Workgroup workgroup;
    @JsonProperty("EmploymentStatus")
    private EmployementStatus employmentStatus;
    @JsonProperty("Id")
    private int id;
    @JsonProperty("PersonId")
    private int personId;
    @JsonProperty("WorkyearId")
    private Integer workyearId;
    @JsonProperty("FunctionId")
    private int functionId;
    @JsonProperty("WorkgroupId")
    private int workgroupId;
    @JsonProperty("EmploymentStatusId")
    private int employmentStatusId;
    @JsonProperty("StartDate")
    private String startDate;
    @JsonProperty("EndDate")
    private String endDate;
}
