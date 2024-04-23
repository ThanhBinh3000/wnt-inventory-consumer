package vn.com.gsoft.inventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Inventory")
public class Inventory {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "DrugStoreId")
    private String drugStoreId;
    @Column(name = "DrugId")
    private Long drugId;
    @Column(name = "LastValue")
    private Double lastValue;
    @Column(name = "DrugUnitId")
    private Long drugUnitId;
    @Column(name = "RecordStatusId")
    private Long recordStatusId;
    @Column(name = "NeedUpdate")
    private Boolean needUpdate;
    @Column(name = "LastInPrice")
    private Double lastInPrice;
    @Column(name = "LastOutPrice")
    private Double lastOutPrice;
    @Column(name = "RetailOutPrice")
    private Double retailOutPrice;
    @Column(name = "RetailBatchOutPrice")
    private Double retailBatchOutPrice;
    @Column(name = "LastUpdated")
    private Date lastUpdated;
    @Column(name = "LastIncurredData")
    private Date lastIncurredData;
    @Column(name = "SerialNumber")
    private String serialNumber;
    @Column(name = "RegenRevenue")
    private Boolean regenRevenue;
    @Column(name = "ArchiveDrugId")
    private Long archiveDrugId;
    @Column(name = "ArchiveUnitId")
    private Long archiveUnitId;
    @Column(name = "HasTransactions")
    private Boolean hasTransactions;


    @Column(name = "ReceiptItemCount")
    private Long receiptItemCount;
    @Column(name = "DeliveryItemCount")
    private Long deliveryItemCount;
    @Column(name = "ExpiredDate")
    private Date expiredDate;

    @Column(name = "InitValue")
    private Double initValue;
    @Column(name = "InitOutPrice")
    private BigDecimal initOutPrice;
    @Column(name = "InitInPrice")
    private Double initInPrice;
    @Column(name = "StoreId")
    private Long storeId;
    @Column(name = "ArchivedDate")
    private Date archivedDate;
    @Column(name = "OutPrice")
    private BigDecimal outPrice;


    public Long getDeliveryItemCount() {
        return deliveryItemCount == null ? 0 : deliveryItemCount;
    }

    public void setDeliveryItemCount(Long deliveryItemCount) {
        this.deliveryItemCount = deliveryItemCount;
    }


    public Long getReceiptItemCount() {
        return receiptItemCount == null ? 0 : receiptItemCount;
    }

    public void setReceiptItemCount(Long receiptItemCount) {
        this.receiptItemCount = receiptItemCount;
    }

    public Double getInitValue() {
        return initValue == null ? 0:initValue;
    }

    public void setInitValue(Double initValue) {
        this.initValue = initValue;
    }

}

