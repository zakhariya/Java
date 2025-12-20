package ua.od.zakhariya.serialization.jackson.model;

import lombok.Data;

import java.util.Date;

@Data
public class Request
{
    private Car car;
    private Date datePurchased;

}
