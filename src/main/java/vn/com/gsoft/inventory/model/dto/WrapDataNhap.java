package vn.com.gsoft.inventory.model.dto;

import lombok.Data;
import vn.com.gsoft.inventory.entity.PhieuNhaps;

import java.util.Date;

@Data
public class WrapDataNhap {
    private String code;
    private Date sendDate;
    private PhieuNhaps data;
}
