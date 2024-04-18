package vn.com.gsoft.inventory.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.inventory.entity.Inventory;

import java.util.Optional;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Long> {
    Optional<Inventory> findByDrugStoreIdAndDrugIdAndRecordStatusId(String nhaThuocMaNhaThuoc, Long thuocThuocId, long active);
}
