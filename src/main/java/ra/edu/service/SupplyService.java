package ra.edu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ra.edu.dto.request.DailyExportDTO;
import ra.edu.dto.request.ImportExportDTO;
import ra.edu.dto.request.SupplyDTO;
import ra.edu.dto.request.TopExportDTO;
import ra.edu.dto.response.SupplyResponse;
import ra.edu.entity.Supply;
import ra.edu.entity.Transaction;
import ra.edu.entity.TransactionType;
import ra.edu.exception.BadRequestException;
import ra.edu.exception.ResourceNotFoundException;
import ra.edu.mapper.SupplyMapper;
import ra.edu.repository.SupplyRepository;
import ra.edu.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class SupplyService {
    private final SupplyRepository supplyRepository;
    private final TransactionRepository transactionRepository;


    private static final Logger historyLogger = LoggerFactory.getLogger("history");


    private static final Logger statisticsLogger = LoggerFactory.getLogger("statistics");

    public SupplyResponse create(SupplyDTO request) {

        Supply supply = new Supply();
        supply.setName(request.getName());
        supply.setSpecification(request.getSpecification());
        supply.setProvider(request.getProvider());
        supply.setQuantity(0);
        supply.setDeleted(false);

        supplyRepository.save(supply);

        log.info("Tạo vật tư: {} - ID {}", supply.getName(), supply.getId());

        return SupplyMapper.toResponse(supply);
    }

    public SupplyResponse update(Long id, SupplyDTO request, Map<String, Object> rawBody) {

        if (rawBody.containsKey("id") || rawBody.containsKey("quantity")) {
            log.warn("Client gửi field cấm khi update ID {}", id);
            throw new BadRequestException("Không được cập nhật id hoặc quantity");
        }

        Supply supply = supplyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy vật tư"));

        if (supply.isDeleted()) {
            throw new BadRequestException("Vật tư đã bị xóa");
        }

        supply.setName(request.getName());
        supply.setSpecification(request.getSpecification());
        supply.setProvider(request.getProvider());
        supply.setUpdatedAt(LocalDateTime.now());

        supplyRepository.save(supply);

        return SupplyMapper.toResponse(supply);
    }


    public void delete(Long id) {

        Supply supply = supplyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy vật tư"));

        if (supply.isDeleted()) {
            throw new ResourceNotFoundException("Vật tư đã bị xóa trước đó");
        }

        supply.setDeleted(true);
        supplyRepository.save(supply);

        log.info("Soft delete vật tư ID {}", id);
    }


    public List<SupplyResponse> getAll() {

        List<Supply> list = supplyRepository.findByDeletedFalse();

        log.debug("Số lượng vật tư: {}", list.size());

        return list.stream()
                .map(SupplyMapper::toResponse)
                .toList();
    }


    public List<SupplyResponse> searchByName(String keyword) {

        List<Supply> list =
                supplyRepository.findByNameContainingIgnoreCaseAndDeletedFalse(keyword);

        if (list.isEmpty()) {
            log.info("Không tìm thấy với keyword: {}", keyword);
        }

        return list.stream()
                .map(SupplyMapper::toResponse)
                .toList();
    }


    public SupplyResponse export(Long id, ImportExportDTO request) {

        Supply supply = supplyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy vật tư"));

        if (supply.getQuantity() < request.getAmount()) {
            log.error("Xuất thất bại ID {}: yêu cầu {}, tồn {}",
                    id, request.getAmount(), supply.getQuantity());
            throw new BadRequestException("Không đủ hàng");
        }

        supply.setQuantity(supply.getQuantity() - request.getAmount());
        supply.setUpdatedAt(LocalDateTime.now());
        supplyRepository.save(supply);

        Transaction t = new Transaction();
        t.setSupply(supply);
        t.setAmount(request.getAmount());
        t.setType(TransactionType.EXPORT);
        transactionRepository.save(t);

        return SupplyMapper.toResponse(supply);
    }



    public SupplyResponse importSupply(Long id, ImportExportDTO request) {

        Supply supply = supplyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy vật tư"));

        int old = supply.getQuantity();

        supply.setQuantity(old + request.getAmount());
        supply.setUpdatedAt(LocalDateTime.now());
        supplyRepository.save(supply);

        Transaction t = new Transaction();
        t.setSupply(supply);
        t.setAmount(request.getAmount());
        t.setType(TransactionType.IMPORT);
        transactionRepository.save(t);

        historyLogger.info("Nhập kho ID {}, số lượng + {} , tồn cũ {}", id, request.getAmount(), old);

        return SupplyMapper.toResponse(supply);
    }


    public List<DailyExportDTO> getDailyExportStatistics() {

        long start = System.currentTimeMillis();

        List<DailyExportDTO> result =
                transactionRepository.getDailyExport(TransactionType.EXPORT);

        long end = System.currentTimeMillis();
        statisticsLogger.info("Daily export done in {} ms", (end - start));

        return result;
    }



    //
    public List<TopExportDTO> getTopExport() {
        List<TopExportDTO> list = transactionRepository.getTopExportQuery(TransactionType.EXPORT);

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Chưa có dữ liệu");
        }

        Long max = list.get(0).getTotalExportQuantity();

        return list.stream()
                .filter(x -> x.getTotalExportQuantity().equals(max))
                .toList();
    }
}
