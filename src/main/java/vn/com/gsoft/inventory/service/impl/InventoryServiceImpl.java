package vn.com.gsoft.inventory.service.impl;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.inventory.constant.AppConstants;
import vn.com.gsoft.inventory.constant.RecordStatusContains;
import vn.com.gsoft.inventory.entity.*;
import vn.com.gsoft.inventory.model.dto.WrapDataNhap;
import vn.com.gsoft.inventory.model.dto.WrapDataXuat;
import vn.com.gsoft.inventory.repository.*;
import vn.com.gsoft.inventory.service.InventoryService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private NhaThuocsRepository nhaThuocsRepository;
    @Autowired
    private ThuocsRepository thuocsRepository;
    @Autowired
    private PhieuXuatChiTietsRepository phieuXuatChiTietsRepository;
    @Autowired
    private PhieuNhapChiTietsRepository phieuNhapChiTietsRepository;
    @Autowired
    private PhieuXuatsRepository phieuXuatsRepository;
    @Autowired
    private PhieuNhapsRepository phieuNhapsRepository;

    @Override
    public void xuat(String wrapData) {
        Gson gson = new Gson();
        WrapDataXuat dataXuat = gson.fromJson(wrapData, WrapDataXuat.class);
        PhieuXuats phieuXuats = dataXuat.getData();
        Optional<Inventory> inventory = inventoryRepository.findByDrugStoreIdAndDrugIdAndRecordStatusId(phieuXuats.getNhaThuocMaNhaThuoc(), phieuXuats.getChiTiets().get(0).getThuocThuocId(), RecordStatusContains.ACTIVE);
        if (phieuXuats.getChiTiets().get(0).getThuocThuocId() == null) {
            log.error("thuốc Id is null! trong phiếu {} của nhà thuốc {}", phieuXuats.getSoPhieuXuat(), phieuXuats.getNhaThuocMaNhaThuoc());
            return;
        }
        Optional<Thuocs> thuocs = thuocsRepository.findById(phieuXuats.getChiTiets().get(0).getThuocThuocId());
        if (thuocs.isEmpty()) {
            log.error("Không tìm thấy thuốc Id {} trong phiếu {} của nhà thuốc {}", phieuXuats.getChiTiets().get(0).getThuocThuocId(), phieuXuats.getSoPhieuXuat(), phieuXuats.getNhaThuocMaNhaThuoc());
            return;
        }
        if (inventory.isEmpty()) {
            Inventory inv = new Inventory();
            // lấy thông tin ban đầu
            Optional<NhaThuocs> nhaThuocs = nhaThuocsRepository.findByMaNhaThuoc(phieuXuats.getNhaThuocMaNhaThuoc());
            if (nhaThuocs.isEmpty()) {
                log.error("Không tìm thấy mã nhà thuốc {} trong phiếu {}", phieuXuats.getNhaThuocMaNhaThuoc(), phieuXuats.getSoPhieuXuat());
                return;
            }
            boolean isChild = !StringUtils.isEmpty(nhaThuocs.get().getMaNhaThuocCha());
            inv.setDrugId(thuocs.get().getId());
            inv.setLastValue(isChild ? 0f : thuocs.get().getSoDuDauKy());  // khởi tạo lần đầu
            inv.setDrugUnitId(thuocs.get().getDonViXuatLeMaDonViTinh());
            inv.setLastInPrice(thuocs.get().getGiaDauKy() > AppConstants.EspPrice ? thuocs.get().getGiaDauKy() : ((thuocs.get().getGiaNhap() >= 0 ? thuocs.get().getGiaNhap() : thuocs.get().getGiaDauKy()))); // i.Value.LastReceiptRetailPrice > AppConstants.EspPrice ? i.Value.LastReceiptRetailPrice : i.Value.InPrice,
            inv.setLastOutPrice(phieuXuats.getChiTiets().get(0).getRetailPrice() * (1 - phieuXuats.getDiscount() / 100));
            inv.setRecordStatusId(RecordStatusContains.ACTIVE);
            inv.setSerialNumber(thuocs.get().getSerialNumber());
            // lấy phiếu nhập thuốc đe lay han su dung

            inv.setExpiredDate(new Date()); // todo i.Value.ExpiredDate, phần hạn xử dụng đang có issue là không thể xử lý được nhiều lần nhập thuốc đang chỉ lấy theo lần nhập gần nhất
            inv.setLastUpdated(new Date());
            inv.setHasTransactions(false);
            inv.setDeliveryItemCount(0l);
            inv.setReceiptItemCount(0l);
            inv.setInitValue(isChild ? 0f : thuocs.get().getSoDuDauKy());
            inv.setInitInPrice(thuocs.get().getGiaDauKy());
            inv.setOutPrice(thuocs.get().getGiaBanLe());
            inventory = Optional.of(inv);
        }
        // Math.Round(InitReceiptQuantity + LastReceiptQuantity - LastDeliveryQuantity, 1)
//        InitReceiptQuantity khoi tao
//        LastReceiptQuantity tinh toan phieu nhan
//        LastDeliveryQuantity tinh toan phieu xuat
        Double lastDeliveryQuantity = phieuXuatChiTietsRepository.sumByNhaThuocMaNhaThuocAndThuocThuocIdAndRecordStatusId(phieuXuats.getNhaThuocMaNhaThuoc(), phieuXuats.getChiTiets().get(0).getThuocThuocId(), thuocs.get().getDonViXuatLeMaDonViTinh(), RecordStatusContains.ACTIVE);
        if (lastDeliveryQuantity == null) {
            lastDeliveryQuantity = 0d;
        }
        if (thuocs.get().getDonViThuNguyenMaDonViTinh() != null && thuocs.get().getDonViThuNguyenMaDonViTinh() > 0) {
            Double lastDeliveryQuantity2 = phieuXuatChiTietsRepository.sumByNhaThuocMaNhaThuocAndThuocThuocIdAndRecordStatusId(phieuXuats.getNhaThuocMaNhaThuoc(), phieuXuats.getChiTiets().get(0).getThuocThuocId(), thuocs.get().getDonViThuNguyenMaDonViTinh(), RecordStatusContains.ACTIVE);
            if (lastDeliveryQuantity2 == null) {
                lastDeliveryQuantity2 = 0d;
            }
            lastDeliveryQuantity = lastDeliveryQuantity + (lastDeliveryQuantity2 * thuocs.get().getHeSo());
        }
        Double lastReceiptQuantity = phieuNhapChiTietsRepository.sumByNhaThuocMaNhaThuocAndThuocThuocIdAndRecordStatusId(phieuXuats.getNhaThuocMaNhaThuoc(), phieuXuats.getChiTiets().get(0).getThuocThuocId(), thuocs.get().getDonViXuatLeMaDonViTinh(), RecordStatusContains.ACTIVE);
        if (lastReceiptQuantity == null) {
            lastReceiptQuantity = 0d;
        }
        if (thuocs.get().getDonViThuNguyenMaDonViTinh() != null && thuocs.get().getDonViThuNguyenMaDonViTinh() > 0) {
            Double lastReceiptQuantity2 = phieuNhapChiTietsRepository.sumByNhaThuocMaNhaThuocAndThuocThuocIdAndRecordStatusId(phieuXuats.getNhaThuocMaNhaThuoc(), phieuXuats.getChiTiets().get(0).getThuocThuocId(), thuocs.get().getDonViThuNguyenMaDonViTinh(), RecordStatusContains.ACTIVE);
            if (lastReceiptQuantity2 == null) {
                lastReceiptQuantity2 = 0d;
            }
            lastReceiptQuantity = lastReceiptQuantity + (lastReceiptQuantity2 * thuocs.get().getHeSo());
        }
        double result = inventory.get().getInitValue() + lastReceiptQuantity - lastDeliveryQuantity;
        double lastValueResult = Math.round(result * 10.0) / 10.0;
        inventory.get().setLastValue(lastValueResult);

        inventory.get().setLastUpdated(new Date());
        // count phieu xuat
        Long deliveryItemCount = phieuXuatChiTietsRepository.countByNhaThuocMaNhaThuocAndThuocThuocIdAndRecordStatusId(phieuXuats.getNhaThuocMaNhaThuoc(), phieuXuats.getChiTiets().get(0).getThuocThuocId(), RecordStatusContains.ACTIVE);
        inventory.get().setDeliveryItemCount(deliveryItemCount);
        // count phieu nhap
        Long receiptItemCount = phieuNhapChiTietsRepository.countByNhaThuocMaNhaThuocAndThuocThuocIdAndRecordStatusId(phieuXuats.getNhaThuocMaNhaThuoc(), phieuXuats.getChiTiets().get(0).getThuocThuocId(), RecordStatusContains.ACTIVE);
        inventory.get().setReceiptItemCount(receiptItemCount);
        inventory.get().setHasTransactions((inventory.get().getDeliveryItemCount() + inventory.get().getReceiptItemCount()) > 0);
        List<PhieuXuatChiTiets> phieuXuatChiTiets = phieuXuatChiTietsRepository.findByNhaThuocMaNhaThuocAndThuocThuocIdAndRecordStatusIdAndMaxNgayXuat(phieuXuats.getNhaThuocMaNhaThuoc(), phieuXuats.getChiTiets().get(0).getThuocThuocId(), RecordStatusContains.ACTIVE);
        if (!phieuXuatChiTiets.isEmpty()) {
            Optional<PhieuXuats> phieuXuatNs = phieuXuatsRepository.findById(phieuXuatChiTiets.get(0).getPhieuXuatMaPhieuXuat());
            if(phieuXuatNs.isPresent()){
                if (thuocs.get().getDonViThuNguyenMaDonViTinh() != null && thuocs.get().getDonViThuNguyenMaDonViTinh() > 0 && thuocs.get().getDonViThuNguyenMaDonViTinh().equals(phieuXuatChiTiets.get(0).getDonViTinhMaDonViTinh())) {
                    inventory.get().setLastOutPrice(((double) (phieuXuatChiTiets.get(0).getRetailPrice() /  thuocs.get().getDonViThuNguyenMaDonViTinh())) * (1 - phieuXuatNs.get().getDiscount() / 100));
                }else {
                    inventory.get().setLastOutPrice(phieuXuatChiTiets.get(0).getRetailPrice() * (1 - phieuXuatNs.get().getDiscount() / 100));
                }
            }
        }

        inventoryRepository.save(inventory.get());
    }

    @Override
    public void nhap(String wrapData) {
        Gson gson = new Gson();
        WrapDataNhap dataNhap = gson.fromJson(wrapData, WrapDataNhap.class);
        PhieuNhaps phieuNhaps = dataNhap.getData();
        Optional<Inventory> inventory = inventoryRepository.findByDrugStoreIdAndDrugIdAndRecordStatusId(phieuNhaps.getNhaThuocMaNhaThuoc(), phieuNhaps.getChiTiets().get(0).getThuocThuocId(), RecordStatusContains.ACTIVE);
        if (phieuNhaps.getChiTiets().get(0).getThuocThuocId() == null) {
            log.error("thuốc Id is null! trong phiếu {} của nhà thuốc {}", phieuNhaps.getSoPhieuNhap(), phieuNhaps.getNhaThuocMaNhaThuoc());
            return;
        }
        Optional<Thuocs> thuocs = thuocsRepository.findById(phieuNhaps.getChiTiets().get(0).getThuocThuocId());
        if (thuocs.isEmpty()) {
            log.error("Không tìm thấy thuốc Id {} trong phiếu {} của nhà thuốc {}", phieuNhaps.getChiTiets().get(0).getThuocThuocId(), phieuNhaps.getSoPhieuNhap(), phieuNhaps.getNhaThuocMaNhaThuoc());
            return;
        }
        if (inventory.isEmpty()) {
            Inventory inv = new Inventory();
            // lấy thông tin ban đầu
            Optional<NhaThuocs> nhaThuocs = nhaThuocsRepository.findByMaNhaThuoc(phieuNhaps.getNhaThuocMaNhaThuoc());
            if (nhaThuocs.isEmpty()) {
                log.error("Không tìm thấy mã nhà thuốc {} trong phiếu {}", phieuNhaps.getNhaThuocMaNhaThuoc(), phieuNhaps.getSoPhieuNhap());
                return;
            }
            boolean isChild = !StringUtils.isEmpty(nhaThuocs.get().getMaNhaThuocCha());
            inv.setDrugId(thuocs.get().getId());
            inv.setLastValue(isChild ? 0f : thuocs.get().getSoDuDauKy());  // khởi tạo lần đầu
            inv.setDrugUnitId(thuocs.get().getDonViXuatLeMaDonViTinh());
            inv.setLastInPrice(thuocs.get().getGiaDauKy() > AppConstants.EspPrice ? thuocs.get().getGiaDauKy() : ((thuocs.get().getGiaNhap() >= 0 ? thuocs.get().getGiaNhap() : thuocs.get().getGiaDauKy()))); // i.Value.LastReceiptRetailPrice > AppConstants.EspPrice ? i.Value.LastReceiptRetailPrice : i.Value.InPrice,
            inv.setLastOutPrice(phieuNhaps.getChiTiets().get(0).getRetailPrice() * (1 - phieuNhaps.getDiscount() / 100));
            inv.setRecordStatusId(RecordStatusContains.ACTIVE);
            inv.setSerialNumber(thuocs.get().getSerialNumber());
            // lấy phiếu nhập thuốc đe lay han su dung

            inv.setExpiredDate(new Date()); // todo i.Value.ExpiredDate, phần hạn xử dụng đang có issue là không thể xử lý được nhiều lần nhập thuốc đang chỉ lấy theo lần nhập gần nhất
            inv.setLastUpdated(new Date());
            inv.setHasTransactions(false);
            inv.setDeliveryItemCount(0l);
            inv.setReceiptItemCount(0l);
            inv.setInitValue(isChild ? 0f : thuocs.get().getSoDuDauKy());
            inv.setInitInPrice(thuocs.get().getGiaDauKy());
            inv.setOutPrice(thuocs.get().getGiaBanLe());
            inventory = Optional.of(inv);
        }
        // Math.Round(InitReceiptQuantity + LastReceiptQuantity - LastDeliveryQuantity, 1)
//        InitReceiptQuantity khoi tao
//        LastReceiptQuantity tinh toan phieu nhan
//        LastDeliveryQuantity tinh toan phieu xuat
        Double lastDeliveryQuantity = phieuXuatChiTietsRepository.sumByNhaThuocMaNhaThuocAndThuocThuocIdAndRecordStatusId(phieuNhaps.getNhaThuocMaNhaThuoc(), phieuNhaps.getChiTiets().get(0).getThuocThuocId(), thuocs.get().getDonViXuatLeMaDonViTinh(), RecordStatusContains.ACTIVE);
        if (lastDeliveryQuantity == null) {
            lastDeliveryQuantity = 0d;
        }
        if (thuocs.get().getDonViThuNguyenMaDonViTinh() != null && thuocs.get().getDonViThuNguyenMaDonViTinh() > 0) {
            Double lastDeliveryQuantity2 = phieuXuatChiTietsRepository.sumByNhaThuocMaNhaThuocAndThuocThuocIdAndRecordStatusId(phieuNhaps.getNhaThuocMaNhaThuoc(), phieuNhaps.getChiTiets().get(0).getThuocThuocId(), thuocs.get().getDonViThuNguyenMaDonViTinh(), RecordStatusContains.ACTIVE);
            if (lastDeliveryQuantity2 == null) {
                lastDeliveryQuantity2 = 0d;
            }
            lastDeliveryQuantity = lastDeliveryQuantity + (lastDeliveryQuantity2 * thuocs.get().getHeSo());
        }
        Double lastReceiptQuantity = phieuNhapChiTietsRepository.sumByNhaThuocMaNhaThuocAndThuocThuocIdAndRecordStatusId(phieuNhaps.getNhaThuocMaNhaThuoc(), phieuNhaps.getChiTiets().get(0).getThuocThuocId(), thuocs.get().getDonViXuatLeMaDonViTinh(), RecordStatusContains.ACTIVE);
        if (lastReceiptQuantity == null) {
            lastReceiptQuantity = 0d;
        }
        if (thuocs.get().getDonViThuNguyenMaDonViTinh() != null && thuocs.get().getDonViThuNguyenMaDonViTinh() > 0) {
            Double lastReceiptQuantity2 = phieuNhapChiTietsRepository.sumByNhaThuocMaNhaThuocAndThuocThuocIdAndRecordStatusId(phieuNhaps.getNhaThuocMaNhaThuoc(), phieuNhaps.getChiTiets().get(0).getThuocThuocId(), thuocs.get().getDonViThuNguyenMaDonViTinh(), RecordStatusContains.ACTIVE);
            if (lastReceiptQuantity2 == null) {
                lastReceiptQuantity2 = 0d;
            }
            lastReceiptQuantity = lastReceiptQuantity + (lastReceiptQuantity2 * thuocs.get().getHeSo());
        }
        double result = inventory.get().getInitValue() + lastReceiptQuantity - lastDeliveryQuantity;
        double lastValueResult = Math.round(result * 10.0) / 10.0;
        inventory.get().setLastValue(lastValueResult);
        inventory.get().setLastUpdated(new Date());
        // count phieu xuat
        Long deliveryItemCount = phieuXuatChiTietsRepository.countByNhaThuocMaNhaThuocAndThuocThuocIdAndRecordStatusId(phieuNhaps.getNhaThuocMaNhaThuoc(), phieuNhaps.getChiTiets().get(0).getThuocThuocId(), RecordStatusContains.ACTIVE);
        inventory.get().setDeliveryItemCount(deliveryItemCount);
        // count phieu nhap
        Long receiptItemCount = phieuNhapChiTietsRepository.countByNhaThuocMaNhaThuocAndThuocThuocIdAndRecordStatusId(phieuNhaps.getNhaThuocMaNhaThuoc(), phieuNhaps.getChiTiets().get(0).getThuocThuocId(), RecordStatusContains.ACTIVE);
        inventory.get().setReceiptItemCount(receiptItemCount);
        inventory.get().setHasTransactions((inventory.get().getDeliveryItemCount() + inventory.get().getReceiptItemCount()) > 0);

        // tìm phiếu nhập mới nhất set để hạn dùng
        List<PhieuNhapChiTiets> phieuNhapChiTiets = phieuNhapChiTietsRepository.findByNhaThuocMaNhaThuocAndThuocThuocIdAndRecordStatusIdAndMaxNgayNhap(phieuNhaps.getNhaThuocMaNhaThuoc(), phieuNhaps.getChiTiets().get(0).getThuocThuocId(), RecordStatusContains.ACTIVE);
        if (!phieuNhapChiTiets.isEmpty()) {
            Optional<PhieuNhaps> phieuNhapNs = phieuNhapsRepository.findById(phieuNhapChiTiets.get(0).getPhieuNhapMaPhieuNhap());
            if(phieuNhapNs.isPresent()){
                if (thuocs.get().getDonViThuNguyenMaDonViTinh() != null && thuocs.get().getDonViThuNguyenMaDonViTinh() > 0 && thuocs.get().getDonViThuNguyenMaDonViTinh().equals(phieuNhapChiTiets.get(0).getDonViTinhMaDonViTinh())) {
                    inventory.get().setLastInPrice(((double) (phieuNhapChiTiets.get(0).getRetailPrice() /  thuocs.get().getDonViThuNguyenMaDonViTinh())) * (1 - phieuNhapNs.get().getDiscount() / 100));
                }else {
                    inventory.get().setLastInPrice(phieuNhapChiTiets.get(0).getRetailPrice() * (1 - phieuNhapNs.get().getDiscount() / 100));
                }
            }
            inventory.get().setExpiredDate(phieuNhapChiTiets.get(0).getHanDung());
        }

        inventoryRepository.save(inventory.get());
    }
}
