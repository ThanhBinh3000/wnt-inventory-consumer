package vn.com.gsoft.inventory.model.dto;

import lombok.Data;
import vn.com.gsoft.inventory.entity.PhieuXuats;

import java.util.Date;

@Data
public class WrapDataXuat {
    private String code;
    private Date sendDate;
    private PhieuXuats data;
}
