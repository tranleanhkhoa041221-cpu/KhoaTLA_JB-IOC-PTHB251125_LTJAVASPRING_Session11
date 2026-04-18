package ra.edu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.edu.dto.request.DailyExportDTO;
import ra.edu.dto.request.ImportExportDTO;
import ra.edu.dto.request.SupplyDTO;
import ra.edu.dto.request.TopExportDTO;
import ra.edu.dto.response.ApiResponse;
import ra.edu.dto.response.SupplyResponse;
import ra.edu.service.SupplyService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/supplies")
@RequiredArgsConstructor
@Slf4j
public class SupplyController {
        private final SupplyService supplyService;


        @PostMapping
        public ResponseEntity<ApiResponse<SupplyResponse>> create(
                @RequestBody SupplyDTO request
        ) {

            SupplyResponse dto = supplyService.create(request);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(
                            "201 CREATED",
                            "Tạo vật tư thành công",
                            dto

                    ));
        }


        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<SupplyResponse>> update(
                @PathVariable Long id,
                @RequestBody SupplyDTO request,
                @RequestBody Map<String, Object> rawBody
        ) {

            SupplyResponse dto = supplyService.update(id, request, rawBody);

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            "200 OK",
                            "Cập nhật thành công",
                            dto

                    )
            );
        }


        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {

            supplyService.delete(id);
            return ResponseEntity.noContent().build();
        }


        @GetMapping
        public ResponseEntity<ApiResponse<List<SupplyResponse>>> getAll() {

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            "200 OK",
                            "Lấy danh sách vật tư thành công",
                            supplyService.getAll()
                    )
            );
        }


        @GetMapping("/search")
        public ResponseEntity<ApiResponse<List<SupplyResponse>>> search(
                @RequestParam("name") String keyword
        ) {

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            "200 OK",
                            "Kết quả tìm kiếm",
                            supplyService.searchByName(keyword)

                    )
            );
        }


        @PatchMapping("/{id}/export")
        public ResponseEntity<ApiResponse<SupplyResponse>> export(
                @PathVariable Long id,
                @RequestBody ImportExportDTO request
        ) {

            SupplyResponse dto = supplyService.export(id, request);

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            "200 OK",
                            "Xuất kho thành công",
                            dto

                    )
            );
        }


        @PatchMapping("/{id}/import")
        public ResponseEntity<ApiResponse<SupplyResponse>> importSupply(
                @PathVariable Long id,
                @RequestBody ImportExportDTO request
        ) {

            SupplyResponse dto = supplyService.importSupply(id, request);

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            "200 OK",
                            "Nhập kho thành công",
                            dto

                    )
            );
        }


        @GetMapping("/statistics/daily-export")
        public ResponseEntity<ApiResponse<List<DailyExportDTO>>> dailyExport() {

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            "200 OK",
                            "Thống kê xuất kho trong ngày",
                            supplyService.getDailyExportStatistics()

                    )
            );
        }


        @GetMapping("/statistics/top-export")
        public ResponseEntity<ApiResponse<List<TopExportDTO>>> topExport() {

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            "200 OK",
                            "Vật tư xuất nhiều nhất",
                            supplyService.getTopExport()

                    )
            );
        }
    }