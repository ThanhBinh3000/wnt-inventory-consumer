package vn.com.gsoft.inventory.service;


import vn.com.gsoft.inventory.entity.PhieuXuats;
import vn.com.gsoft.inventory.model.dto.PhieuXuatsReq;

import java.util.Date;

public interface PhieuXuatsService extends BaseService<PhieuXuats, PhieuXuatsReq, Long> {


    PhieuXuats init(Long noteId, Integer taskMode, Date noteDate, Integer partnerId, Integer noteTypeId, Integer isConnectivity, Integer sampleNoteId, Integer isManagement) throws Exception;
}