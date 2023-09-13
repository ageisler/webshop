package de.szut.webshop.supplier;

import de.szut.webshop.mapping.MappingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/store/supplier")
public class SupplierController {
    private final SupplierService service;
    private final MappingService mappingService;

    public SupplierController(SupplierService service, MappingService mappingService) {
        this.service = service;
        this.mappingService = mappingService;
    }

    @PostMapping
    public ResponseEntity<GetSupplierDto> createSupplier(@Valid @RequestBody final AddSupplierDto dto){
        SupplierEntity newSupplier = this.mappingService.mapAddSupplierDtoToSupplier(dto);
        newSupplier = this.service.create(newSupplier);
        final GetSupplierDto request = this.mappingService.mapSupplierToGetSupplierDto(newSupplier);
        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GetSupplierDto>> findAllSuppliers(){
        List<SupplierEntity> all = this.service.readAll();
        List<GetSupplierDto> dtoList = new LinkedList<>();
        for(SupplierEntity supplier: all) {
            dtoList.add(this.mappingService.mapSupplierToGetSupplierDto(supplier));
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSupplierDto> getSupplierById(@PathVariable final Long id) {
        final var entity = this.service.readById(id);
        final GetSupplierDto dto = this.mappingService.mapSupplierToGetSupplierDto(entity);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<GetSupplierDto> updateSupplier(@RequestBody SupplierEntity supplierToUpdate){
        SupplierEntity updatedSupplier = this.service.update(supplierToUpdate);
        if(updatedSupplier != null){
            return new ResponseEntity<>(this.mappingService.mapSupplierToGetSupplierDto(updatedSupplier), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteSupplierById(@PathVariable Long id){
        this.service.delete(id);
    }


}
