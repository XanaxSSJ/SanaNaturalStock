package com.sananatural.backend.service;

import com.sananatural.backend.dto.DetallePedidoDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportService {

    public ByteArrayInputStream exportarDetallesAExcel(List<DetallePedidoDTO> detalles) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Detalles Pedido");

            // Encabezados
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID Detalle", "Codigo Producto", "Nombre", "Cantidad"};

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Contenido
            int rowIdx = 1;
            for (DetallePedidoDTO detalle : detalles) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(detalle.getId());
                row.createCell(1).setCellValue(detalle.getCodigoProducto());
                row.createCell(2).setCellValue(detalle.getNombreProducto());
                row.createCell(3).setCellValue(detalle.getCantidad());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("Error al exportar datos a Excel", e);
        }
    }
}
