package vn.com.gsoft.inventory.service.impl;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.inventory.constant.RecordStatusContains;
import vn.com.gsoft.inventory.entity.Inventory;
import vn.com.gsoft.inventory.entity.PhieuNhaps;
import vn.com.gsoft.inventory.entity.PhieuXuats;
import vn.com.gsoft.inventory.model.dto.WrapDataNhap;
import vn.com.gsoft.inventory.model.dto.WrapDataXuat;
import vn.com.gsoft.inventory.repository.InventoryRepository;
import vn.com.gsoft.inventory.service.InventoryService;

import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public void xuat(String wrapData) {
        Gson gson = new Gson();
        WrapDataXuat dataXuat = gson.fromJson(wrapData, WrapDataXuat.class);
        PhieuXuats phieuXuats = dataXuat.getData();
        Optional<Inventory> inventory = inventoryRepository.findByDrugStoreIdAndDrugIdAndRecordStatusId(phieuXuats.getNhaThuocMaNhaThuoc(), phieuXuats.getChiTiets().get(0).getThuocThuocId(), RecordStatusContains.ACTIVE);
        if (inventory.isPresent()) {

        } else {

        }
    }

    @Override
    public void nhap(String wrapData) {
        Gson gson = new Gson();
        WrapDataNhap dataNhap = gson.fromJson(wrapData, WrapDataNhap.class);
        PhieuNhaps phieuNhaps = dataNhap.getData();
        Optional<Inventory> inventory = inventoryRepository.findByDrugStoreIdAndDrugIdAndRecordStatusId(phieuNhaps.getNhaThuocMaNhaThuoc(), phieuNhaps.getChiTiets().get(0).getThuocThuocId(), RecordStatusContains.ACTIVE);
        if (inventory.isPresent()) {

        } else {

        }
    }
}
