package de.szut.webshop.supplier;

import de.szut.webshop.contact.ContactEntity;
import de.szut.webshop.exceptionhandling.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    private final SupplierRepository repository;

    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    public SupplierEntity create(SupplierEntity newSupplier){
        return repository.save(newSupplier);
    }

    public List<SupplierEntity> readAll(){
        return repository.findAll();
    }

    public SupplierEntity readById(long id) {
        Optional<SupplierEntity> supplierOptional = repository.findById(id);
        if (supplierOptional.isEmpty()) {
            throw new ResourceNotFoundException("Supplier not found on id = " + id);
        }
        return supplierOptional.get();
    }

    public SupplierEntity update(SupplierEntity supplier) {
        SupplierEntity updatedSupplier = readById(supplier.getSid());

        if (updatedSupplier != null) {
            updatedSupplier.setName(supplier.getName());

            if (updatedSupplier.getContact() != null) {
                updatedSupplier.getContact().setStreet(supplier.getContact().getStreet());
                updatedSupplier.getContact().setPostcode(supplier.getContact().getPostcode());
                updatedSupplier.getContact().setCity(supplier.getContact().getCity());
                updatedSupplier.getContact().setPhone(supplier.getContact().getPhone());
            } else {
                de.szut.webshop.contact.ContactEntity newContact = new ContactEntity();
                newContact.setStreet(supplier.getContact().getStreet());
                newContact.setPostcode(supplier.getContact().getPostcode());
                newContact.setCity(supplier.getContact().getCity());
                newContact.setPhone(supplier.getContact().getPhone());
                updatedSupplier.setContact(newContact);
            }

            updatedSupplier = this.repository.save(updatedSupplier);
        }

        return updatedSupplier;
    }

    public void delete(long id){
        repository.deleteById(id);
    }
}
