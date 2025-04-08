package com.sananatural.backend.specification;

import com.sananatural.backend.model.Producto;
import org.springframework.data.jpa.domain.Specification;

public class ProductoSpecification {

    public static Specification<Producto> nombreContiene(String nombre) {
        return (root, query, criteriaBuilder) ->
                nombre == null ? null :
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%");
    }

    public static Specification<Producto> tieneCategoria(Long categoriaId) {
        return (root, query, criteriaBuilder) ->
                categoriaId == null ? null :
                        criteriaBuilder.equal(root.get("categoria").get("id"), categoriaId);
    }

    public static Specification<Producto> tieneProveedor(Long proveedorId) {
        return (root, query, criteriaBuilder) ->
                proveedorId == null ? null :
                        criteriaBuilder.equal(root.get("proveedor").get("id"), proveedorId);
    }
}
