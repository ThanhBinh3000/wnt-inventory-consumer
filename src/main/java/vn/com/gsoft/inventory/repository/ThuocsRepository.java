package vn.com.gsoft.inventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.inventory.entity.Thuocs;
import vn.com.gsoft.inventory.model.dto.ThuocsReq;

import java.util.List;

@Repository
public interface ThuocsRepository extends BaseRepository<Thuocs, ThuocsReq, Long> {
    @Query("SELECT c FROM Thuocs c " +
            "WHERE 1=1 "
            + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId})"
            + " AND (:#{#param.maThuoc} IS NULL OR lower(c.maThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maThuoc},'%'))))"
            + " AND (:#{#param.tenThuoc} IS NULL OR lower(c.tenThuoc) LIKE lower(concat('%',CONCAT(:#{#param.tenThuoc},'%'))))"
            + " AND (:#{#param.thongTin} IS NULL OR lower(c.thongTin) LIKE lower(concat('%',CONCAT(:#{#param.thongTin},'%'))))"
            + " AND (:#{#param.heSo} IS NULL OR c.heSo = :#{#param.heSo}) "
            + " AND (:#{#param.giaNhap} IS NULL OR c.giaNhap = :#{#param.giaNhap}) "
            + " AND (:#{#param.giaBanBuon} IS NULL OR c.giaBanBuon = :#{#param.giaBanBuon}) "
            + " AND (:#{#param.giaBanLe} IS NULL OR c.giaBanLe = :#{#param.giaBanLe}) "
            + " AND (:#{#param.soDuDauKy} IS NULL OR c.soDuDauKy = :#{#param.soDuDauKy}) "
            + " AND (:#{#param.giaDauKy} IS NULL OR c.giaDauKy = :#{#param.giaDauKy}) "
            + " AND (:#{#param.gioiHan} IS NULL OR c.gioiHan = :#{#param.gioiHan}) "
            + " AND (:#{#param.nhaThuocMaNhaThuoc} IS NULL OR lower(c.nhaThuocMaNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.nhaThuocMaNhaThuoc},'%'))))"
            + " AND (:#{#param.nhomThuocMaNhomThuoc} IS NULL OR c.nhomThuocMaNhomThuoc = :#{#param.nhomThuocMaNhomThuoc}) "
            + " AND (:#{#param.nuocMaNuoc} IS NULL OR c.nuocMaNuoc = :#{#param.nuocMaNuoc}) "
            + " AND (:#{#param.dangBaoCheMaDangBaoChe} IS NULL OR c.dangBaoCheMaDangBaoChe = :#{#param.dangBaoCheMaDangBaoChe}) "
            + " AND (:#{#param.donViXuatLeMaDonViTinh} IS NULL OR c.donViXuatLeMaDonViTinh = :#{#param.donViXuatLeMaDonViTinh}) "
            + " AND (:#{#param.donViThuNguyenMaDonViTinh} IS NULL OR c.donViThuNguyenMaDonViTinh = :#{#param.donViThuNguyenMaDonViTinh}) "
            + " AND (:#{#param.barCode} IS NULL OR lower(c.barCode) LIKE lower(concat('%',CONCAT(:#{#param.barCode},'%'))))"
            + " AND (:#{#param.duTru} IS NULL OR c.duTru = :#{#param.duTru}) "
            + " AND (:#{#param.nhaThuocMaNhaThuocCreate} IS NULL OR lower(c.nhaThuocMaNhaThuocCreate) LIKE lower(concat('%',CONCAT(:#{#param.nhaThuocMaNhaThuocCreate},'%'))))"
            + " AND (:#{#param.connectivityDrugID} IS NULL OR c.connectivityDrugID = :#{#param.connectivityDrugID}) "
            + " AND (:#{#param.connectivityDrugFactor} IS NULL OR c.connectivityDrugFactor = :#{#param.connectivityDrugFactor}) "
            + " AND (:#{#param.maNhaCungCap} IS NULL OR c.maNhaCungCap = :#{#param.maNhaCungCap}) "
            + " AND (:#{#param.parentDrugId} IS NULL OR c.parentDrugId = :#{#param.parentDrugId}) "
            + " AND (:#{#param.metadataHash} IS NULL OR c.metadataHash = :#{#param.metadataHash}) "
            + " AND (:#{#param.rpMetadataHash} IS NULL OR c.rpMetadataHash = :#{#param.rpMetadataHash}) "
            + " AND (:#{#param.referenceId} IS NULL OR c.referenceId = :#{#param.referenceId}) "
            + " AND (:#{#param.discount} IS NULL OR c.discount = :#{#param.discount}) "
            + " AND (:#{#param.saleTypeId} IS NULL OR c.saleTypeId = :#{#param.saleTypeId}) "
            + " AND (:#{#param.saleOff} IS NULL OR c.saleOff = :#{#param.saleOff}) "
            + " AND (:#{#param.saleDescription} IS NULL OR lower(c.saleDescription) LIKE lower(concat('%',CONCAT(:#{#param.saleDescription},'%'))))"
            + " AND (:#{#param.imageThumbUrl} IS NULL OR lower(c.imageThumbUrl) LIKE lower(concat('%',CONCAT(:#{#param.imageThumbUrl},'%'))))"
            + " AND (:#{#param.imagePreviewUrl} IS NULL OR lower(c.imagePreviewUrl) LIKE lower(concat('%',CONCAT(:#{#param.imagePreviewUrl},'%'))))"
            + " AND (:#{#param.connectivityTypeId} IS NULL OR c.connectivityTypeId = :#{#param.connectivityTypeId}) "
            + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
            + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
            + " AND (:#{#param.productTypeId} IS NULL OR c.productTypeId = :#{#param.productTypeId}) "
            + " AND (:#{#param.serialNumber} IS NULL OR lower(c.serialNumber) LIKE lower(concat('%',CONCAT(:#{#param.serialNumber},'%'))))"
            + " AND (:#{#param.moneyToOneScoreRate} IS NULL OR c.moneyToOneScoreRate = :#{#param.moneyToOneScoreRate}) "
            + " AND (:#{#param.nameHash} IS NULL OR c.nameHash = :#{#param.nameHash}) "
            + " AND (:#{#param.registeredNo} IS NULL OR lower(c.registeredNo) LIKE lower(concat('%',CONCAT(:#{#param.registeredNo},'%'))))"
            + " AND (:#{#param.activeSubstance} IS NULL OR lower(c.activeSubstance) LIKE lower(concat('%',CONCAT(:#{#param.activeSubstance},'%'))))"
            + " AND (:#{#param.contents} IS NULL OR lower(c.contents) LIKE lower(concat('%',CONCAT(:#{#param.contents},'%'))))"
            + " AND (:#{#param.packingWay} IS NULL OR lower(c.packingWay) LIKE lower(concat('%',CONCAT(:#{#param.packingWay},'%'))))"
            + " AND (:#{#param.manufacturer} IS NULL OR lower(c.manufacturer) LIKE lower(concat('%',CONCAT(:#{#param.manufacturer},'%'))))"
            + " AND (:#{#param.countryOfManufacturer} IS NULL OR lower(c.countryOfManufacturer) LIKE lower(concat('%',CONCAT(:#{#param.countryOfManufacturer},'%'))))"
            + " AND (:#{#param.countryId} IS NULL OR c.countryId = :#{#param.countryId}) "
            + " AND (:#{#param.connectivityId} IS NULL OR lower(c.connectivityId) LIKE lower(concat('%',CONCAT(:#{#param.connectivityId},'%'))))"
            + " AND (:#{#param.connectivityResult} IS NULL OR lower(c.connectivityResult) LIKE lower(concat('%',CONCAT(:#{#param.connectivityResult},'%'))))"
            + " AND (:#{#param.dosageForms} IS NULL OR lower(c.dosageForms) LIKE lower(concat('%',CONCAT(:#{#param.dosageForms},'%'))))"
            + " AND (:#{#param.smallestPackingUnit} IS NULL OR lower(c.smallestPackingUnit) LIKE lower(concat('%',CONCAT(:#{#param.smallestPackingUnit},'%'))))"
            + " AND (:#{#param.importers} IS NULL OR lower(c.importers) LIKE lower(concat('%',CONCAT(:#{#param.importers},'%'))))"
            + " AND (:#{#param.declaredPrice} IS NULL OR c.declaredPrice = :#{#param.declaredPrice}) "
            + " AND (:#{#param.connectivityCode} IS NULL OR lower(c.connectivityCode) LIKE lower(concat('%',CONCAT(:#{#param.connectivityCode},'%'))))"
            + " AND (:#{#param.codeHash} IS NULL OR c.codeHash = :#{#param.codeHash}) "
            + " AND (:#{#param.connectivityStatusId} IS NULL OR c.connectivityStatusId = :#{#param.connectivityStatusId}) "
            + " AND (:#{#param.organizeDeclaration} IS NULL OR lower(c.organizeDeclaration) LIKE lower(concat('%',CONCAT(:#{#param.organizeDeclaration},'%'))))"
            + " AND (:#{#param.countryRegistration} IS NULL OR lower(c.countryRegistration) LIKE lower(concat('%',CONCAT(:#{#param.countryRegistration},'%'))))"
            + " AND (:#{#param.addressRegistration} IS NULL OR lower(c.addressRegistration) LIKE lower(concat('%',CONCAT(:#{#param.addressRegistration},'%'))))"
            + " AND (:#{#param.addressManufacture} IS NULL OR lower(c.addressManufacture) LIKE lower(concat('%',CONCAT(:#{#param.addressManufacture},'%'))))"
            + " AND (:#{#param.identifier} IS NULL OR lower(c.identifier) LIKE lower(concat('%',CONCAT(:#{#param.identifier},'%'))))"
            + " AND (:#{#param.classification} IS NULL OR lower(c.classification) LIKE lower(concat('%',CONCAT(:#{#param.classification},'%'))))"
            + " AND (:#{#param.forWholesale} IS NULL OR c.forWholesale = :#{#param.forWholesale}) "
            + " AND (:#{#param.hoatChat} IS NULL OR lower(c.hoatChat) LIKE lower(concat('%',CONCAT(:#{#param.hoatChat},'%'))))"
            + " AND (:#{#param.typeService} IS NULL OR c.typeService = :#{#param.typeService}) "
            + " AND (:#{#param.typeServices} IS NULL OR c.typeServices = :#{#param.typeServices}) "
            + " AND (:#{#param.idTypeService} IS NULL OR c.idTypeService = :#{#param.idTypeService}) "
            + " AND (:#{#param.idClinic} IS NULL OR c.idClinic = :#{#param.idClinic}) "
            + " AND (:#{#param.countNumbers} IS NULL OR c.countNumbers = :#{#param.countNumbers}) "
            + " AND (:#{#param.idWarehouseLocation} IS NULL OR c.idWarehouseLocation = :#{#param.idWarehouseLocation}) "
            + " AND (:#{#param.hamLuong} IS NULL OR lower(c.hamLuong) LIKE lower(concat('%',CONCAT(:#{#param.hamLuong},'%'))))"
            + " AND (:#{#param.quyCachDongGoi} IS NULL OR lower(c.quyCachDongGoi) LIKE lower(concat('%',CONCAT(:#{#param.quyCachDongGoi},'%'))))"
            + " AND (:#{#param.nhaSanXuat} IS NULL OR lower(c.nhaSanXuat) LIKE lower(concat('%',CONCAT(:#{#param.nhaSanXuat},'%'))))"
            + " AND (:#{#param.advantages} IS NULL OR lower(c.advantages) LIKE lower(concat('%',CONCAT(:#{#param.advantages},'%'))))"
            + " AND (:#{#param.userObject} IS NULL OR lower(c.userObject) LIKE lower(concat('%',CONCAT(:#{#param.userObject},'%'))))"
            + " AND (:#{#param.userManual} IS NULL OR lower(c.userManual) LIKE lower(concat('%',CONCAT(:#{#param.userManual},'%'))))"
            + " AND (:#{#param.pharmacokinetics} IS NULL OR lower(c.pharmacokinetics) LIKE lower(concat('%',CONCAT(:#{#param.pharmacokinetics},'%'))))"
            + " AND (:#{#param.groupIdMapping} IS NULL OR c.groupIdMapping = :#{#param.groupIdMapping}) "
            + " AND (:#{#param.groupNameMapping} IS NULL OR lower(c.groupNameMapping) LIKE lower(concat('%',CONCAT(:#{#param.groupNameMapping},'%'))))"
            + " AND (:#{#param.resultService} IS NULL OR lower(c.resultService) LIKE lower(concat('%',CONCAT(:#{#param.resultService},'%'))))"
            + " AND (:#{#param.titleResultService} IS NULL OR lower(c.titleResultService) LIKE lower(concat('%',CONCAT(:#{#param.titleResultService},'%'))))"
            + " AND (:#{#param.typeResultService} IS NULL OR c.typeResultService = :#{#param.typeResultService}) "
            + " AND (:#{#param.groupIdMappingV2} IS NULL OR c.groupIdMappingV2 = :#{#param.groupIdMappingV2}) "
            + " AND (:#{#param.storageConditions} IS NULL OR lower(c.storageConditions) LIKE lower(concat('%',CONCAT(:#{#param.storageConditions},'%'))))"
            + " AND (:#{#param.storageLocation} IS NULL OR lower(c.storageLocation) LIKE lower(concat('%',CONCAT(:#{#param.storageLocation},'%'))))"
            + " AND (:#{#param.chiDinh} IS NULL OR lower(c.chiDinh) LIKE lower(concat('%',CONCAT(:#{#param.chiDinh},'%'))))"
            + " AND (:#{#param.chongChiDinh} IS NULL OR lower(c.chongChiDinh) LIKE lower(concat('%',CONCAT(:#{#param.chongChiDinh},'%'))))"
            + " AND (:#{#param.xuatXu} IS NULL OR lower(c.xuatXu) LIKE lower(concat('%',CONCAT(:#{#param.xuatXu},'%'))))"
            + " AND (:#{#param.luuY} IS NULL OR lower(c.luuY) LIKE lower(concat('%',CONCAT(:#{#param.luuY},'%'))))"
            + " AND (:#{#param.promotionalDiscounts} IS NULL OR c.promotionalDiscounts = :#{#param.promotionalDiscounts}) "
            + " AND (:#{#param.descriptionOnWebsite} IS NULL OR lower(c.descriptionOnWebsite) LIKE lower(concat('%',CONCAT(:#{#param.descriptionOnWebsite},'%'))))"
            + " AND (:#{#param.imgReferenceDrugId} IS NULL OR c.imgReferenceDrugId = :#{#param.imgReferenceDrugId}) "
            + " AND (:#{#param.userUploadImgId} IS NULL OR c.userUploadImgId = :#{#param.userUploadImgId}) "
            + " AND (:#{#param.statusConfirm} IS NULL OR c.statusConfirm = :#{#param.statusConfirm}) "
            + " AND (:#{#param.userIdConfirm} IS NULL OR c.userIdConfirm = :#{#param.userIdConfirm}) "
            + " AND (:#{#param.userIdMapping} IS NULL OR c.userIdMapping = :#{#param.userIdMapping}) "
            + " AND ((:#{#param.textSearch} IS NULL OR lower(c.maThuoc) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%')))) "
            + " OR (:#{#param.textSearch} IS NULL OR lower(c.tenThuoc) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%')))) "
            + " OR (:#{#param.textSearch} IS NULL OR lower(c.thongTin) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%'))))) "
            + " ORDER BY c.id desc"
    )
    Page<Thuocs> searchPage(@Param("param") ThuocsReq param, Pageable pageable);


    @Query("SELECT c FROM Thuocs c " +
            "WHERE 1=1 "
            + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId})"
            + " AND (:#{#param.maThuoc} IS NULL OR lower(c.maThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maThuoc},'%'))))"
            + " AND (:#{#param.tenThuoc} IS NULL OR lower(c.tenThuoc) LIKE lower(concat('%',CONCAT(:#{#param.tenThuoc},'%'))))"
            + " AND (:#{#param.thongTin} IS NULL OR lower(c.thongTin) LIKE lower(concat('%',CONCAT(:#{#param.thongTin},'%'))))"
            + " AND (:#{#param.heSo} IS NULL OR c.heSo = :#{#param.heSo}) "
            + " AND (:#{#param.giaNhap} IS NULL OR c.giaNhap = :#{#param.giaNhap}) "
            + " AND (:#{#param.giaBanBuon} IS NULL OR c.giaBanBuon = :#{#param.giaBanBuon}) "
            + " AND (:#{#param.giaBanLe} IS NULL OR c.giaBanLe = :#{#param.giaBanLe}) "
            + " AND (:#{#param.soDuDauKy} IS NULL OR c.soDuDauKy = :#{#param.soDuDauKy}) "
            + " AND (:#{#param.giaDauKy} IS NULL OR c.giaDauKy = :#{#param.giaDauKy}) "
            + " AND (:#{#param.gioiHan} IS NULL OR c.gioiHan = :#{#param.gioiHan}) "
            + " AND (:#{#param.nhaThuocMaNhaThuoc} IS NULL OR lower(c.nhaThuocMaNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.nhaThuocMaNhaThuoc},'%'))))"
            + " AND (:#{#param.nhomThuocMaNhomThuoc} IS NULL OR c.nhomThuocMaNhomThuoc = :#{#param.nhomThuocMaNhomThuoc}) "
            + " AND (:#{#param.nuocMaNuoc} IS NULL OR c.nuocMaNuoc = :#{#param.nuocMaNuoc}) "
            + " AND (:#{#param.dangBaoCheMaDangBaoChe} IS NULL OR c.dangBaoCheMaDangBaoChe = :#{#param.dangBaoCheMaDangBaoChe}) "
            + " AND (:#{#param.donViXuatLeMaDonViTinh} IS NULL OR c.donViXuatLeMaDonViTinh = :#{#param.donViXuatLeMaDonViTinh}) "
            + " AND (:#{#param.donViThuNguyenMaDonViTinh} IS NULL OR c.donViThuNguyenMaDonViTinh = :#{#param.donViThuNguyenMaDonViTinh}) "
            + " AND (:#{#param.barCode} IS NULL OR lower(c.barCode) LIKE lower(concat('%',CONCAT(:#{#param.barCode},'%'))))"
            + " AND (:#{#param.duTru} IS NULL OR c.duTru = :#{#param.duTru}) "
            + " AND (:#{#param.nhaThuocMaNhaThuocCreate} IS NULL OR lower(c.nhaThuocMaNhaThuocCreate) LIKE lower(concat('%',CONCAT(:#{#param.nhaThuocMaNhaThuocCreate},'%'))))"
            + " AND (:#{#param.connectivityDrugID} IS NULL OR c.connectivityDrugID = :#{#param.connectivityDrugID}) "
            + " AND (:#{#param.connectivityDrugFactor} IS NULL OR c.connectivityDrugFactor = :#{#param.connectivityDrugFactor}) "
            + " AND (:#{#param.maNhaCungCap} IS NULL OR c.maNhaCungCap = :#{#param.maNhaCungCap}) "
            + " AND (:#{#param.parentDrugId} IS NULL OR c.parentDrugId = :#{#param.parentDrugId}) "
            + " AND (:#{#param.metadataHash} IS NULL OR c.metadataHash = :#{#param.metadataHash}) "
            + " AND (:#{#param.rpMetadataHash} IS NULL OR c.rpMetadataHash = :#{#param.rpMetadataHash}) "
            + " AND (:#{#param.referenceId} IS NULL OR c.referenceId = :#{#param.referenceId}) "
            + " AND (:#{#param.discount} IS NULL OR c.discount = :#{#param.discount}) "
            + " AND (:#{#param.saleTypeId} IS NULL OR c.saleTypeId = :#{#param.saleTypeId}) "
            + " AND (:#{#param.saleOff} IS NULL OR c.saleOff = :#{#param.saleOff}) "
            + " AND (:#{#param.saleDescription} IS NULL OR lower(c.saleDescription) LIKE lower(concat('%',CONCAT(:#{#param.saleDescription},'%'))))"
            + " AND (:#{#param.imageThumbUrl} IS NULL OR lower(c.imageThumbUrl) LIKE lower(concat('%',CONCAT(:#{#param.imageThumbUrl},'%'))))"
            + " AND (:#{#param.imagePreviewUrl} IS NULL OR lower(c.imagePreviewUrl) LIKE lower(concat('%',CONCAT(:#{#param.imagePreviewUrl},'%'))))"
            + " AND (:#{#param.connectivityTypeId} IS NULL OR c.connectivityTypeId = :#{#param.connectivityTypeId}) "
            + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
            + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
            + " AND (:#{#param.productTypeId} IS NULL OR c.productTypeId = :#{#param.productTypeId}) "
            + " AND (:#{#param.serialNumber} IS NULL OR lower(c.serialNumber) LIKE lower(concat('%',CONCAT(:#{#param.serialNumber},'%'))))"
            + " AND (:#{#param.moneyToOneScoreRate} IS NULL OR c.moneyToOneScoreRate = :#{#param.moneyToOneScoreRate}) "
            + " AND (:#{#param.nameHash} IS NULL OR c.nameHash = :#{#param.nameHash}) "
            + " AND (:#{#param.registeredNo} IS NULL OR lower(c.registeredNo) LIKE lower(concat('%',CONCAT(:#{#param.registeredNo},'%'))))"
            + " AND (:#{#param.activeSubstance} IS NULL OR lower(c.activeSubstance) LIKE lower(concat('%',CONCAT(:#{#param.activeSubstance},'%'))))"
            + " AND (:#{#param.contents} IS NULL OR lower(c.contents) LIKE lower(concat('%',CONCAT(:#{#param.contents},'%'))))"
            + " AND (:#{#param.packingWay} IS NULL OR lower(c.packingWay) LIKE lower(concat('%',CONCAT(:#{#param.packingWay},'%'))))"
            + " AND (:#{#param.manufacturer} IS NULL OR lower(c.manufacturer) LIKE lower(concat('%',CONCAT(:#{#param.manufacturer},'%'))))"
            + " AND (:#{#param.countryOfManufacturer} IS NULL OR lower(c.countryOfManufacturer) LIKE lower(concat('%',CONCAT(:#{#param.countryOfManufacturer},'%'))))"
            + " AND (:#{#param.countryId} IS NULL OR c.countryId = :#{#param.countryId}) "
            + " AND (:#{#param.connectivityId} IS NULL OR lower(c.connectivityId) LIKE lower(concat('%',CONCAT(:#{#param.connectivityId},'%'))))"
            + " AND (:#{#param.connectivityResult} IS NULL OR lower(c.connectivityResult) LIKE lower(concat('%',CONCAT(:#{#param.connectivityResult},'%'))))"
            + " AND (:#{#param.dosageForms} IS NULL OR lower(c.dosageForms) LIKE lower(concat('%',CONCAT(:#{#param.dosageForms},'%'))))"
            + " AND (:#{#param.smallestPackingUnit} IS NULL OR lower(c.smallestPackingUnit) LIKE lower(concat('%',CONCAT(:#{#param.smallestPackingUnit},'%'))))"
            + " AND (:#{#param.importers} IS NULL OR lower(c.importers) LIKE lower(concat('%',CONCAT(:#{#param.importers},'%'))))"
            + " AND (:#{#param.declaredPrice} IS NULL OR c.declaredPrice = :#{#param.declaredPrice}) "
            + " AND (:#{#param.connectivityCode} IS NULL OR lower(c.connectivityCode) LIKE lower(concat('%',CONCAT(:#{#param.connectivityCode},'%'))))"
            + " AND (:#{#param.codeHash} IS NULL OR c.codeHash = :#{#param.codeHash}) "
            + " AND (:#{#param.connectivityStatusId} IS NULL OR c.connectivityStatusId = :#{#param.connectivityStatusId}) "
            + " AND (:#{#param.organizeDeclaration} IS NULL OR lower(c.organizeDeclaration) LIKE lower(concat('%',CONCAT(:#{#param.organizeDeclaration},'%'))))"
            + " AND (:#{#param.countryRegistration} IS NULL OR lower(c.countryRegistration) LIKE lower(concat('%',CONCAT(:#{#param.countryRegistration},'%'))))"
            + " AND (:#{#param.addressRegistration} IS NULL OR lower(c.addressRegistration) LIKE lower(concat('%',CONCAT(:#{#param.addressRegistration},'%'))))"
            + " AND (:#{#param.addressManufacture} IS NULL OR lower(c.addressManufacture) LIKE lower(concat('%',CONCAT(:#{#param.addressManufacture},'%'))))"
            + " AND (:#{#param.identifier} IS NULL OR lower(c.identifier) LIKE lower(concat('%',CONCAT(:#{#param.identifier},'%'))))"
            + " AND (:#{#param.classification} IS NULL OR lower(c.classification) LIKE lower(concat('%',CONCAT(:#{#param.classification},'%'))))"
            + " AND (:#{#param.forWholesale} IS NULL OR c.forWholesale = :#{#param.forWholesale}) "
            + " AND (:#{#param.hoatChat} IS NULL OR lower(c.hoatChat) LIKE lower(concat('%',CONCAT(:#{#param.hoatChat},'%'))))"
            + " AND (:#{#param.typeService} IS NULL OR c.typeService = :#{#param.typeService}) "
            + " AND (:#{#param.typeServices} IS NULL OR c.typeServices = :#{#param.typeServices}) "
            + " AND (:#{#param.idTypeService} IS NULL OR c.idTypeService = :#{#param.idTypeService}) "
            + " AND (:#{#param.idClinic} IS NULL OR c.idClinic = :#{#param.idClinic}) "
            + " AND (:#{#param.countNumbers} IS NULL OR c.countNumbers = :#{#param.countNumbers}) "
            + " AND (:#{#param.idWarehouseLocation} IS NULL OR c.idWarehouseLocation = :#{#param.idWarehouseLocation}) "
            + " AND (:#{#param.hamLuong} IS NULL OR lower(c.hamLuong) LIKE lower(concat('%',CONCAT(:#{#param.hamLuong},'%'))))"
            + " AND (:#{#param.quyCachDongGoi} IS NULL OR lower(c.quyCachDongGoi) LIKE lower(concat('%',CONCAT(:#{#param.quyCachDongGoi},'%'))))"
            + " AND (:#{#param.nhaSanXuat} IS NULL OR lower(c.nhaSanXuat) LIKE lower(concat('%',CONCAT(:#{#param.nhaSanXuat},'%'))))"
            + " AND (:#{#param.advantages} IS NULL OR lower(c.advantages) LIKE lower(concat('%',CONCAT(:#{#param.advantages},'%'))))"
            + " AND (:#{#param.userObject} IS NULL OR lower(c.userObject) LIKE lower(concat('%',CONCAT(:#{#param.userObject},'%'))))"
            + " AND (:#{#param.userManual} IS NULL OR lower(c.userManual) LIKE lower(concat('%',CONCAT(:#{#param.userManual},'%'))))"
            + " AND (:#{#param.pharmacokinetics} IS NULL OR lower(c.pharmacokinetics) LIKE lower(concat('%',CONCAT(:#{#param.pharmacokinetics},'%'))))"
            + " AND (:#{#param.groupIdMapping} IS NULL OR c.groupIdMapping = :#{#param.groupIdMapping}) "
            + " AND (:#{#param.groupNameMapping} IS NULL OR lower(c.groupNameMapping) LIKE lower(concat('%',CONCAT(:#{#param.groupNameMapping},'%'))))"
            + " AND (:#{#param.resultService} IS NULL OR lower(c.resultService) LIKE lower(concat('%',CONCAT(:#{#param.resultService},'%'))))"
            + " AND (:#{#param.titleResultService} IS NULL OR lower(c.titleResultService) LIKE lower(concat('%',CONCAT(:#{#param.titleResultService},'%'))))"
            + " AND (:#{#param.typeResultService} IS NULL OR c.typeResultService = :#{#param.typeResultService}) "
            + " AND (:#{#param.groupIdMappingV2} IS NULL OR c.groupIdMappingV2 = :#{#param.groupIdMappingV2}) "
            + " AND (:#{#param.storageConditions} IS NULL OR lower(c.storageConditions) LIKE lower(concat('%',CONCAT(:#{#param.storageConditions},'%'))))"
            + " AND (:#{#param.storageLocation} IS NULL OR lower(c.storageLocation) LIKE lower(concat('%',CONCAT(:#{#param.storageLocation},'%'))))"
            + " AND (:#{#param.chiDinh} IS NULL OR lower(c.chiDinh) LIKE lower(concat('%',CONCAT(:#{#param.chiDinh},'%'))))"
            + " AND (:#{#param.chongChiDinh} IS NULL OR lower(c.chongChiDinh) LIKE lower(concat('%',CONCAT(:#{#param.chongChiDinh},'%'))))"
            + " AND (:#{#param.xuatXu} IS NULL OR lower(c.xuatXu) LIKE lower(concat('%',CONCAT(:#{#param.xuatXu},'%'))))"
            + " AND (:#{#param.luuY} IS NULL OR lower(c.luuY) LIKE lower(concat('%',CONCAT(:#{#param.luuY},'%'))))"
            + " AND (:#{#param.promotionalDiscounts} IS NULL OR c.promotionalDiscounts = :#{#param.promotionalDiscounts}) "
            + " AND (:#{#param.descriptionOnWebsite} IS NULL OR lower(c.descriptionOnWebsite) LIKE lower(concat('%',CONCAT(:#{#param.descriptionOnWebsite},'%'))))"
            + " AND (:#{#param.imgReferenceDrugId} IS NULL OR c.imgReferenceDrugId = :#{#param.imgReferenceDrugId}) "
            + " AND (:#{#param.userUploadImgId} IS NULL OR c.userUploadImgId = :#{#param.userUploadImgId}) "
            + " AND (:#{#param.statusConfirm} IS NULL OR c.statusConfirm = :#{#param.statusConfirm}) "
            + " AND (:#{#param.userIdConfirm} IS NULL OR c.userIdConfirm = :#{#param.userIdConfirm}) "
            + " AND (:#{#param.userIdMapping} IS NULL OR c.userIdMapping = :#{#param.userIdMapping}) "
            + " AND ((:#{#param.textSearch} IS NULL OR lower(c.maThuoc) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%')))) "
            + " OR (:#{#param.textSearch} IS NULL OR lower(c.tenThuoc) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%')))) "
            + " OR (:#{#param.textSearch} IS NULL OR lower(c.thongTin) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%'))))) "
            + " ORDER BY c.id desc"
    )
    List<Thuocs> searchList(@Param("param") ThuocsReq param);
}
