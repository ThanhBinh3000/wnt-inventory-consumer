package vn.com.gsoft.inventory.service.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.inventory.constant.AppConstants;
import vn.com.gsoft.inventory.constant.ENoteType;
import vn.com.gsoft.inventory.constant.ESynStatus;
import vn.com.gsoft.inventory.constant.RecordStatusContains;
import vn.com.gsoft.inventory.entity.PhieuXuatChiTiets;
import vn.com.gsoft.inventory.entity.PhieuXuats;
import vn.com.gsoft.inventory.model.dto.PhieuXuatsReq;
import vn.com.gsoft.inventory.model.system.Profile;
import vn.com.gsoft.inventory.repository.PhieuXuatsRepository;
import vn.com.gsoft.inventory.service.ApplicationSettingService;
import vn.com.gsoft.inventory.service.PhieuXuatsService;
import vn.com.gsoft.inventory.service.ThuocsService;
import vn.com.gsoft.inventory.util.system.DataUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@Log4j2
public class PhieuXuatsServiceImpl extends BaseServiceImpl<PhieuXuats, PhieuXuatsReq, Long> implements PhieuXuatsService {
    private PhieuXuatsRepository hdrRepo;
    private ApplicationSettingService applicationSettingService;

    @Autowired
    public PhieuXuatsServiceImpl(PhieuXuatsRepository hdrRepo, ApplicationSettingService applicationSettingService) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
        this.applicationSettingService = applicationSettingService;
    }

    @Override
    public PhieuXuats init(Long id, Integer taskMode, Date noteDate, Integer partnerId, Integer noteTypeId, Integer isConnectivity, Integer sampleNoteId, Integer isManagement) throws Exception {
        String storeCode = getLoggedUser().getNhaThuoc().getMaNhaThuoc();
        Profile currUser = getLoggedUser();
        Map<String, Object> applicationSetting = applicationSettingService.getDrugStoreSetting(storeCode);
        PhieuXuats data = null;
        Boolean isConn = isConnectivity > 0;
        if (!isConn && isManagement == 0) {
            data = getNoteModel(storeCode, currUser, id, noteDate, partnerId, taskMode, noteTypeId, sampleNoteId, applicationSetting);
        }
        if (isConn) {
            data = getConnectivityNoteModel(storeCode, applicationSetting.get("STORE_CODE_FOR_CONNECTIVITY"), id, currUser);
        }
        if (isManagement > 0) {
            data = getManagementNoteModel(storeCode, applicationSetting.get("STORE_CODE_FOR_MANAGEMENT"), id, currUser);
        }
        if (data != null) {
            data.setIsConnectivity(isConn);
            data.setIsManagement(isManagement > 0);
        }

        return data;
    }

    private PhieuXuats getManagementNoteModel(String storeCode, Object storeCodeForManagement, Long id, Profile currUser) {
        PhieuXuats noteModel = new PhieuXuats();
        noteModel.setNhaThuocMaNhaThuoc(storeCode);
        Long soPhieuXuat = hdrRepo.findBySoPhieuXuatMax();
        if (soPhieuXuat == null) {
            soPhieuXuat = 1L;
        }
        noteModel.setSoPhieuXuat(soPhieuXuat);
        return noteModel;
    }

    private PhieuXuats getConnectivityNoteModel(String storeCode, Object storeCodeForConnectivity, Long id, Profile currUser) {
        PhieuXuats noteModel = new PhieuXuats();
        noteModel.setNhaThuocMaNhaThuoc(storeCode);
        Long soPhieuXuat = hdrRepo.findBySoPhieuXuatMax();
        if (soPhieuXuat == null) {
            soPhieuXuat = 1L;
        }
        noteModel.setSoPhieuXuat(soPhieuXuat);
        return noteModel;
    }

    private PhieuXuats getNoteModel(String storeCode, Profile currUser, Long id, Date noteDate, Integer partnerId, Integer taskMode, Integer noteTypeId, Integer sampleNoteId, Map<String, Object> applicationSetting) throws Exception {
        PhieuXuats noteModel = new PhieuXuats();
        noteModel.setNhaThuocMaNhaThuoc(storeCode);
        Long soPhieuXuat = hdrRepo.findBySoPhieuXuatMax();
        if (soPhieuXuat == null) {
            soPhieuXuat = 1L;
        }

        //Copy phiếu
        if (taskMode == 5) {
            if (id != null) {
                Optional<PhieuXuats> phieuXuats = hdrRepo.findById(id);
                if (phieuXuats.isPresent()) {
                    noteModel = phieuXuats.get();
                    noteModel.setId(null);
                    noteModel.setCreatedByUserId(null);
                    noteModel.setModifiedByUserId(null);
                    noteModel.setCreated(null);
                    noteModel.setModified(null);
                } else {
                    throw new Exception("Không tìm thấy phiếu copy!");
                }
            }

        }
        noteModel.setNgayXuat(new Date());
        noteModel.setSoPhieuXuat(soPhieuXuat);

        return noteModel;
    }

    @Override
    public PhieuXuats create(PhieuXuatsReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        boolean normalUser = true;
        Date noteDate = req.getNgayXuat().compareTo(AppConstants.MinProductionDataDate) < 0 ? new Date() : req.getNgayXuat();
        Map<String, Object> applicationSetting = applicationSettingService.getDrugStoreSetting(req.getMaNhaThuoc());
        if ((Boolean) applicationSetting.get("AUTO_LOCK_NOTES_AFTER_DAYS") && (Integer) applicationSetting.get("AUTO_LOCK_NOTES_AFTER_DAYS") >= 0 && normalUser) {
            LocalDateTime minNoteDate = LocalDateTime.now().minusDays((Integer) applicationSetting.get("AUTO_LOCK_NOTES_AFTER_DAYS"));
            var nextDate = LocalDateTime.now().plusDays(1);
            if ((noteDate.compareTo(DataUtils.toDate(minNoteDate)) < 0) || (noteDate.compareTo(DataUtils.toDate(nextDate)) >= 0)) {
                String msg = (noteDate.compareTo(DataUtils.toDate(nextDate)) >= 0) ? "Ngày trên phiếu không hợp lệ. Ngày trên phiếu không được lớn hơn " + DataUtils.toString(new Date()) :
                        "Ngày trên phiếu không hợp lệ. Ngày trên phiếu không được lớn hơn " + DataUtils.toString(DataUtils.toDate(minNoteDate));
                throw new Exception(msg);
            }
        }

        if (Objects.equals(req.getMaLoaiXuatNhap(), ENoteType.WarehouseTransfer) && req.getTargetStoreId() == null) {
            throw new Exception("TargetStoreId không được để trống");
        }
        if (req.getRecordStatusId() != null && req.getRecordStatusId() == RecordStatusContains.ARCHIVED) {
            throw new Exception("Không thể chỉnh sửa phiếu đã sao lưu.");
        }
        if (Objects.equals(req.getMaLoaiXuatNhap(), ENoteType.WarehouseTransfer) && !req.getChiTiets().isEmpty()) {
            // Kiểm tra sự trùng lặp
            Map<Object, Long> itemCounts = req.getChiTiets().stream()
                    .collect(Collectors.groupingBy(item -> Map.of(
                            "batchNumber", item.getBatchNumber(),
                            "expiredDate", item.getExpiredDate(),
                            "price", item.getGiaXuat(),
                            "selectedUnitId", item.getDonViTinhMaDonViTinh(),
                            "drugId", item.getThuocThuocId()
                    ), Collectors.counting()));

            boolean isDuplicate = itemCounts.values().stream().anyMatch(count -> count > 1);

            if (isDuplicate) {
                // Hợp nhất các mục trùng lặp
                List<PhieuXuatChiTiets> uniqueItems = req.getChiTiets().stream()
                        .collect(Collectors.collectingAndThen(Collectors.toMap(item -> Map.of(
                                "batchNumber", item.getBatchNumber(),
                                "expiredDate", item.getExpiredDate(),
                                "price", item.getGiaXuat(),
                                "drugId", item.getDonViTinhMaDonViTinh(),
                                "selectedUnitId", item.getThuocThuocId()
                        ), Function.identity(), (item1, item2) -> item1), map -> map.values().stream().collect(Collectors.toList())));

                req.setChiTiets(uniqueItems);
            }
        }

        req.setNgayXuat(new Date());
        PhieuXuats e = new PhieuXuats();
        BeanUtils.copyProperties(req, e, "id");
        if (e.getRecordStatusId() == null) {
            e.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        hdrRepo.save(e);
        return e;
    }

    @Override
    public PhieuXuats update(PhieuXuatsReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        Optional<PhieuXuats> optional = hdrRepo.findById(req.getId());
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }

        boolean normalUser = true;
        Date noteDate = req.getNgayXuat().compareTo(AppConstants.MinProductionDataDate) < 0 ? new Date() : req.getNgayXuat();
        Map<String, Object> applicationSetting = applicationSettingService.getDrugStoreSetting(req.getMaNhaThuoc());
        if ((Boolean) applicationSetting.get("AUTO_LOCK_NOTES_AFTER_DAYS") && (Integer) applicationSetting.get("AUTO_LOCK_NOTES_AFTER_DAYS") >= 0 && normalUser) {
            LocalDateTime minNoteDate = LocalDateTime.now().minusDays((Integer) applicationSetting.get("AUTO_LOCK_NOTES_AFTER_DAYS"));
            var nextDate = LocalDateTime.now().plusDays(1);
            if ((noteDate.compareTo(DataUtils.toDate(minNoteDate)) < 0) || (noteDate.compareTo(DataUtils.toDate(nextDate)) >= 0)) {
                String msg = (noteDate.compareTo(DataUtils.toDate(nextDate)) >= 0) ? "Ngày trên phiếu không hợp lệ. Ngày trên phiếu không được lớn hơn " + DataUtils.toString(new Date()) :
                        "Ngày trên phiếu không hợp lệ. Ngày trên phiếu không được lớn hơn " + DataUtils.toString(DataUtils.toDate(minNoteDate));
                throw new Exception(msg);
            }
        }

        if (Objects.equals(req.getMaLoaiXuatNhap(), ENoteType.WarehouseTransfer) && req.getTargetStoreId() == null) {
            throw new Exception("TargetStoreId không được để trống");
        }
        if (req.getRecordStatusId() != null && req.getRecordStatusId() == RecordStatusContains.ARCHIVED) {
            throw new Exception("Không thể chỉnh sửa phiếu đã sao lưu.");
        }
        if (Objects.equals(req.getMaLoaiXuatNhap(), ENoteType.WarehouseTransfer) && !req.getChiTiets().isEmpty()) {
            // Kiểm tra sự trùng lặp
            Map<Object, Long> itemCounts = req.getChiTiets().stream()
                    .collect(Collectors.groupingBy(item -> Map.of(
                            "batchNumber", item.getBatchNumber(),
                            "expiredDate", item.getExpiredDate(),
                            "price", item.getGiaXuat(),
                            "selectedUnitId", item.getDonViTinhMaDonViTinh(),
                            "drugId", item.getThuocThuocId()
                    ), Collectors.counting()));

            boolean isDuplicate = itemCounts.values().stream().anyMatch(count -> count > 1);

            if (isDuplicate) {
                // Hợp nhất các mục trùng lặp
                List<PhieuXuatChiTiets> uniqueItems = req.getChiTiets().stream()
                        .collect(Collectors.collectingAndThen(Collectors.toMap(item -> Map.of(
                                "batchNumber", item.getBatchNumber(),
                                "expiredDate", item.getExpiredDate(),
                                "price", item.getGiaXuat(),
                                "drugId", item.getDonViTinhMaDonViTinh(),
                                "selectedUnitId", item.getThuocThuocId()
                        ), Function.identity(), (item1, item2) -> item1), map -> map.values().stream().collect(Collectors.toList())));

                req.setChiTiets(uniqueItems);
            }
        }

        req.setNgayXuat(new Date());
        PhieuXuats e = new PhieuXuats();
        BeanUtils.copyProperties(req, e, "id", "created", "createdByUserId");
        if (e.getRecordStatusId() == null) {
            e.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        e.setModified(new Date());
        e.setModifiedByUserId(getLoggedUser().getId());
        hdrRepo.save(e);
        return e;
    }

}
